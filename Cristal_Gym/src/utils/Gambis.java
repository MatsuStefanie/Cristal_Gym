package utils;

import java.util.Scanner;

public class Gambis {
    public static void limparConsole(){
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("|-----------------------------Aperte algo para continuar ------------------------------|");
        System.out.println("+--------------------------------------------------------------------------------------+");

        new Scanner(System.in).next();

        for (int i = 0; i<15; i++){
            System.out.println("+--------------------------------------------------------------------------------------+");
        }
    }
}
