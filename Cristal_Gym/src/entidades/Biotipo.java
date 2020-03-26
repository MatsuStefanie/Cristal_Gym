package entidades;

// classe refetente a tabela "biotipo" do BD
public class Biotipo {

    private Integer id;
    private Float altura;
    private Float peso;
    private Integer idCliente;

    public Biotipo(Integer id, Float altura, Float peso, Integer idCliente) {
        this.id = id;
        this.altura = altura;
        this.peso = peso;
        this.idCliente = idCliente;
    }

    public Integer getId() {
        return id;
    }

    public Float getAltura() {
        return altura;
    }

    public Float getPeso() {
        return peso;
    }

    public Integer getIdCliente(){
        return idCliente;
    }

    @Override
    public String toString() {
        return "Altura: " + this.getAltura() + " Peso: " +this.getPeso();
    }
}