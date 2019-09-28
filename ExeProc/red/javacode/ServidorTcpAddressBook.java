import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rodrigo
 */

class Connect {
   public static void connect() {
       Connection conn = null;
       try {
           // db parameters
           String url = "jdbc:sqlite:Notas.db";
           // create a connection to the database
           conn = DriverManager.getConnection(url);
           
           System.out.println("Connection to SQLite has been established.");
           
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       } finally {
           try {
               if (conn != null) {
                   conn.close();
               }
           } catch (SQLException ex) {
               System.out.println(ex.getMessage());
           }
       }
   }

}

public class ServidorTcpAddressBook {

    public static void main(String args[]) {
        try {
            Connect.connect();
            int serverPort = 7000; // porta do servidor
            /* cria um socket e mapeia a porta para aguardar conexão */
            ServerSocket listenSocket = new ServerSocket(serverPort);

            //while (true) {
                System.out.println("Servidor aguardando conexão ...");

                /* aguarda conexões */
                Socket clientSocket = listenSocket.accept();

                System.out.println("Cliente conectado ...");
                
                /* recebe os dados enviados pelo cliente*/
                DataInputStream inClient = new DataInputStream(clientSocket.getInputStream());
                InputStreamReader i;
                
                String valueStr = inClient.readLine();
                System.out.println("Valor lido:" + valueStr);
                
                int sizeBuffer = Integer.valueOf(valueStr);
                
                byte[] buffer = new byte[sizeBuffer];
                inClient.read(buffer);
                System.out.println(Arrays.toString(buffer));
                
                /* realiza o unmarshalling */
                Addressbook.Person p = Addressbook.Person.parseFrom(buffer);
                
                /* exibe na tela */
                System.out.println("--\n" + p + "--\n");
                
                
            //} //while

        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        } //catch
    } //main
} //class    

