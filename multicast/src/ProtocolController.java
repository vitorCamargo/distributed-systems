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
    private final ChatGUI ui;

    public ProtocolController(Properties properties) throws IOException {
        mport = (Integer) properties.get("multicastPort");
        uport = (Integer) properties.get("udpPort");
        group = (InetAddress) properties.get("multicastIP");
        nick = (String) properties.get("nickname");
        ui = (ChatGUI) properties.get("UI");

        multicastSocket = new MulticastSocket(mport);
        udpSocket = new DatagramSocket(uport);
        
        onlineUsers = new HashMap<>();
        onlineUsers.put("Todos", group);
    }

    public void send(String targetUser, Message msg) throws IOException {
        if(targetUser.equals("Todos")) this.sendMessageGroup(msg);
        else this.sendMessage(msg, onlineUsers.get(targetUser));
    }

    private void sendMessageGroup(Message msg) throws IOException {
        try {
            byte [] m = msg.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, this.group, 6789);
            /* envia o datagrama como multicast */
            this.multicastSocket.send(messageOut);
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    private void sendMessage(Message msg, InetAddress target) throws IOException {
    }

    public void join() throws IOException {
    }

    public void leave() throws IOException {
        try {
            this.multicastSocket.leaveGroup(this.group);
        } catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if(multicastSocket != null) multicastSocket.close(); //fecha o socket
        }
    }
    
    public void close() throws IOException {
        if(udpSocket != null) udpSocket.close();
        if(multicastSocket != null) multicastSocket.close();
    }

    public void processPacket(DatagramPacket p) throws IOException {
    }

    public void receiveMulticastPacket() throws IOException {
        byte[] buffer = new byte[1000];
        DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
        multicastSocket.receive(messageIn);

        Message msg = new Message(messageIn.getData());
        
        if(msg.getType() == 1) {
            this.ui.writeMessage(msg.getSource(), msg.getMessage());
            this.onlineUsers.put(msg.getSource(), messageIn.getAddress());
            this.ui.addNickname(msg.getSource());
        }
    }

    public void receiveUdpPacket() throws IOException {
    }
}
