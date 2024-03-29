/*
    Description: This is the Multicast chat. The idea is someone hosts the communication between users.
    Author: Vitor Bueno de Camargo, Gabriel David Sacca
    Created at: September, 18th. 2019
    Updated at: September, 22th. 2019
*/

import java.io.IOException;

public class Listener extends Thread {
    Thread multicastListener, udpListener;
    ProtocolController protoController;

    public Listener(ProtocolController protoController) {
        this.protoController = protoController;
    }

    public void run() {
        multicastListener = new Thread(() -> {
            try {
                while(true) {
                    protoController.receiveMulticastPacket();
                }
            } catch(IOException ex) {
                System.out.println(ex);
            }
        });

        udpListener = new Thread(() -> {
            try {
                while(true) {
                    protoController.receiveUdpPacket();
                }
            } catch(IOException ex) {
                System.out.println(ex);
            }
        });

        multicastListener.start();
        udpListener.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}

