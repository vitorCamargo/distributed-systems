public class Message {
    private final byte type;
    private final String source;
    private final String message;

    public Message(byte typeMsg, String nick, String msg) {
        type = typeMsg;
        source = nick;
        message = msg;
    }

    public Message(byte[] msg) {
        int sizeApelido = (int) msg[1];
        
        type = msg[0];
        source = new String(msg, 2, sizeApelido);
        message = new String(msg,2+sizeApelido,msg.length-(sizeApelido+2));
    }

    public byte[] getBytes() {
        int size = 1 + 1 + source.length() + message.length();
        byte[] msg = new byte[size];
        
        msg[0] = type;
        msg[1] = (byte) source.length();
        System.arraycopy(source.getBytes(), 0, msg, 2, source.length());
        System.arraycopy(message.getBytes(), 0, msg, 2+source.length(), message.length());
        
        return msg;
    }

    public byte getType() { return type; }
    public String getSource() { return source; }
    public String getMessage() { return message; }
    
    @Override
    public String toString() {
        return "[ " + this.type + ", " + this.source + ", " + this.message + " ]";
    }
}
