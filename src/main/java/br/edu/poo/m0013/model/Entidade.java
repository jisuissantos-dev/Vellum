package br.edu.poo.m0013.model;

import java.io.Serializable;

public abstract class Entidade implements Descritivel, Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Cada entidade monta o seu próprio resumo (polimorfismo).
    public abstract String resumo();

    @Override
    public String toString() {
        return resumo();
    }
}