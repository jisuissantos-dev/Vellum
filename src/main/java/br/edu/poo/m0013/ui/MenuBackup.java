package br.edu.poo.m0013.ui;

import br.edu.poo.m0013.dao.ManhwaDAO;
import br.edu.poo.m0013.model.Manhwa;
import br.edu.poo.m0013.util.BackupManhwa;
import br.edu.poo.m0013.util.TarefaBackup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class MenuBackup {

    private static final ManhwaDAO manhwaDAO = new ManhwaDAO();

    public static void salvar() throws SQLException {
        System.out.println("\n--- Backup do acervo ---");
        TarefaBackup tarefa = new TarefaBackup(manhwaDAO.listarTodos());
        tarefa.start();
        try {
            tarefa.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[!] O backup foi interrompido.");
            return;
        }
        if (tarefa.getErro() != null) {
            System.out.println("[!] Não foi possível salvar o backup: "
                    + tarefa.getErro().getMessage());
        } else {
            System.out.println(tarefa.getQuantidadeSalva() + " manhwa(s) salvo(s) em "
                    + BackupManhwa.ARQUIVO + ".");
        }
    }

    public static void ver() {
        System.out.println("\n--- Backup salvo ---");
        try {
            Manhwa[] manhwas = BackupManhwa.carregar();
            if (manhwas.length == 0) {
                System.out.println("O backup está vazio.");
            }
            for (int i = 0; i < manhwas.length; i++) {
                Exibicao.mostrar(manhwas[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("[!] Nenhum backup encontrado. Use a opção 12 primeiro.");
        } catch (IOException e) {
            System.out.println("[!] Não foi possível ler o backup: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[!] O arquivo de backup está em um formato inválido.");
        }
    }
}