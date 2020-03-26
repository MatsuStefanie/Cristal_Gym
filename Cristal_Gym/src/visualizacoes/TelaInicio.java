package visualizacoes;

import controladores.BiotipoControlador;
import controladores.ClienteControlador;
import controladores.EmailControlador;
import entidades.Biotipo;
import entidades.Cliente;
import entidades.Email;
import utils.Container;
import utils.Gambis;
import utils.StringUtils;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class TelaInicio {

    Scanner ler = new Scanner(System.in);

    ClienteControlador clienteControlador = Container.buscarDependencia(ClienteControlador.class);
    ClienteVisualizador clienteVisualizador = Container.buscarDependencia(ClienteVisualizador.class);
    List<Cliente> clientes;

    BiotipoControlador biotipoControlador = Container.buscarDependencia(BiotipoControlador.class);
    BiotipoVisualizador biotipoVisualizador = Container.buscarDependencia(BiotipoVisualizador.class);
    List<Biotipo> biotipos ;

    EmailControlador emailControlador = Container.buscarDependencia(EmailControlador.class);
    EmailVisualizador emailVisualizador = Container.buscarDependencia(EmailVisualizador.class);
    List<Email> emails;

    public void iniciarSistema() {

        clientes = clienteControlador.buscarTodosClientes();
        biotipos = biotipoControlador.buscarTodosBiotipos();
        emails = emailControlador.buscarTodosEmails();

        int hora = LocalTime.now().getHour();
            System.out.println("+--------------------------------------------------------------------------------------+");
        if (hora <= 12) {
            System.out.println("|                                     Bom dia Dev                                      |");
        } else if (hora <= 18) {
            System.out.println("|                                     Boa tarde DEV                                    |");
        } else {
            System.out.println("|                               Boa noite DEV vá dormir                                |");
        }
        int leitura = 0;
        do {

            System.out.println("+--------------------------------------------------------------------------------------+");
            System.out.println("|                                  O que deseja fazer?                                 |");
            System.out.println("+--------------------------------------------------------------------------------------+");
            System.out.println("|                            1---Mostrar APENAS BIOTIPO-----                           |");
            System.out.println("|                            2---Mostrar APENAS CLIENTE-----                           |");
            System.out.println("|                            3----Mostrar APENAS EMAIL------                           |");
            System.out.println("|                            4--Mostrar TUDO DE CLIENTES----                           |");
            System.out.println("|                            0------------Fechar------------                           |");
            System.out.println("+--------------------------------------------------------------------------------------+");

            leitura = validarEntrada(0,1,2,3,4);
            switch (leitura) {
                case 1:
                    /*Mostrar APENAS BIOTIPO*/
                    biotipoVisualizador.mostrarBiotipo(biotipos);
                    break;
                case 2:
                    /*Mostrar APENAS CLIENTE*/
                    clienteVisualizador.mostrarClientes(clientes);
                    break;
                case 3:
                    /*Mostrar APENAS EMAIL*/
                    emailVisualizador.mostrarEmails(emails);
                    break;
                case 4:
                    /*Mostrar TUDO DE CLIENTES
                     * CLIENTE
                     * BIOTIPO*/
                    clienteVisualizador.mostrarTudoDeCliente(clientes);
                    break;
            }

            System.out.println("+--------------------------------------------------------------------------------------+");
            System.out.println("|--------------------------------Continuar? 1-SIM 2-NÃO--------------------------------|");
            System.out.println("+--------------------------------------------------------------------------------------+");
            if(validarEntrada(1,2) != 1){
                break;
            }
            Gambis.limparConsole();

        } while (leitura != 0);
    }

    private Integer validarEntrada( Integer... aceitos) {


        while (true){
            // verificar a minha leitura pertence aos numerico
            String leitura = ler.next();
            if(StringUtils.ehNumero(leitura)) {
                Integer numeroLeitura = Integer.parseInt(leitura);

                if(Arrays.asList(aceitos).contains(numeroLeitura)){
                    return numeroLeitura;
                }
            }

            String aceitosStr = Arrays.stream(aceitos)
                    .map(Objects::toString)
                    .reduce((s, s2) -> s.concat(", ").concat(s2))
                    .orElse("");

            System.out.println("Digite um numero valido ("+aceitosStr + ") ." );
        }
    }


}
