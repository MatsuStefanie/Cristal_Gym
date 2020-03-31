package visualizacoes;

import controladores.BiotipoControlador;
import controladores.ClienteControlador;
import controladores.EmailControlador;
import entidades.Biotipo;
import entidades.Cliente;
import entidades.Email;
import utils.Gambis;
import utils.LeituraUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TelaCliente<main> implements Tela {

    private ClienteControlador clienteControlador;
    private EmailControlador emailControlador;
    private BiotipoControlador biotipoControlador;

    public TelaCliente(ClienteControlador clienteControlador, EmailControlador emailControlador, BiotipoControlador biotipoControlador) {
        this.clienteControlador = clienteControlador;
        this.biotipoControlador = biotipoControlador;
        this.emailControlador = emailControlador;
    }

    public void iniciar() {
        int leitura = 0;
        do {

            System.out.println("+--------------------------------------------------------------------------------------+");
            System.out.println("|                                  O que deseja fazer?                                 |");
            System.out.println("+--------------------------------------------------------------------------------------+");
            System.out.println("|                            1----Ver lista de clientes-----                           |");
            System.out.println("|                            2----Inserir novo cliente------                           |");
            System.out.println("|                            3----Editar algum cliente------                           |");
            System.out.println("|                            4-----Exclusão de cliente------                           |");
            System.out.println("|                            0------------voltar------------                           |");
            System.out.println("+--------------------------------------------------------------------------------------+");

            leitura = LeituraUtils.validarEntradaDeInteiro(0, 1, 2, 3, 4);

            switch (leitura) {
                case 1:
                    /*Listar CLIENTE*/
                    listar();
                    break;
                case 2:
                    /*Criar CLIENTE*/
                    criar();
                    break;
                case 3:
                    /*Editar CLIENTE*/
                    editar();
                    break;
                case 4:
                    /*Excluir CLIENTE*/
                    excluir();
                    break;
            }
            Gambis.limparConsole();

        } while (leitura != 0);
    }

    private void criar() {

        Scanner ler = LeituraUtils.getScanner();
        int leitura = 0;
        Cliente novoCliente = new Cliente();
        Email novoEmail = new Email();
        Biotipo novoBiotipo = new Biotipo();

        System.out.println("+------------------------------------Qual o nome?------------------------------------+");
        novoCliente.setNome(ler.nextLine());
        System.out.println("+------------------------------------Qual o CPF ?------------------------------------+");
        novoCliente.setCpf(LeituraUtils.lerCPF());
        System.out.println("+---------------------------------Data de nascimento?--------------------------------+");
        novoCliente.setDataNascimento(LeituraUtils.validarData());

        clienteControlador.criar(novoCliente);
        novoCliente.setId(clienteControlador.trazerId());

        do {
            System.out.println("+-----------------------------------Qual o e-mail?-----------------------------------+");
            novoEmail.setEmail(ler.nextLine());
            System.out.println("+-----------------------------------É o principal ?----------------------------------+");
            System.out.println("+---------------------------------1 SIM---------2 NÃO--------------------------------+");
            leitura = LeituraUtils.validarEntradaDeInteiro(1, 2);
            if (leitura == 1) {
                novoEmail.setPrincipal(true);
            } else {
                novoEmail.setPrincipal(false);
            }
            novoEmail.setIdCliente(novoCliente.getId());
            //novoCliente.addEmail(novoEmail);
            emailControlador.criar(novoEmail);

            System.out.println("+--------------------------------Deseja adicinar mais?-------------------------------+");
            System.out.println("+---------------------------------1 SIM---------0 NÃO--------------------------------+");

        } while (LeituraUtils.validarEntradaDeInteiro(1, 0) == 1);

            System.out.println("+--------------------------------Qual seu peso atual?--------------------------------+");
            novoBiotipo.setPeso(LeituraUtils.validarEntradaDeFloat());
            System.out.println("+-------------------------------Qual sua altura atual?-------------------------------+");
            novoBiotipo.setAltura(LeituraUtils.validarEntradaDeFloat());

            novoBiotipo.setIdCliente(novoCliente.getId());
            //novoCliente.addBiotipo(novoBiotipo);
            biotipoControlador.inserindoBiotipos(novoBiotipo);
    }

    private void listar() {
        List<Cliente> clientes;
        int leitura = 0;
        do {
            System.out.println("+------------------------------Mostrar tudo de cliente?------------------------------+");
            System.out.println("+-------------------------1 SIM--------2 NÃO--------0 VOLTAR-------------------------+");
            leitura = LeituraUtils.validarEntradaDeInteiro(1, 2, 0);

            switch (leitura) {
                case 1:
                    clientes = clienteControlador.buscarTudoDeClientes();
                    for (Cliente cliente : clientes) {
                        System.out.println("\nMatricula: " + cliente.getId() + " Nome:" + cliente.getNome() + " CPF:" + cliente.getCpf() + " Data Nasc.: " + cliente.getDataNascimento());
                        System.out.println("Emails:");
                        cliente.getEmails().forEach(email -> {
                            System.out.println(email.toString());
                        });
                        System.out.println("Biotipo:");
                        cliente.getBiotipos().forEach(biotipo -> {
                            System.out.println(biotipo.toString());
                        });
                    }
                    break;
                case 2:
                    clientes = clienteControlador.buscarTodosClientes();
                    for (Cliente cliente : clientes) {
                        String line = buildLine(cliente);
                        System.out.println(line);
                    }
                    break;
            }
        } while (leitura != 0);
    }

    private void editar(){
        List<Cliente> clientes;
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("+-------------------------------Qual a matricula deseja?-------------------------------+");
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("+--------------------------------------------------------------------------------------+");

        clientes = clienteControlador.buscarTodosClientes();
        for (Cliente cliente : clientes) {
            String line = buildLine(cliente);
            System.out.println(line);
            System.out.println("+--------------------------------------------------------------------------------------+");
        }
        /*qual o id desejado (mostrar pouca coisas)
        quais dados deseja alterar de "Cliente"
    	email (qual dos?)
    	base cliente*/
    }

    private void excluir(){

    }

    private String buildLine(Cliente cliente) {
        return "Matricula: " + cliente.getId() +
                " Nome: " + cliente.getNome() +
                " CPF: " + cliente.getCpf() +
                " Data de Nasc.: " + cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


}