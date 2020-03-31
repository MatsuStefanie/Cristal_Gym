package visualizacoes;

import utils.Gambis;
import utils.LeituraUtils;
import utils.StringUtils;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class TelaInicio implements Tela {

    Scanner ler = new Scanner(System.in);

    private TelaCliente telaCliente;

    public TelaInicio(TelaCliente telaCliente) {
        this.telaCliente = telaCliente;
    }


    public void iniciar() {


        mostrarCabecalho();

        int leitura = 0;

        do {

            mostrarMenu();

            leitura = LeituraUtils.validarEntradaDeInteiro(0, 1, 2, 3, 4);

            switch (leitura) {
                case 1:
                    /*Mostrar APENAS CLIENTE*/
                    telaCliente.iniciar();
                    break;
                case 2:
                    /*Mostrar APENAS BIOTIPO*/
                    break;
                case 3:
                    /*Mostrar APENAS EMAIL*/
                    break;
                case 4:
                    /*Mostrar TUDO DE CLIENTES
                     * CLIENTE
                     * BIOTIPO*/
                    break;
            }

            Gambis.limparConsole();

        } while (leitura != 0);
    }

    private void mostrarMenu() {
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("|                                  O que deseja fazer?                                 |");
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("|                            1----Gerenciador de CLIENTE----                           |");
        System.out.println("|                            2----Gerenciador de BIOTIPO----                           |");
        System.out.println("|                            3-----Gerenciar financeiro-----                           |");
        System.out.println("|                            4-----------Creditos-----------                           |");
        System.out.println("|                            0------------Fechar------------                           |");
        System.out.println("+--------------------------------------------------------------------------------------+");
    }

    private void mostrarCabecalho() {
        int hora = LocalTime.now().getHour();
        System.out.println("+--------------------------------------------------------------------------------------+");
        if (hora <= 12) {
            System.out.println("|                                     Bom dia Dev                                      |");
        } else if (hora <= 18) {
            System.out.println("|                                     Boa tarde DEV                                    |");
        } else {
            System.out.println("|                               Boa noite DEV vá dormir                                |");
        }
    }

}