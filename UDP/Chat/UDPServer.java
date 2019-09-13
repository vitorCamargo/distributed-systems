package chat_udp;

import java.net.*;
import java.io.*;

public class UDPServer {
    public static void main(String args[]) {
    	DatagramSocket dgramSocket = null;
        try {
            dgramSocket = new DatagramSocket(6666);

            while(true) {
                byte[] buffer = new byte[1000];

                DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
                dgramSocket.receive(dgramPacket);

                System.out.println("Cliente: " + new String(dgramPacket.getData(), 0, dgramPacket.getLength()));    
                DatagramPacket reply = new DatagramPacket(dgramPacket.getData(), dgramPacket.getLength(), dgramPacket.getAddress(), dgramPacket.getPort());

                dgramSocket.send(reply);
            }
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            dgramSocket.close();
        }
    }
}
