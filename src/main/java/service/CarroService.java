package service;

import domain.Carro;
import exception.CarroJaCadastradoException;
import exception.CarroNaoCadastradoException;
import exception.ListaCarrosVaziaException;
import repository.CarroDAO;

import java.util.*;

public class CarroService {
    private CarroDAO carroDAO = new CarroDAO();
    public void cadastrarCarro(String marca, String modelo, String placa) {
        if (carroDAO.existePlaca(placa)) {
            throw new CarroJaCadastradoException("carro ja cadastrado");
        }

        Carro carro = new Carro(marca, modelo, placa);
        carroDAO.cadastrarCarro(carro);
    }

    public void removerCarroCliente(String placa) {
        if (!carroDAO.existePlaca(placa)) {
            throw new CarroNaoCadastradoException("carro não encontrado.");
        }

        carroDAO.removerCarro(placa);
    }

    public Carro buscarCarroPorPlaca(String placa) {
        if (placa == null || placa.isEmpty()) {
            throw new IllegalArgumentException("placa nao pode ser vazia.");
        }

        if (!carroDAO.existePlaca(placa)) {
            throw new CarroNaoCadastradoException("nenhum carro cadastrado com este placa.");
        }

        return carroDAO.buscarCarroPlaca(placa);
    }

    public List<Carro> listarCarros() {
        List<Carro> carros = carroDAO.listarCarros();

        if (carros.isEmpty()) {
            throw new ListaCarrosVaziaException("lista de caarros vazia.");
        }

        return  carros;
    }
}
