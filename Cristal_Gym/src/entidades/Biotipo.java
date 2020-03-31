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

    public Biotipo(){

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Biotipo(Integer idCliente){
        this.idCliente = idCliente;

        // pedir cada atributo do biotipo para o usuario
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