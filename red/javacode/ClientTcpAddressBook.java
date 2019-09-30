import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Gabriel David Sacca
 */

public class ClientTcpAddressBook {

    public static void main(String args[]) {
        Socket clientSocket = null; // socket do cliente
        Scanner reader = new Scanner(System.in);
        
        try{
            /* Endereço e porta do servidor */
            int serverPort = 7001;   
            InetAddress serverAddr = InetAddress.getByName("127.0.0.1");
            
            /* conecta com o servidor */  
            clientSocket = new Socket(serverAddr, serverPort);  
            
            /* cria objetos de leitura e escrita */
            DataInputStream in = new DataInputStream( clientSocket.getInputStream());
            DataOutputStream out =new DataOutputStream( clientSocket.getOutputStream());
        
            /* protocolo de comunicação */
            MatriculaOuterClass.Mess.Builder message = MatriculaOuterClass.Mess.newBuilder();
            
            String buffer = "";
            while(true) {
                System.out.print("/> ");
                buffer = reader.nextLine();


                if(buffer.equals("EXIT")) break;
                if(buffer.equals("HELP")){
                    System.out.println("POST [RA] [cod_disciplina] [ano] [semestre] [nota] [falta]");
                    System.out.println("PUT [RA] [cod_disciplina] [ano] [semestre] [nota]");
                    System.out.println("REMOVE [RA] [cod_disciplina] [ano] [semestre]");
                    System.out.println("GETMYNOTA [RA]");
                    System.out.println("GETNOTABYSEMESTRE [cod_disciplina] [ano]");
                    System.out.println("GETALUNOSBYANO [cod_disciplina] [ano] [semestre]");
                } else {
                    message.setMess(buffer);
                    if(buffer.startsWith("POST ")){
                        String[] request = buffer.split(" ");
                        if(request.length == 7) {
                            System.out.println("deu tudo certo aqui");
                        } else{
                            System.out.println("deu errado aqui");
                        }
                    }
                    if(buffer.startsWith("PUT ")){
                        String[] request = buffer.split(" ");
                        if(request.length == 6) {
                            System.out.println("deu tudo certo aqui");
                        } else{
                            System.out.println("deu errado aqui");
                        }
                    }
                    if(buffer.startsWith("REMOVE ")){
                        String[] request = buffer.split(" ");
                        if(request.length == 5) {
                            System.out.println("deu tudo certo aqui");
                        } else{
                            System.out.println("deu errado aqui");
                        }
                    }
                    if(buffer.startsWith("GETMYNOTA ")){
                        String[] request = buffer.split(" ");
                        if(request.length == 2) {
                            System.out.println("deu tudo certo aqui");
                        } else{
                            System.out.println("deu errado aqui");
                        }
                    }
                    if(buffer.startsWith("GETNOTABYSEMESTRE ")){
                        String[] request = buffer.split(" ");
                        if(request.length == 3) {
                            System.out.println("deu tudo certo aqui");
                        } else{
                            System.out.println("deu errado aqui");
                        }
                    }
                    if(buffer.startsWith("GETALUNOSBYANO ")){
                        String[] request = buffer.split(" ");
                        if(request.length == 4) {
                            System.out.println("deu tudo certo aqui");
                        } else{
                            System.out.println("deu errado aqui");
                        }
                    }
                    String msg = message.toString();
                    out.writeUTF(msg);
                }
            }
            // msg = in.read();      // aguarda resposta do servidor
            // System.out.println("Server disse: " + msg);
	    } catch (UnknownHostException ue){
		    System.out.println("Socket:" + ue.getMessage());
        } catch (EOFException eofe){
            System.out.println("EOF:" + eofe.getMessage());
        } catch (IOException ioe){
            System.out.println("IO:" + ioe.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ioe) {
                System.out.println("IO: " + ioe);;
            }
        }
    } //main
} //class    

