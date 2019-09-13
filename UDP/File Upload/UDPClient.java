package chat_udp;

import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class UDPClient {
    public static void main(String args[]) {
        DatagramSocket dgramSocket;
        int resp = 0;

        try {
            dgramSocket = new DatagramSocket();
            
            String dstIP = JOptionPane.showInputDialog("IP Destino?");
            int dstPort = Integer.parseInt(JOptionPane.showInputDialog("Porta Destino?"));

            InetAddress serverAddr = InetAddress.getByName(dstIP);
            int serverPort = dstPort;

            do {
                String msg = JOptionPane.showInputDialog("Mensagem?");

                byte[] m = msg.getBytes();

                DatagramPacket request = new DatagramPacket(m, m.length, serverAddr, serverPort);

                dgramSocket.send(request);

                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

                dgramSocket.receive(reply);
                System.out.println("Resposta: " + new String(reply.getData(),0,reply.getLength()));

                resp = JOptionPane.showConfirmDialog(null, "Nova mensagem?", "Continuar", JOptionPane.YES_NO_OPTION);
            } while(resp != JOptionPane.NO_OPTION);

            dgramSocket.close();
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }		      	
}
