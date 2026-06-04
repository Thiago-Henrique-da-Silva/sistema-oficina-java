package service;

import domain.Carro;
import domain.Cliente;
import domain.OrdemServico;
import domain.StatusOrdemServico;
import exception.ListaServicosVazia;
import exception.StatusOrdemInvalidoServicoException;
import repository.CarroDAO;
import repository.ClienteDAO;
import repository.OficinaDAO;

import java.util.*;

public class OficinaService {
    private OficinaDAO oficinaDAO = new OficinaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private CarroDAO carroDAO = new CarroDAO();

    public void criarOrdemServico(Cliente cliente,Carro carro) {
        oficinaDAO.criarOrdemServico(cliente,carro);
    }

    public void atualizarOrdemServico(Long id, StatusOrdemServico novoStatus) {
        oficinaDAO.atualizarOrdemServico(id, novoStatus);
    }

    public List<OrdemServico> listarServicos() {
        List<OrdemServico> listaServicos = oficinaDAO.listarOrdemServico();

        if (listaServicos.isEmpty()){
            throw new ListaServicosVazia("lista de servicos vazia");
        }

        return listaServicos;
    }


}
