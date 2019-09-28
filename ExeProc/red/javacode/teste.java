import java.sql.*;

public class teste {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Notas";
      String login = "root";
      String senha = "root";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("\nDriver carregado com sucesso!\n");
            try {
                    Connection conn = DriverManager.getConnection(url, login, senha);
                try {
                    String sql = "SELECT cod_curso,nome FROM Notas.Aluno";
                    Statement stm = conn.createStatement();
                    try {
                        ResultSet rs = stm.executeQuery(sql);
                        while (rs.next()) {
                            String nome = rs.getString("nome");
                            String cod_curso = rs.getString("cod_curso");
                            System.out.println("cod_curso: " + cod_curso + "\nNome: " +nome);
                            System.out.println("---------------------------------------");
                        }
                        System.out.println("\nConsulta realizada com sucesso!!!\n");                     
                    } catch (Exception ex) {
                        System.out.println("\nErro no resultset!");
                    }
                } catch (Exception ex) {
                    System.out.println("\nErro no statement!");
                }
            } catch (Exception ex) {
                System.out.println("\nErro no connection!");
            }   
        } catch (Exception ex) {
            System.out.println("\nDriver nao pode ser carregado!");
        }
    }
}