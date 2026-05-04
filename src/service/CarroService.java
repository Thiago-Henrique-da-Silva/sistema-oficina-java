package service;

import domain.Carro;
import exception.CarroJaCadastradoException;
import exception.CarroNaoCadastradoException;
import exception.ListaCarrosVaziaException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CarroService {
    private Map<String,Carro> carrosMap = new HashMap<>();

    public void cadastrarCarro(String marca, String modelo, String placa) {
        if (carrosMap.containsKey(placa)) {
            throw new CarroJaCadastradoException("carro ja cadastrado.");
        }

        Carro carro = new Carro(marca, modelo, placa);
        carrosMap.put(placa, carro);
    }

    public Carro buscarCarroPorPlaca(String placa) {
        if (placa == null || placa.isEmpty()) {
            throw new IllegalArgumentException("placa nao pode ser vazia.");
        }

        if (!carrosMap.containsKey(placa)) {
            throw new CarroNaoCadastradoException("nenhum carro cadastrado com este placa.");
        }

        return  carrosMap.get(placa);
    }

    public Collection<Carro> listarCarros() {
        if (carrosMap.isEmpty()) {
            throw new ListaCarrosVaziaException("lista de carros vazia.");
        }

        return carrosMap.values();
    }
}
