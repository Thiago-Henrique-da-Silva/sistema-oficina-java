package repository;

import connection.Conexao;
import domain.Carro;
import domain.Cliente;
import domain.OrdemServico;
import domain.StatusOrdemServico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OficinaDAO {

    public void criarOrdemServico (Cliente cliente, Carro carro) {
        String sql = "INSERT INTO ordem_servico (cliente_id, carro_id) VALUES (?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1,cliente.getId());
            stmt.setLong(2,carro.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizarOrdemServico (Long id, StatusOrdemServico novoStatus) {
        String sql = "UPDATE ordem_servico SET status = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,novoStatus.name());
            stmt.setLong(2,id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrdemServico> listarOrdemServico() {
        String sql = "SELECT cl.nome,cl.cpf, ca.modelo, ca.placa, os.id, os.status FROM OrdemServico AS os " +
                     "JOIN Clientes AS cl ON os.cliente_id = cl.id " +
                     "JOIN Carros AS ca ON os.carro_id = ca.id";
        List<OrdemServico> ordensServicos = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf")
                );
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("placa")
                );
                ordensServicos.add(new OrdemServico(
                        rs.getLong("id"),
                        cliente,
                        carro,
                        StatusOrdemServico.valueOf(rs.getString("status"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ordensServicos;
    }
}
