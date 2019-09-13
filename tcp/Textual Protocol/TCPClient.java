/*
    Description: This is the class of the TCP Server for a Textual Protocol.
    Author: Vitor Bueno de Camargo
    Created at: September, 9th. 2019
    Updated at: September, 13th. 2019
*/

package text_tcp;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient {
	public static void main (String args[]) {
        Socket clientSocket = null;
        Scanner reader = new Scanner(System.in);

        try {
            int serverPort = 6666;
            InetAddress serverAddr = InetAddress.getByName("127.0.0.1");

            clientSocket = new Socket(serverAddr, serverPort);

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            String buffer = "";
            String name = "";

            while(name.isEmpty()) {
                System.out.print("Digite seu nome: ");
                name = reader.nextLine();
            }
            out.writeUTF("NAME:" + name);

            while(true) {
                System.out.print("/> ");
                buffer = reader.nextLine();

                out.writeUTF(buffer);

                if(buffer.equals("EXIT")) break;
                else if(buffer.equals("FILES")) {
                    int number = Integer.parseInt(in.readUTF());
                    System.out.println("Servidor disse: são " + number + " arquivos:");
                    for(int i = 0; i < number; i++) {
                        String file = in.readUTF();
                        System.out.println("---> " + file);
                    }
                } else if(buffer.startsWith("DOWN ")) {
                    String fileName = buffer.substring(5);
                    long number = in.readLong();

                    if(number == 0) {
                        System.out.print("Arquivo Inválido");
                    } else {
                        try {
                            DataOutputStream outStream = new DataOutputStream(new FileOutputStream(fileName));

                            byte[] bf = new byte[1];
                            for(int i = 0; i < number; i++) {
                                in.read(bf);
                                outStream.write(bf);
                                outStream.flush();
                            }
                            outStream.close();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Downloaded FILE: " + fileName);
                } else {
                    buffer = in.readUTF();
                    System.out.println("Servidor disse: " + buffer);
                }
            }
        } catch(UnknownHostException ue) {
            System.out.println("Socket:" + ue.getMessage());
        } catch(EOFException eofe) {
            System.out.println("EOF:" + eofe.getMessage());
        } catch(IOException ioe) {
            System.out.println("IO:" + ioe.getMessage());
        } finally {
            try {
                clientSocket.close();
                reader.close();
            } catch(IOException ioe) {
                System.out.println("IO: " + ioe);;
            }
        }
    }
}
