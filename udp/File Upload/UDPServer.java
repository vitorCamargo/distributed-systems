/*
    Description: This is the class of the UDP Server for files upload. The idea is for the server get the header first, n-packages for the file and a checksum MD5.
    Author: Vitor Bueno de Camargo, Gabriel David Sacca
    Created at: September, 14th. 2019
    Updated at: September, 16th. 2019
*/

package upload_udp;

import java.net.*;
import java.io.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UDPServer {
    public static void main(String args[]) {
    	DatagramSocket dgramSocket = null;
        try {
            dgramSocket = new DatagramSocket(6666);

            while(true) {
                byte[] buffer = new byte[1024];

                DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
                dgramSocket.receive(dgramPacket);

                String data = new String(dgramPacket.getData(), 0, dgramPacket.getLength());
                String[] parts = data.split(";", 2);
                String fileName = parts[1];
                long fileLength = Long.parseLong(parts[0]);

                long number = fileLength/1024 + 1;

                try {
                    DataOutputStream outStream = new DataOutputStream(new FileOutputStream(new String(fileName)));

                    byte[] bf = new byte[1024];
                    for(int i = 0; i < number; i++) {
                        DatagramPacket dgramFilePacket = new DatagramPacket(bf, bf.length);
                        dgramSocket.receive(dgramFilePacket);

                        byte[] recv = new byte[dgramFilePacket.getLength()];
                        System.arraycopy(bf, 0, recv, 0, dgramFilePacket.getLength());

                        outStream.write(recv);
                        outStream.flush();
                    }
                    outStream.close();

                    byte[] checksum = new byte[1024];
                    DatagramPacket dgramCheckSumPacket = new DatagramPacket(checksum, checksum.length);
                    dgramSocket.receive(dgramCheckSumPacket);

                    InputStream inStream = new FileInputStream(new File(fileName));
                    String oldMD5 = new String(dgramCheckSumPacket.getData(), 0, dgramCheckSumPacket.getLength());
                    String md5 = calculateMD5(inStream.readAllBytes());
                    inStream.close();

                    System.out.println(oldMD5);
                    System.out.println(md5);

                    if(oldMD5.equals(md5)) {
                        DatagramPacket reply = new DatagramPacket("1".getBytes(), "1".getBytes().length, dgramPacket.getAddress(), dgramPacket.getPort());
                        dgramSocket.send(reply);
                    } else {
                        DatagramPacket reply = new DatagramPacket("2".getBytes(), "2".getBytes().length, dgramPacket.getAddress(), dgramPacket.getPort());
                        dgramSocket.send(reply);
                    }
                } catch(IOException e) {
                    e.printStackTrace();

                    DatagramPacket reply = new DatagramPacket("2".getBytes(), "2".getBytes().length, dgramPacket.getAddress(), dgramPacket.getPort());
                    dgramSocket.send(reply);
                }
            }
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            dgramSocket.close();
        }
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
