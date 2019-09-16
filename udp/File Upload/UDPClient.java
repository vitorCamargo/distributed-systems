/*
    Description: This is the class of the UDP Client for files upload. The idea is for the client send the header (with the length and name of the file) first, n-packages for the file content and a checksum MD5.
    Author: Vitor Bueno de Camargo
    Created at: September, 14th. 2019
    Updated at: September, 16th. 2019
*/

package upload_udp;

import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.file.*;

public class UDPClient {
    public static void main(String args[]) {
        DatagramSocket dgramSocket;
        int resp = 0;

        try {
            String rootFile = "C:\\Users\\vitor\\Downloads\\Telegram Desktop";
            File fileName = new File(rootFile);
            File[] listFiles = fileName.listFiles();
            String[] files = getAllFiles(listFiles);

            dgramSocket = new DatagramSocket();

            // String dstIP = JOptionPane.showInputDialog("IP Destino?");
            // int dstPort = Integer.parseInt(JOptionPane.showInputDialog("Porta Destino?"));
            String dstIP = "127.0.0.1";
            int dstPort = 6666;

            InetAddress serverAddr = InetAddress.getByName(dstIP);
            int serverPort = dstPort;

            do {
                String chosenFile = (String) JOptionPane.showInputDialog(null, "Escolha um arquivo para Upload", "Escolha de Arquivo", JOptionPane.QUESTION_MESSAGE, null, files, files[0]);

                File f = new File(rootFile + "\\" + chosenFile);

                InputStream inStream = new FileInputStream(f);
                ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();
                
                sendMessage.write((byte[]) String.valueOf(f.length()).getBytes());
                sendMessage.write((byte[]) (";" + chosenFile).getBytes());

                long number = f.length()/1024 + 1;

                byte[] m = sendMessage.toByteArray();
                DatagramPacket request = new DatagramPacket(m, m.length, serverAddr, serverPort);
                dgramSocket.send(request);

                byte[] fileArray = inStream.readAllBytes();
                inStream.close();

                String md5 = calculateMD5(fileArray);
                System.out.println(md5);

                for(int i = 0; i < number; i++) {
                    int maxContentSize = i * 1024 + 1024;
                    if(maxContentSize > fileArray.length) maxContentSize = fileArray.length;

                    byte[] fileMsg = new byte[(maxContentSize) - (i * 1024)];
                    System.arraycopy(fileArray, i * 1024, fileMsg, 0, maxContentSize - i * 1024);

                    DatagramPacket fileRequest = new DatagramPacket(fileMsg, fileMsg.length, serverAddr, serverPort);
                    dgramSocket.send(fileRequest);
                }

                DatagramPacket checkSumRequest = new DatagramPacket(md5.getBytes(), md5.getBytes().length, serverAddr, serverPort);
                dgramSocket.send(checkSumRequest);

                byte[] buffer = new byte[1024];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

                dgramSocket.receive(reply);
                String response = new String(reply.getData(), 0, reply.getLength());

                if(response.equals("1")) resp = JOptionPane.showConfirmDialog(null, "Arquivo enviado com Sucesso, enviar outro?", "Continuar", JOptionPane.YES_NO_OPTION);
                else resp = JOptionPane.showConfirmDialog(null, "Falha ao enviar arquivo, enviar outro?", "Continuar", JOptionPane.YES_NO_OPTION);
            } while(resp != JOptionPane.NO_OPTION);

            dgramSocket.close();
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    public static String[] getAllFiles(File[] listFiles) {
        String[] files = new String[listFiles.length];
        int i = 0;

        for(File f: listFiles) {
            files[i] = f.getName();
            i++;
        }

        return files;
    }

    private static String calculateMD5(byte[] fileArray) { // Calculate the MD5 Checksum for the file
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(fileArray);
            byte[] digest = md.digest();
            String myChecksum = bytesToHex(digest);

            return myChecksum;
        } catch(NoSuchAlgorithmException e) {
            System.err.println("Could not generate MD5 hash!");
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] hashInBytes) { // Transform the hash into a hex string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashInBytes.length; i++) {
            sb.append(Integer.toString((hashInBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
