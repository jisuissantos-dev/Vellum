package br.edu.poo.m0013.exception;

public class DadoInvalidoException extends IllegalArgumentException {

    public DadoInvalidoException(String mensagem) {
        super(mensagem);
    }
}