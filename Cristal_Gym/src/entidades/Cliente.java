package entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// classe refetente a tabela "cliente" do BD
public class Cliente {


    // atributos de tabela
    private Integer id;
    private String nome;
    private String cpf;
    private Date dataNascimento;

    // atributos de relacionamento

    //Msny-To-One
    private List<Biotipo> biotipos;

    //Many-To-One
    private List<Email> emails;

    public Cliente(Integer id, String nome, String cpf, Date dataNascimento) {
        this.id = id ;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;

        this.emails = new ArrayList<>();
        this.biotipos = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public List<Biotipo> getBiotipos() {
        return biotipos;
    }


    public List<Email> getEmails() {
        return emails;
    }

    public void addEmail(Email email) {
        this.emails.add(email);
    }

    public void addBiotipo(Biotipo biotipo) {
        this.biotipos.add(biotipo);
    }
}
