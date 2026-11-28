package br.edu.poo.m0013.util;

public class Validador {

    public static boolean emailValido(String email) {
        return email.contains("@") && !email.contains(" ");
    }

    public static boolean cepValido(String cep) {
        if (cep.length() != 8) {
            return false;
        }
        for (int i = 0; i < cep.length(); i++) {
            if (!Character.isDigit(cep.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}