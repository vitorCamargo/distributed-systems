import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Properties;

public class ProtocolController {
    private final MulticastSocket multicastSocket;
    private final DatagramSocket udpSocket;
    private final InetAddress group;
    private final Integer mport, uport;
    private final String nick;
    private final HashMap<String, InetAddress> onlineUsers;
    private final UIControl ui;

    public ProtocolController(Properties properties) throws IOException {
        mport = (Integer) properties.get("multicastPort");
        uport = (Integer) properties.get("udpPort");
        group = (InetAddress) properties.get("multicastIP");
        nick = (String) properties.get("nickname");
        ui = (UIControl) properties.get("UI");

        multicastSocket = new MulticastSocket(mport);
        udpSocket = new DatagramSocket(uport);
        
        multicastSocket.joinGroup(group);

        onlineUsers = new HashMap<>();
        onlineUsers.put("Todos", group);
    }

    public void send(String targetUser, String msg) throws IOException {
        if(targetUser.equals("Todos")) {
            Message messageSent = new Message((byte) 3, this.nick, msg);
            this.sendMessageGroup(messageSent);
        } else {
            Message messageSent = new Message((byte) 4, this.nick, msg);
            this.sendMessage(messageSent, onlineUsers.get(targetUser));
        }
    }

    private void sendMessageGroup(Message msg) throws IOException {
        try {
            byte [] m = this.criptografa(msg.getBytes());
            DatagramPacket messageOut = new DatagramPacket(m, m.length, this.group, 6789);

            this.multicastSocket.send(messageOut);
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    private void sendMessage(Message msg, InetAddress target) throws IOException {
        try {
            byte[] m = this.criptografa(msg.getBytes());
            DatagramPacket request = new DatagramPacket(m, m.length, target, 6799);
            this.udpSocket.send(request);
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    public void join() throws IOException {
        Message messageInitial = new Message((byte) 1, this.nick, "");
        this.sendMessageGroup(messageInitial);
    }

    public void leave() throws IOException {
        try {
            Message msg = new Message((byte) 5, this.nick, "");
            this.sendMessageGroup(msg);

            this.multicastSocket.leaveGroup(this.group);
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if(multicastSocket != null) multicastSocket.close();
        }
    }
    
    public void close() throws IOException {
        if(udpSocket != null) udpSocket.close();
        if(multicastSocket != null) multicastSocket.close();
    }

    public void receiveMulticastPacket() throws IOException {
        byte[] buffer = new byte[1000];
        DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);

        multicastSocket.receive(messageIn);

        Message msg = new Message(this.criptografa(messageIn.getData()));

        if(!msg.getSource().equals(this.nick)) {
            if(msg.getType() == 1) {
                this.onlineUsers.put(msg.getSource(), messageIn.getAddress());

                Message msgJoinACK = new Message((byte) 2, this.nick, "");
                this.sendMessage(msgJoinACK, messageIn.getAddress());
            } else if(msg.getType() == 5) {
                this.onlineUsers.remove(msg.getSource());
            }
        }

            this.ui.update(msg);
    }

    public void receiveUdpPacket() throws IOException {
        byte[] buffer = new byte[1000];
        DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);

        udpSocket.receive(messageIn);

        Message msg = new Message(this.criptografa(messageIn.getData()));

        if(msg.getType() == 2) {
            this.onlineUsers.put(msg.getSource(), messageIn.getAddress());
        }

        this.ui.update(msg);
    }

    private byte[] criptografa(byte[] message) {
        for(int i = 0; i < message.length; i += 2) {
            if(i < message.length - 1) {
                byte aux = message[i];
                message[i] = message[i + 1];
                message[i + 1] = aux;
            }
        }

        return message;
    }
}
