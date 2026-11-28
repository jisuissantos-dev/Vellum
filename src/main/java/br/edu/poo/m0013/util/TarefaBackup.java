package br.edu.poo.m0013.util;

import br.edu.poo.m0013.model.Manhwa;

import java.io.IOException;
import java.util.List;

public class TarefaBackup extends Thread {

    private final List<Manhwa> manhwas;
    private int quantidadeSalva;
    private IOException erro;

    public TarefaBackup(List<Manhwa> manhwas) {
        this.manhwas = manhwas;
    }

    @Override
    public void run() {
        try {
            quantidadeSalva = BackupManhwa.salvar(manhwas);
        } catch (IOException e) {
            erro = e;
        }
    }

    public int getQuantidadeSalva() {
        return quantidadeSalva;
    }

    public IOException getErro() {
        return erro;
    }
}