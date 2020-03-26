package entidades;

// Entidade relacionada a tabela Email
public class Email {
    private Integer id;
    private String email;
    private Boolean isPrincipal;
    private Integer idCliente;

    public Email(Integer id, String email, Boolean isPrincipal, Integer idCliente) {
        this.id = id;
        this.email = email;
        this.isPrincipal = isPrincipal;
        this.idCliente = idCliente;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPrincipal() {
        String resposta;
        if (isPrincipal==true) {
            return resposta = "Sim";
        } else {
            return resposta = "Não";
        }
    }

    public Integer getIdCliente() {
        return idCliente;
    }
    public Boolean getIsPrincipal(){
        return isPrincipal;
    }

    @Override
    public String toString() {
        return this.getEmail() + (isPrincipal ? ", email Princial": "");
    }
}