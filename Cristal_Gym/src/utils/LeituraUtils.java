package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class LeituraUtils {

    public static String lerCPF() {
        Scanner scanner = getScanner();

        while (true) {
            String cpf = scanner.next();
            if (isCPF(cpf)) {
                return cpf;
            }
            System.out.println("CPF invalido, ex: XXX.XXX.XXX-YY");
        }
    }

    public static LocalDate validarData() {
        Scanner scanner = getScanner();
        while (true) {
            try {
                String leitura = scanner.next();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(leitura, dateTimeFormatter);
                if (dateTimeFormatter.format(data).equals(leitura)) {
                    return data;
                } else {
                    System.out.println("Data maxima de fevereiro: ".concat(dateTimeFormatter.format(data)));
                }
            } catch (DateTimeParseException e) {
                System.out.println("Insira uma data válida.\nExemplo: 01/01/2000");
            }
        }
    }

    public static LocalDate validaDataMenorAtual() {
        while (true) {
            LocalDate localDate = validarData();
            if (!localDate.isAfter(LocalDate.now())) {
                return localDate;
            }
            System.out.println("Data futura não permitida");
        }

    }

    public static Float validarEntradaDeFloat() {
        while (true) {
            String leitura = getScanner().next();
            leitura = leitura.replaceAll("[^0-9?!\\\\.]", ".");
            try {
                Float numeroLeitura = Float.parseFloat(leitura);
                return numeroLeitura;
            } catch (NumberFormatException e) {
                System.out.println("Digite um peso válido. EX: 80,00");
            }
        }
    }

    public static Integer validarEntradaDeInteiro(Integer... aceitos) {
        while (true) {
            // verificar a minha leitura pertence aos numerico
            String leitura = new Scanner(System.in).next();
            if (StringUtils.ehNumero(leitura)) {
                Integer numeroLeitura = Integer.parseInt(leitura);

                if (Arrays.asList(aceitos).contains(numeroLeitura)) {
                    return numeroLeitura;
                }
            }

            String aceitosStr = Arrays.stream(aceitos)
                    .map(Objects::toString)
                    .reduce((s, s2) -> s.concat(", ").concat(s2))
                    .orElse("");

            System.out.println("Digite um numero valido (" + aceitosStr + ") .");
        }
    }

    public static Scanner getScanner() {
        return new Scanner(System.in);
    }

    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        CPF = CPF.replaceAll("[^0-9?!\\\\.]", "");

        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }
            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static String imprimeCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }


}
