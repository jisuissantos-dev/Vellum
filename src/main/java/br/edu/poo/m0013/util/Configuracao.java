package br.edu.poo.m0013.util;

import io.github.cdimascio.dotenv.Dotenv;

public class Configuracao {

    private static Configuracao instancia;

    private final Dotenv dotenv;

    private Configuracao() {
        this.dotenv = Dotenv.load();
    }

    public static synchronized Configuracao getInstancia() {
        if (instancia == null) {
            instancia = new Configuracao();
        }
        return instancia;
    }

    public String get(String chave) {
        return dotenv.get(chave);
    }

    public String get(String chave, String padrao) {
        return dotenv.get(chave, padrao);
    }
}