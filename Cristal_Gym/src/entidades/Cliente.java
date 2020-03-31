package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// classe refetente a tabela "cliente" do BD
public class Cliente {


    // atributos de tabela
    private Integer id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    // atributos de relacionamento

    //Many-To-One
    private List<Biotipo> biotipos;

    //Many-To-One
    private List<Email> emails;

    public Cliente() {
        super();
    }


    public Cliente(Integer id, String nome, String cpf, LocalDate dataNascimento) {
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

    public LocalDate getDataNascimento() {
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
