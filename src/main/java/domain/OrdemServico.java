package domain;

import java.util.Objects;

public class OrdemServico {
    private static Long contadorId = 0L;
    private Long id;
    private Cliente cliente;
    private Carro carro;
    private StatusOrdemServico statusOrdemServico;

    public OrdemServico(Cliente cliente, Carro carro) {
        if (cliente == null || carro == null) {
            throw new IllegalArgumentException("cliente/carro nao podem ser null.");
        }

        this.cliente = cliente;
        this.carro = carro;
        this.statusOrdemServico = StatusOrdemServico.INICIADO;
        this.id = contadorId++;
    }

    public OrdemServico(Long id, Cliente cliente, Carro carro, StatusOrdemServico statusOrdemServico) {
        this.id = id;
        this.cliente = cliente;
        this.carro = carro;
        this.statusOrdemServico = statusOrdemServico;
    }

    public void MudarStatusOrdemServico(StatusOrdemServico statusOrdemServico) {
        this.statusOrdemServico = statusOrdemServico;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrdemServico that = (OrdemServico) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrdemServico{" +
                "cliente=" + cliente +
                ", carro=" + carro +
                ", statusOrdemServico=" + statusOrdemServico +
                '}';
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public StatusOrdemServico getStatusOrdemServico() {
        return statusOrdemServico;
    }

    public void setStatusOrdemServico(StatusOrdemServico statusOrdemServico) {
        this.statusOrdemServico = statusOrdemServico;
    }

    public static Long getContadorId() {
        return contadorId;
    }

    public static void setContadorId(Long contadorId) {
        OrdemServico.contadorId = contadorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
