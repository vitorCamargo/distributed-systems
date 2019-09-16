package Chat;

/*
    Description: This is the UDP P2P, he is both client and server. The idea is 2 machines execute this with diferent
    names, and create a pipe to comunication with this clients.
    Author: Vitor Bueno de Camargo, Gabriel David Sacca
    Created at: September, 13th. 2019
    Updated at: September, 16th. 2019
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class UDPServer {
    public static void main(String args[]) {
    	DatagramSocket dgramSocket = null;

        Map<String,Integer> dictionary = new HashMap<>();
        dictionary.put("gabriel",6000);
        dictionary.put("vitor",6001);
        dictionary.put("thiago",6002);

        try {
            InetAddress serverAddr = InetAddress.getLocalHost();
            System.out.println("myself: " + args[0]);
            dgramSocket = new DatagramSocket(dictionary.get(args[0]));

            ReceiveThread r = new ReceiveThread(dgramSocket);
            SendThread s = new SendThread(dgramSocket, serverAddr, dictionary, args[0]);
            /* initializes the thread */
            s.start();
            r.start();
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}

class ReceiveThread extends Thread {

    DatagramSocket dgramSocketIn;
    public ReceiveThread(DatagramSocket dgramSocket) {
        this.dgramSocketIn = dgramSocket;

    } //construtor
    /* method performed when starting thread - start() */
    @Override
    public void run() {
        try {
            while(true) {
                byte[] buffer = new byte[1000];

                DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);
                dgramSocketIn.receive(dgramPacket);
                byte[] b = dgramPacket.getData();
                Byte tamNick = b[0];

                Byte tamMsg = b[1 + tamNick.intValue()];

                System.out.println(new String(dgramPacket.getData(), 1, tamNick.intValue()) + ": " + new String(dgramPacket.getData(), 2 + tamNick.intValue(), tamMsg.intValue()));
            }
        } catch (EOFException eofe) {
            System.out.println("EOF: " + eofe.getMessage());
        } catch (IOException ioe) {
            System.out.println("IOE: " + ioe.getMessage());
        }
    } //run
} //class

class SendThread extends Thread {
    Scanner reader = new Scanner(System.in);
    DatagramSocket dgramSocketOut;
    InetAddress adressOut;
    String nick;
    Map<String,Integer> dictionaryOut;

    public SendThread(DatagramSocket dgramSocket, InetAddress adress, Map<String,Integer> dictionary, String mynick) {
        this.dgramSocketOut = dgramSocket;
        this.adressOut = adress;
        this.dictionaryOut = dictionary;
        this.nick = mynick;
    } //constructor
    /* method performed when starting thread - start() */
    @Override
    public void run() {
        try {

            String buffer = "";
            String msg = "";
            int tamMsg;
            String destino = "";
            int tamNick;
            System.out.print("Send to: ");
            destino = reader.nextLine();
            System.out.println("connection established, send your message:");
            while (true) {
                tamNick = nick.getBytes().length;
                msg = reader.nextLine();
                tamMsg = msg.getBytes().length;

                ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();

                // -------------begin of header----------------------

                sendMessage.write((byte) tamNick);
                sendMessage.write((byte[]) nick.getBytes());
                sendMessage.write((byte) tamMsg);
                sendMessage.write((byte[]) msg.getBytes());

                // -------------end of header----------------------

                byte[] m = sendMessage.toByteArray();

                DatagramPacket request = new DatagramPacket(m, m.length, adressOut, dictionaryOut.get(destino));

                dgramSocketOut.send(request);
            }
        } catch (EOFException eofe) {
            System.out.println("EOF: " + eofe.getMessage());
        } catch (IOException ioe) {
            System.out.println("IOE: " + ioe.getMessage());
        }
    } //run
} //class
