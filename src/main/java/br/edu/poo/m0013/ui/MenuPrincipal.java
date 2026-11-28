package br.edu.poo.m0013.ui;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class MenuPrincipal {

    private static final String[] LINHAS_MENU = {
            "==========================================",
            "      VELLUM - Biblioteca de Manhwas",
            "==========================================",
            "-- Guilda dos Leitores --",
            " 1. Registrar leitor",
            " 2. Listar leitores",
            " 3. Atualizar leitor",
            " 4. Remover leitor",
            "-- Acervo de Manhwas --",
            " 5. Registrar manhwa",
            " 6. Listar manhwas",
            " 7. Atualizar manhwa",
            " 8. Remover manhwa",
            "-- Grimório de Favoritos --",
            " 9. Favoritar manhwa",
            "10. Ver favoritos de um leitor",
            "11. Remover favorito",
            "-- Backup do Acervo --",
            "12. Salvar backup dos manhwas",
            "13. Ver backup salvo",
            " 0. Sair"
    };

    public static void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = Entrada.lerInteiro("Escolha seu caminho: ");
            try {
                executar(opcao);
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("[!] Dado repetido ou vínculo inexistente "
                        + "(ex.: email já cadastrado, favorito já existente).");
            } catch (SQLException e) {
                System.out.println("[!] Erro no banco de dados: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("[!] " + e.getMessage());
            }
        } while (opcao != 0);
        System.out.println("Até a próxima aventura!");
    }

    private static void exibirMenu() {
        System.out.println();
        for (int i = 0; i < LINHAS_MENU.length; i++) {
            System.out.println(LINHAS_MENU[i]);
        }
    }

    private static void executar(int opcao) throws SQLException {
        switch (opcao) {
            case 1:
                MenuLeitor.cadastrar();
                break;
            case 2:
                MenuLeitor.listar();
                break;
            case 3:
                MenuLeitor.atualizar();
                break;
            case 4:
                MenuLeitor.remover();
                break;
            case 5:
                MenuManhwa.cadastrar();
                break;
            case 6:
                MenuManhwa.listar();
                break;
            case 7:
                MenuManhwa.atualizar();
                break;
            case 8:
                MenuManhwa.remover();
                break;
            case 9:
                MenuFavorito.favoritar();
                break;
            case 10:
                MenuFavorito.verFavoritos();
                break;
            case 11:
                MenuFavorito.removerFavorito();
                break;
            case 12:
                MenuBackup.salvar();
                break;
            case 13:
                MenuBackup.ver();
                break;
            case 0:
                break;
            default:
                System.out.println("[!] Opção inválida, aventureiro.");
        }
    }
}