package repository;

import connection.Conexao;
import domain.Carro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    public void cadastrarCarro (Carro carro) {
        String sql = "INSERT INTO Carros (marca, modelo, placa) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setString(3, carro.getPlaca());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    carro.setId(rs.getLong("id"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removerCarro (String placa) {
        String sql = "DELETE FROM Carros WHERE placa = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,placa);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Carro buscarCarroPlaca (String placa) {
        String sql = "SELECT * FROM Carros WHERE placa = ?";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,placa);

            try (ResultSet rs = stmt.executeQuery()) {
               if (rs.next()) {
                   return new Carro(
                           rs.getString("marca"),
                           rs.getString("modelo"),
                           rs.getString("placa")
                   );
               }
               return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Carro> listarCarros() {
        String  sql = "SELECT marca, modelo, placa FROM Carros";
        List<Carro> carros = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                carros.add(new Carro(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa")
                ));
            }

            return carros;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existePlaca (String placa) {
        String sql = "SELECT * FROM Carros WHERE placa = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,placa);

            try (ResultSet rs = stmt.executeQuery()) {
                return (rs.next());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
