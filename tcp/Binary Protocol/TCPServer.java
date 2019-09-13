/*
    Description: This is the class of the TCP Server for a Binary Protocol.
    Author: Vitor Bueno de Camargo
    Created at: September, 12th. 2019
    Updated at: September, 13th. 2019
*/

package binary_tcp;

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
        byte[] comm = new byte[2];
        try {
            while(true) {
                in.read(comm);

                if(comm[1] == 1) {
                    System.out.println("Cliente disse: ADDFILE");
                    ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();

                    sendMessage.write((byte) 2);
                    sendMessage.write((byte) 1);

                    byte[] fileNameLength = new byte[1];
                    in.read(fileNameLength);

                    byte[] fileName = new byte[fileNameLength[0]];
                    in.read(fileName);

                    long fileLength = in.readLong();

                    try {
                        DataOutputStream outStream = new DataOutputStream(new FileOutputStream(rootFile + "\\" + new String(fileName)));

                        byte[] bf = new byte[1];
                        for(int i = 0; i < fileLength; i++) {
                            in.read(bf);
                            outStream.write(bf);
                            outStream.flush();
                        }
                        outStream.close();

                        sendMessage.write((byte) 1);
                        out.write(sendMessage.toByteArray());
                    } catch(IOException e) {
                        e.printStackTrace();

                        sendMessage.write((byte) 2);
                        out.write(sendMessage.toByteArray());
                    }
                } else if(comm[1] == 2) {
                    System.out.println("Cliente disse: DELETE");
                    ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();

                    sendMessage.write((byte) 2);
                    sendMessage.write((byte) 2);

                    byte[] fileNameLength = new byte[1];
                    in.read(fileNameLength);

                    byte[] fileName = new byte[fileNameLength[0]];
                    in.read(fileName);

                    File file = new File(rootFile + "\\" + new String(fileName)); 
          
                    if(file.delete()) {
                        sendMessage.write((byte) 1);
                        out.write(sendMessage.toByteArray());
                    } else {
                        sendMessage.write((byte) 2);
                        out.write(sendMessage.toByteArray());
                    }
                } else if(comm[1] == 3) {
                    System.out.println("Cliente disse: GETFILESLIST");
                    ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();

                    sendMessage.write((byte) 2);
                    sendMessage.write((byte) 3);
                    sendMessage.write((byte) 1);
                    sendMessage.write((byte) files.length);

                    out.write(sendMessage.toByteArray());

                    for(File f: files) {
                        ByteArrayOutputStream sendFile = new ByteArrayOutputStream();

                        sendFile.write(f.getName().length());
                        sendFile.write((byte[]) f.getName().getBytes());

                        out.write(sendFile.toByteArray());
                        out.flush();
                    }
                } else if(comm[1] == 4) {
                    System.out.println("Cliente disse: GETFILE");
                    ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();

                    sendMessage.write((byte) 2);
                    sendMessage.write((byte) 4);

                    byte[] fileNameLength = new byte[1];
                    in.read(fileNameLength);

                    byte[] fileName = new byte[fileNameLength[0]];
                    in.read(fileName);

                    File f = new File(rootFile + "\\" + new String(fileName));

                    if(f.isFile() && f.canRead()) {
                        InputStream inStream = new FileInputStream(f);

                        sendMessage.write((byte) 1);
                        sendMessage.write(longToByteArray(f.length()));
                        sendMessage.write(inStream.readAllBytes());

                        inStream.close();

                        out.write(sendMessage.toByteArray());
                    } else {
                        sendMessage.write((byte) 2);
                        out.write(sendMessage.toByteArray());
                    }
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
        System.out.println("Comunicação de Thread Cliente foi finalizada.");
    }

    private static byte[] longToByteArray(final long i) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeLong(i);
        dos.flush();
        return bos.toByteArray();
    }
}
