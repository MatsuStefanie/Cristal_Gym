package visualizacoes;

import controladores.BiotipoControlador;
import controladores.ClienteControlador;
import controladores.EmailControlador;
import entidades.Biotipo;
import entidades.Cliente;
import entidades.Email;
import utils.Gambis;
import utils.LeituraUtils;

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

    private void editar() {
        List<Cliente> clientes;
        Cliente atualizacaoCliente = new Cliente();
        Email atualizacaoEmail = new Email();
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("+---------------------------Qual a matricula deseja alterar?---------------------------+");
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("+--------------------------------------------------------------------------------------+");

        clientes = clienteControlador.buscarTodosClientes();
        for (Cliente cliente : clientes) {
            String line = buildLine(cliente);
            System.out.println(line);
            System.out.println("+--------------------------------------------------------------------------------------+");
        }
        Scanner ler = LeituraUtils.getScanner();
        var ref = new Object() {
            Integer matriculaLeitura = 0;
        };
        /*-Ler entrada-*/
        do {
            System.out.println("Digite uma matricula valida");
            ref.matriculaLeitura = ler.nextInt();
        } while (clientes.stream().map(Cliente::getId).noneMatch(id -> id.equals(ref.matriculaLeitura)));

        int leitura = 0;
        do {
            System.out.println("+--------------------------------------------------------------------------------------+");
            System.out.println("|------------------------O que deseja alterar da matricula " + ref.matriculaLeitura + "?------------------------|");
            System.out.println("|--------------------------------------------------------------------------------------|");
            System.out.println("|--------------------1-Informação base------2-Email------0-Cancelar--------------------|");
            System.out.println("+--------------------------------------------------------------------------------------+");
            leitura = LeituraUtils.validarEntradaDeInteiro(0, 1, 2);

            switch (leitura) {

                case 1: /*-Se 1-*/ {
                    System.out.println("+---------------------------Nome atual- Digite a atualização---------------------------+");
                    atualizacaoCliente.setNome(LeituraUtils.getScanner().nextLine());
                    System.out.println("+---------------------------CPF atual - Digite a atualização---------------------------+");
                    atualizacaoCliente.setCpf(LeituraUtils.lerCPF());
                    System.out.println("+------------------------Data nascimento - Digite a atualização------------------------+");
                    atualizacaoCliente.setDataNascimento(LeituraUtils.validarData());
                    atualizacaoCliente.setId(ref.matriculaLeitura);
                    int novaLeitura = 0;
                    System.out.println("|---------------------------------Confirma alteração ?---------------------------------|");
                    System.out.println("|----------------------------------1 Sim--------2 Não----------------------------------|");
                    novaLeitura = LeituraUtils.validarEntradaDeInteiro(1, 2);
                    if (novaLeitura == 1) {/*Se sim, atualizar banco*/
                        clienteControlador.atualizar(atualizacaoCliente);
                        System.out.println("|--------------------------------Atualizado com sucesso--------------------------------|");
                    } else if (novaLeitura == 2) {/* Se não, cancelar e voltar ao menu*/
                        System.out.println("|------------------------------Atualização foi cancelada!------------------------------|");
                    }
                    break;
                }
                case 2:/*-Senão se 2-*/ {
                    List<Email> emails;
                    System.out.println("+--------------------------------------------------------------------------------------+");
                    System.out.println("|---------------------------Qual dos e-mails deseja alterar?---------------------------|");
                    System.out.println("+--------------------------------------------------------------------------------------+");
                    System.out.println("+--------------------------------------------------------------------------------------+");

                    emails = emailControlador.buscarTodosEmailsDeClienteEspecifico(ref.matriculaLeitura);
                    for (Email email : emails) {
                        String line = buildLine(email);
                        System.out.println(line);
                        System.out.println("+--------------------------------------------------------------------------------------+");
                    }

                    var refEmail = new Object() {
                        Integer idEmailLeitura = 0;
                    };
                    /*-Ler entrada-*/
                    do {
                        System.out.println("|--------------------------------Digite um id de email!--------------------------------|");
                        refEmail.idEmailLeitura = LeituraUtils.getScanner().nextInt();
                    } while (emails.stream().map(Email::getId).noneMatch(id -> id.equals(refEmail.idEmailLeitura)));

                    atualizacaoEmail.setId(refEmail.idEmailLeitura);

                    atualizacaoEmail.setIdCliente(ref.matriculaLeitura);

                    System.out.println("+---------------------------Email atual- Qual a atualização?---------------------------+");
                    atualizacaoEmail.setEmail(LeituraUtils.getScanner().nextLine());

                    System.out.println("+---------------------------É principal?---- 1 SIM---- 2 NÃO---------------------------+");
                    atualizacaoEmail.setPrincipal(LeituraUtils.validarEntradaDeInteiro(1, 2) == 1);

                    int novaLeitura = 0;

                    System.out.println("|---------------------------------Confirma alteração ?---------------------------------|");
                    System.out.println("|----------------------------------1 Sim--------2 Não----------------------------------|");

                    novaLeitura = LeituraUtils.validarEntradaDeInteiro(1, 2);

                    if (novaLeitura == 1) {/*Se sim, atualizar banco*/
                        emailControlador.atualizar(atualizacaoEmail);
                        System.out.println("|--------------------------------Atualizado com sucesso--------------------------------|");
                    } else if (novaLeitura == 2) {/* Se não, cancelar e voltar ao menu*/
                        System.out.println("|------------------------------Atualização foi cancelada!------------------------------|");
                    }

                    break;
                }
            }
        } while (leitura != 0);
    }

    private void excluir() {
        List<Cliente> clientes;
        Cliente excluirCliente = new Cliente();

        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("+----------------------Qual a cliente deseja excluir informações?----------------------+");
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("+--------------------------------------------------------------------------------------+");

        clientes = clienteControlador.buscarTodosClientes();
        for (Cliente cliente : clientes) {
            String line = buildLine(cliente);
            System.out.println(line);
            System.out.println("+--------------------------------------------------------------------------------------+");
        }
        Scanner ler = LeituraUtils.getScanner();
        var ref = new Object() {
            Integer matriculaLeitura = 0;
        };
        /*-Ler entrada-*/
        do {
            System.out.println("Digite uma matricula valida");
            ref.matriculaLeitura = ler.nextInt();
        } while (clientes.stream().map(Cliente::getId).noneMatch(id -> id.equals(ref.matriculaLeitura)));

        int leitura = 0;
        do {
            System.out.println("+--------------------------------------------------------------------------------------+");
            System.out.println("|------------------------O que deseja excluir da matricula " + ref.matriculaLeitura + "?------------------------|");
            System.out.println("|--------------------------------------------------------------------------------------|");
            System.out.println("|----------------------1-Tudo------2- Apenas email------0-Cancelar---------------------|");
            System.out.println("+--------------------------------------------------------------------------------------+");
            leitura = LeituraUtils.validarEntradaDeInteiro(0, 1, 2);

            switch (leitura) {
                case 1:{
                    System.out.println("+---------------------------------------Excluir?---------------------------------------+");
                    excluirCliente = clienteControlador.buscarEspecificoDesseId(ref.matriculaLeitura);
                    String line = buildLine(excluirCliente);
                    System.out.println(line);
                    System.out.println("+---------------------A exclusão realizada não poderá ser desfeita---------------------+");
                    System.out.println("+-----------------------1-Estou ciente----------------2-Cancelar-----------------------+");
                    if(LeituraUtils.validarEntradaDeInteiro(1,2)==1){
                        excluirTudo(ref.matriculaLeitura);
                    }else{
                        System.out.println("+--------------------------------Nada foi excluido!------------------------------------+");
                    }
                    break;}
                case 2:
                    List<Email> emails;
                    System.out.println("+--------------------------------------------------------------------------------------+");
                    System.out.println("|---------------------------Qual dos e-mails deseja deletar?---------------------------|");
                    System.out.println("+--------------------------------------------------------------------------------------+");
                    System.out.println("+--------------------------------------------------------------------------------------+");

                    emails = emailControlador.buscarTodosEmailsDeClienteEspecifico(ref.matriculaLeitura);
                    for (Email email : emails) {
                        String line = buildLine(email);
                        System.out.println(line);
                        System.out.println("+--------------------------------------------------------------------------------------+");
                    }
                    var refEmail = new Object() {Integer idEmailLeitura = 0;};
                    do {
                        System.out.println("|--------------------------------Digite um id de email!--------------------------------|");
                        refEmail.idEmailLeitura = LeituraUtils.getScanner().nextInt();
                    } while (emails.stream().map(Email::getId).noneMatch(id -> id.equals(refEmail.idEmailLeitura)));


                    Email excluirEmail = emails
                                            .stream()
                                            .filter(email -> email.getId().equals(refEmail.idEmailLeitura))
                                            .findFirst()
                                            .orElseThrow(() -> new RuntimeException("Email com id: " + refEmail.idEmailLeitura + " não existe"));

                    System.out.println("+---------------------------------------Excluir?---------------------------------------+");
                    String line = buildLine(excluirEmail);
                    System.out.println(line);
                    System.out.println("+---------------------A exclusão realizada não poderá ser desfeita---------------------+");
                    System.out.println("+-----------------------1-Estou ciente----------------2-Cancelar-----------------------+");
                    if(LeituraUtils.validarEntradaDeInteiro(1,2)==1){
                        excluirEmails(excluirEmail);
                    }else{
                        System.out.println("+--------------------------------Nada foi excluido!------------------------------------+");
                    }
                    break;
            }
        } while (leitura != 0);
    }

    private void excluirEmails(Email email) {
        emailControlador.excluir(email);
    }

    private void excluirTudo(Integer idCliente) {
        clienteControlador.excluirTudoDe(idCliente);
        System.out.println("+---------------------------------Excluido com sucesso---------------------------------+");
    }

    private String buildLine(Cliente cliente) {
        return "Matricula: " + cliente.getId() +
                " Nome: " + cliente.getNome() +
                " CPF: " + cliente.getCpf() +
                " Data de Nasc.: " + cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String buildLine(Email email) {
        return "ID: " + email.getId() + " Email: " + email.getEmail() + " Principal? " + email.getPrincipal();
    }

}