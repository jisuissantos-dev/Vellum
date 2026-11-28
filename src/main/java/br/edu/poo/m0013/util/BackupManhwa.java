package br.edu.poo.m0013.util;

import br.edu.poo.m0013.model.Manhwa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class BackupManhwa {

    public static final String ARQUIVO = "backup_manhwas.dat";

    public static int salvar(List<Manhwa> manhwas) throws IOException {
        Manhwa[] vetor = manhwas.toArray(new Manhwa[0]);
        try (ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            saida.writeObject(vetor);
        }
        return vetor.length;
    }

    public static Manhwa[] carregar() throws IOException, ClassNotFoundException {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (Manhwa[]) entrada.readObject();
        }
    }
}