package repository;

import connection.Conexao;
import domain.Carro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    public void cadastrarCarro (Carro carro) {
        String sql = "INSERT INTO carro (marca, modelo, placa) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setString(3, carro.getPlaca());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removerCarro (String placa) {
        String sql = "DELETE FROM carro WHERE placa = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,placa);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Carro buscarCarroPlaca (String placa) {
        String sql = "SELECT * FROM carro WHERE placa = ?";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,placa);

            try (ResultSet rs = stmt.executeQuery()) {
                return new Carro(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Carro> listarCarros() {
        String  sql = "SELECT marca, modelo, placa FROM carros";
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carros;
    }

    public boolean existePlaca (String placa) {
        String sql = "SELECT * FROM carro WHERE placa = ?";

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
