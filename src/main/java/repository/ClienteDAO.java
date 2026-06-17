package repository;

import connection.Conexao;
import domain.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void cadastrarCliente (Cliente cliente) {
        String sql = "INSERT INTO Clientes (nome, cpf, telefone) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if(rs.next()) {
                    cliente.setId(rs.getLong("id"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removerCliente (String cpf) {
        String sql = "DELETE FROM Clientes WHERE cpf = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente buscarClientePorCpf(String cpf) {
        String sql = "SELECT * FROM Clientes WHERE cpf = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("telefone")
                    );
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> listarClientes() {
        String sql = "SELECT nome, cpf, telefone FROM Clientes ORDER BY nome ASC";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();) {

            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"));
                clientes.add(cliente);
            }

            return clientes;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existeCPF(String cpf) {
        String sql = "SELECT * FROM Clientes WHERE cpf = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}