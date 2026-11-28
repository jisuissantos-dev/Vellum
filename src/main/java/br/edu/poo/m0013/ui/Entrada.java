package br.edu.poo.m0013.ui;

import br.edu.poo.m0013.model.StatusManhwa;
import br.edu.poo.m0013.util.Validador;

import java.util.Scanner;

public class Entrada {

    private static final Scanner scanner = new Scanner(System.in);

    public static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("[!] Este campo não pode ficar vazio.");
        }
    }

    public static String lerTextoOpcional(String mensagem) {
        System.out.print(mensagem);
        String texto = scanner.nextLine().trim();
        return texto.isEmpty() ? null : texto;
    }

    public static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Digite um número inteiro válido.");
            }
        }
    }

    public static int lerInteiroNaoNegativo(String mensagem) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor >= 0) {
                return valor;
            }
            System.out.println("[!] O valor não pode ser negativo.");
        }
    }

    public static int lerInteiroOuManter(String mensagem, int atual) {
        while (true) {
            String texto = lerTextoOpcional(mensagem);
            if (texto == null) {
                return atual;
            }
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= 0) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // cai na mensagem de erro abaixo
            }
            System.out.println("[!] Digite um número inteiro igual ou maior que 0.");
        }
    }

    public static String lerEmail(String mensagem) {
        while (true) {
            String email = lerTexto(mensagem);
            if (Validador.emailValido(email)) {
                return email;
            }
            System.out.println("[!] Email inválido (precisa ter @ e não ter espaços).");
        }
    }

    // Retorna null se o usuário apertar Enter (manter o valor atual).
    public static String lerEmailOuManter(String mensagem) {
        while (true) {
            String email = lerTextoOpcional(mensagem);
            if (email == null) {
                return null;
            }
            if (Validador.emailValido(email)) {
                return email;
            }
            System.out.println("[!] Email inválido (precisa ter @ e não ter espaços).");
        }
    }

    public static String lerCepOpcional(String mensagem) {
        while (true) {
            String cep = lerTextoOpcional(mensagem);
            if (cep == null) {
                return null;
            }
            cep = cep.replace("-", "");
            if (Validador.cepValido(cep)) {
                return cep;
            }
            System.out.println("[!] O CEP deve ter exatamente 8 números.");
        }
    }

    public static StatusManhwa lerStatus() {
        while (true) {
            System.out.println("Status: 1 - Em publicação | 2 - Finalizada");
            int opcao = lerInteiro("Escolha: ");
            if (opcao == 1) {
                return StatusManhwa.EM_PUBLICACAO;
            }
            if (opcao == 2) {
                return StatusManhwa.FINALIZADA;
            }
            System.out.println("[!] Escolha 1 ou 2.");
        }
    }

    // Se o usuário apertar Enter, devolve o status atual sem mudar.
    public static StatusManhwa lerStatusOuManter(StatusManhwa atual) {
        while (true) {
            String op = lerTextoOpcional("Status [" + atual
                    + "] (1 - Em publicação, 2 - Finalizada, Enter mantém): ");
            if (op == null) {
                return atual;
            }
            if (op.equals("1")) {
                return StatusManhwa.EM_PUBLICACAO;
            }
            if (op.equals("2")) {
                return StatusManhwa.FINALIZADA;
            }
            System.out.println("[!] Digite 1, 2 ou apenas Enter.");
        }
    }

    public static boolean confirmar(String mensagem) {
        System.out.print(mensagem + " (s/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("s");
    }
}