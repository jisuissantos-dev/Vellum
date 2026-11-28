package br.edu.poo.m0013;

import br.edu.poo.m0013.database.InicializadorBanco;
import br.edu.poo.m0013.ui.MenuPrincipal;

public class Main {

    public static void main(String[] args) {
        try {
            InicializadorBanco.criarTabelas();
        } catch (Exception e) {
            System.out.println("[!] Não foi possível preparar o banco de dados: " + e.getMessage());
            return;
        }
        MenuPrincipal.iniciar();
    }
}