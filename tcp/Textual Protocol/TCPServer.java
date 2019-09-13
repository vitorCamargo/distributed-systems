/*
    Description: This is the class of the TCP Client for a Textual Protocol.
    Author: Vitor Bueno de Camargo
    Created at: September, 9th. 2019
    Updated at: September, 13th. 2019
*/

package text_tcp;

import java.net.*;
import java.io.*;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class TCPServer {
    public static void main(String args[]) {
        try {
            int serverPort = 6666;
            ServerSocket listenSocket = new ServerSocket(serverPort);

            while(true) {
                System.out.println("Servidor aguardando conexao ...");

                Socket clientSocket = listenSocket.accept();

                System.out.println("Cliente conectado ... Criando thread ...");

                ClientThread c = new ClientThread(clientSocket);
                c.start();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

class ClientThread extends Thread {
    public static String rootFile = "C:\\Users\\vitor\\Downloads\\Telegram Desktop";
    File fileName = new File(rootFile);
    File[] files = fileName.listFiles();

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch(IOException ioe) {
            System.out.println("Connection:" + ioe.getMessage());
        }
    }

    @Override
    public void run() {
        String buffer = "", name = "", response = "";

        try {
            buffer = in.readUTF();
            if(buffer.startsWith("NAME:")) {
                name = buffer.substring(5);
                System.out.println("Usuário identificado: " + name);
            }

            while(true) {
                buffer = in.readUTF();
                response = "";

                System.out.println("Cliente " + name + " disse: " + buffer);

                if(buffer.equals("EXIT")) break;
                else if(buffer.equals("TIME")) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    response = dtf.format(now);

                    System.out.print(response);
                    out.writeUTF(response);
                } else if(buffer.equals("DATE")) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
                    LocalDateTime now = LocalDateTime.now();
                    response = dtf.format(now);

                    System.out.print(response);
                    out.writeUTF(response);
                } else if(buffer.equals("FILES")) {
                    out.writeUTF(String.valueOf(files.length));
                    for(File f: files) {
                        out.writeUTF(f.getName());
                    }
                } else if(buffer.startsWith("DOWN ")) {
                    File f = new File(rootFile + "\\" + buffer.substring(5));

                    if(f.isFile() && f.canRead()) {
                        InputStream inStream = new FileInputStream(f);
                        out.writeLong(f.length());
                        byte[] buffered = new byte[1];

                        for(int i = 0; i < f.length(); i++) {
                            inStream.read(buffered);
                            out.write(buffered);
                            out.flush();
                        }
                        inStream.close();
                    } else out.writeLong(0);
                } else {
                    response = buffer + ":OK";

                    System.out.print(response);
                    out.writeUTF(response);
                }
            }
        } catch(EOFException eofe) {
            System.out.println("EOF: " + eofe.getMessage());
        } catch(IOException ioe) {
            System.out.println("IOE: " + ioe.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch(IOException ioe) {
                System.err.println("IOE: " + ioe);
            }
        }
        System.out.println("Comunicação de Thread Cliente " + name + " foi finalizada.");
    }
}
