package app;

import domain.Carro;
import domain.Cliente;
import domain.OrdemServico;
import domain.StatusOrdemServico;
import exception.*;
import service.CarroService;
import service.ClienteService;
import service.OficinaService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClienteService clienteService = new ClienteService();
        CarroService carroService = new CarroService();
        OficinaService oficinaService = new OficinaService();
        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n=== OFICINA ===");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Cadastrar carro");
            System.out.println("3 - Criar ordem de serviço");
            System.out.println("4 - Listar ordens");
            System.out.println("5 - Atualizar status da ordem");
            System.out.println("0 - Sair");

            System.out.print("Opção: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {

                case 1:

                    try {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();

                        System.out.print("CPF: ");
                        String cpf = sc.nextLine();

                        System.out.print("Telefone: ");
                        String telefone = sc.nextLine();

                        clienteService.cadastrarCliente(nome, cpf, telefone);

                        System.out.println("Cliente cadastrado!");

                    } catch (IllegalArgumentException | ClienteJaCadastradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 2:

                    try {
                        System.out.print("Marca: ");
                        String marca = sc.nextLine();

                        System.out.print("Modelo: ");
                        String modelo = sc.nextLine();

                        System.out.print("Placa: ");
                        String placa = sc.nextLine();

                        carroService.cadastrarCarro(marca, modelo, placa);

                        System.out.println("Carro cadastrado!");

                    } catch (IllegalArgumentException | CarroJaCadastradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 3:

                    try {
                        System.out.print("CPF do cliente: ");
                        String cpf = sc.nextLine();

                        System.out.print("Placa do carro: ");
                        String placa = sc.nextLine();

                        Cliente cliente = clienteService.buscarClienteCPF(cpf);
                        Carro carro = carroService.buscarCarroPorPlaca(placa);

                        oficinaService.criarOrdemServico(cliente, carro);

                        System.out.println("Ordem criada!");

                    } catch (IllegalArgumentException | CarroNaoCadastradoException | ClienteNaoCadastradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 4:

                    try {
                        for (OrdemServico ordemServico : oficinaService.listarServicos()) {
                            System.out.println(ordemServico);
                        }

                    } catch (ListaServicosVazia e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 5:

                    try {

                        System.out.print("ID da ordem: ");
                        Long id = Long.parseLong(sc.nextLine());

                        System.out.println("1 - INICIADO");
                        System.out.println("2 - CANCELADO");
                        System.out.println("3 - FINALIZADO");

                        int status = Integer.parseInt(sc.nextLine());

                        StatusOrdemServico novoStatus;

                        switch (status) {
                            case 1:
                                novoStatus = StatusOrdemServico.INICIADO;
                                break;

                            case 2:
                                novoStatus = StatusOrdemServico.CANCELADO;
                                break;

                            case 3:
                                novoStatus = StatusOrdemServico.FINALIZADO;
                                break;

                            default:
                                throw new IllegalArgumentException("Status inválido.");
                        }

                        oficinaService.atualizarOrdemServico(id, novoStatus);
                        System.out.println("Status atualizado!");

                    } catch (StatusOrdemInvalidoServicoException | OrdemServicoNaoExisteException | IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 0:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
    }
}