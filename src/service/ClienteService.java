package service;

import domain.Cliente;
import exception.ClienteJaCadastradoException;
import exception.ClienteNaoCadastradoException;
import exception.ListaClientesVaziaException;

import java.util.*;

public class ClienteService {
    private Map<String, Cliente> clientesMap = new HashMap<>();

    public void cadastrarCliente(String nome, String cpf,String telefone) {
        if (clientesMap.containsKey(cpf)) {
            throw new ClienteJaCadastradoException("cliente já está cadastrado.");
        }

        Cliente cliente = new Cliente(nome, cpf, telefone);
        clientesMap.put(cpf, cliente);
    }

    public Cliente buscarClienteCPF(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }

        if (!clientesMap.containsKey(cpf)) {
            throw new ClienteNaoCadastradoException("nenhum cliente encontrado");
        }

        return clientesMap.get(cpf);

    }

    public Collection<Cliente> listaDeClientes(){
        if (clientesMap.isEmpty()) {
            throw new ListaClientesVaziaException("Lista de clientes vazia.");
        }

        return  clientesMap.values();
    }
}
