/*
    Description: This is the class of the TCP Client for a Binary Protocol.
    Author: Vitor Bueno de Camargo
    Created at: September, 12th. 2019
    Updated at: September, 13th. 2019
*/

package binary_tcp;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
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

            while(true) {
                System.out.print("/> ");
                buffer = reader.nextLine();

                if(buffer.startsWith("ADDFILE ")) {
                    String fileName = buffer.substring(8);
                    File f = new File(fileName);

                    if(f.isFile() && f.canRead()) {
                        InputStream inStream = new FileInputStream(f);
                        ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();

                        sendMessage.write((byte) 1);
                        sendMessage.write((byte) 1);
                        sendMessage.write((byte) fileName.length());
                        sendMessage.write((byte[]) fileName.getBytes());
                        sendMessage.write(longToByteArray(f.length()));
                        sendMessage.write(inStream.readAllBytes());

                        inStream.close();

                        out.write(sendMessage.toByteArray());

                        byte[] response = new byte[3];
                        in.read(response);

                        if(response[2] == 1) System.out.println("SUCESSO");
                        else System.out.println("ERRO");
                    } else System.out.print("Arquivo Inválido");
                } else if(buffer.startsWith("DELETE ")) {
                    String fileName = buffer.substring(7);

                    ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();

                    sendMessage.write((byte) 1);
                    sendMessage.write((byte) 2);
                    sendMessage.write((byte) fileName.length());
                    sendMessage.write((byte[]) fileName.getBytes());

                    out.write(sendMessage.toByteArray());

                    byte[] response = new byte[3];
                    in.read(response);

                    if(response[2] == 1) System.out.println("SUCESSO");
                    else System.out.println("ERRO");
                } else if(buffer.equals("GETFILESLIST")) {
                    ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();

                    sendMessage.write((byte) 1);
                    sendMessage.write((byte) 3);

                    out.write(sendMessage.toByteArray());

                    byte[] response = new byte[3];
                    in.read(response);

                    if(response[2] == 1) {
                        byte[] number = new byte[1];
                        in.read(number);

                        System.out.println("Número de Arquivos: " + number[0]);

                        for(int i = 0; i < number[0]; i++) {
                            byte fileNameLength = in.readByte();

                            byte[] fileName = new byte[fileNameLength];
                            in.read(fileName, 0, fileNameLength);
                            System.out.println("---> " + new String(fileName));
                        }
                    } else System.out.println("ERRO");
                } else if(buffer.startsWith("GETFILE ")) {
                    String fileName = buffer.substring(8);

                    ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();

                    sendMessage.write((byte) 1);
                    sendMessage.write((byte) 4);
                    sendMessage.write((byte) fileName.length());
                    sendMessage.write((byte[]) fileName.getBytes());

                    out.write(sendMessage.toByteArray());

                    byte[] response = new byte[3];
                    in.read(response);

                    if(response[2] == 1) {
                        DataOutputStream outStream = new DataOutputStream(new FileOutputStream(fileName));

                        try {
                            long fileLength = in.readLong();
    
                            byte[] bf = new byte[1];
                            for(int i = 0; i < fileLength; i++) {
                                in.read(bf);
                                outStream.write(bf);
                                outStream.flush();
                            }
                            outStream.close();
                            System.out.println("SUCESSO");
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    } else System.out.println("ERRO");
                } else {
                    System.out.println("Comando não Reconhecido");
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

    private static byte[] longToByteArray(final long i) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeLong(i);
        dos.flush();
        return bos.toByteArray();
    }
}
