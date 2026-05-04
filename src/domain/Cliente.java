package domain;

import java.util.Objects;

public class Cliente {
    private static Long contadorId = 0L;

    private Long id;
    private String nome;
    private String cpf;
    private String telefone;

    private Cliente() {};

    public Cliente(String nome, String cpf, String telefone) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }

        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }

        //negando ponto e virgula,ou qualquer coisa que não seja numero.
        cpf = cpf.replaceAll("[^0-9]", "");

        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.id = contadorId++;
    }

    //comparando pelo cpf pois id é gerado automaticamente.
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

    public static Long getContadorId() {
        return contadorId;
    }

    public static void setContadorId(Long contadorId) {
        Cliente.contadorId = contadorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
