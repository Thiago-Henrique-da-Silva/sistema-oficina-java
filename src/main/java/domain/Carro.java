package domain;

import java.util.Objects;

public class Carro {
    private static Long ContadorId = 0L;

    private Long id;
    private String marca;
    private String modelo;
    private String placa;

    public Carro(String marca, String modelo, String placa) {
        if (marca == null || marca.isEmpty()) {
            throw new IllegalArgumentException("Marca não pode ser vazio.");
        }

        if (modelo == null || modelo.isEmpty()) {
            throw  new IllegalArgumentException("Modelo não pode ser vazio.");
        }

        if (placa == null || placa.isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser vazio.");
        }

        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.id = ContadorId++;
    }

    public Carro(String modelo, String placa) {
        this.modelo = modelo;
        this.placa = placa;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Carro carro = (Carro) o;
        return Objects.equals(placa, carro.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(placa);
    }

    @Override
    public String toString() {
        return "Carro{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                '}';
    }

    public static Long getContadorId() {
        return ContadorId;
    }

    public static void setContadorId(Long contadorId) {
        ContadorId = contadorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
