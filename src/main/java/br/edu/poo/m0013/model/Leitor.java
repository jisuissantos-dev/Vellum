package br.edu.poo.m0013.model;

import br.edu.poo.m0013.exception.DadoInvalidoException;

public class Leitor extends Entidade {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

    public Leitor() {
    }

    public Leitor(String nome, String email) {
        setNome(nome);
        setEmail(email);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DadoInvalidoException("O nome não pode ficar vazio.");
        }
        this.nome = nome.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new DadoInvalidoException("Email inválido.");
        }
        this.email = email.trim();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if (cep != null && cep.length() != 8) {
            throw new DadoInvalidoException("O CEP deve ter 8 dígitos.");
        }
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        if (uf != null && uf.length() != 2) {
            throw new DadoInvalidoException("A UF deve ter 2 letras.");
        }
        this.uf = uf;
    }

    @Override
    public String resumo() {
        return getId() + " - " + nome + " (" + email + ")";
    }

    @Override
    public String detalhes() {
        if (cep == null) {
            return "";
        }
        return "    CEP " + cep + " - " + logradouro + ", " + bairro
                + " - " + cidade + "/" + uf;
    }
}