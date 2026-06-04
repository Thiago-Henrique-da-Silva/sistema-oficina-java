package service;

import repository.ClienteDAO;
import domain.Cliente;
import exception.ClienteJaCadastradoException;
import exception.ClienteNaoCadastradoException;
import exception.ListaClientesVaziaException;

import java.util.*;

public class ClienteService {
    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastrarCliente(String nome, String cpf,String telefone) {
        if (clienteDAO.existeCPF(cpf)) {
            throw new ClienteJaCadastradoException("cliente já está cadastrado.");
        }

        Cliente cliente = new Cliente(nome, cpf, telefone);
        clienteDAO.cadastrarCliente(cliente);
    }

    public void removerCliente(String cpf) {
        if (cpf.isEmpty()) {
            throw new IllegalArgumentException("cpf invalido.");
        }

        if (!clienteDAO.existeCPF(cpf)) {
            throw new ClienteNaoCadastradoException("cliente não encontrado.");
        }

        clienteDAO.removerCliente(cpf);
    }

    public Cliente buscarClienteCPF(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }

        if (!clienteDAO.existeCPF(cpf)) {
            throw new ClienteNaoCadastradoException("nenhum cliente encontrado.");
        }

        return clienteDAO.buscarClientePorCpf(cpf);
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteDAO.listarClientes();

        if (clientes.isEmpty()) {
            throw new ListaClientesVaziaException("Lista de clientes vazia");
        }

        return clientes;
    }
}
