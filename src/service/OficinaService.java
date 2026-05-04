package service;

import domain.Carro;
import domain.Cliente;
import domain.OrdemServico;
import domain.StatusOrdemServico;
import exception.ListaServicosVazia;
import exception.StatusOrdemInvalidoServicoException;

import java.util.*;

public class OficinaService {
    private List<OrdemServico> servicos = new ArrayList<>();

    public void criarOrdemServico(Cliente cliente,Carro carro) {
        OrdemServico ordemServico = new OrdemServico(cliente, carro);
        servicos.add(ordemServico);
    }

    public void atualizarOrdemServico(Long id, StatusOrdemServico novoStatus) {
        for (OrdemServico ordemServico : servicos) {
            if (ordemServico.getId().equals(id)) {
                if (ordemServico.getStatusOrdemServico().equals(novoStatus)) {
                    throw new StatusOrdemInvalidoServicoException("status deve ser difente do anterior.");
                }

                ordemServico.setStatusOrdemServico(novoStatus);
                return;
            }
        }

        throw new RuntimeException("ordem de serviço nao encontrada.");
    }

    public List<OrdemServico> listarServicos() {
        if (servicos.isEmpty()) {
            throw new ListaServicosVazia("lista de serviços vazia");
        }

        return servicos;
    }


}
