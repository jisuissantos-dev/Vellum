package br.edu.poo.m0013.ui;

import br.edu.poo.m0013.model.Entidade;

public class Exibicao {

    // Funciona para qualquer Entidade (Leitor, Manhwa...): o Java decide em
    // tempo de execução qual resumo() e detalhes() chamar.
    public static void mostrar(Entidade entidade) {
        System.out.println(entidade.resumo());
        String detalhes = entidade.detalhes();
        if (!detalhes.isEmpty()) {
            System.out.println(detalhes);
        }
    }
}