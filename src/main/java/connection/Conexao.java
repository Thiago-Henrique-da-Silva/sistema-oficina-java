package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/SistemaOficinaMecanica";
    private static final String user = "root";
    private static final String password = "senha";

    public static Connection getConexao() {

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("conexão realizada com sucesso!");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
