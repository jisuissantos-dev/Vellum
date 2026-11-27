package br.edu.poo.m0013.model;

public class Leitor {
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ficar vazio.");
        }
        this.nome = nome.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }
        this.email = email.trim();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if (cep != null && cep.length() != 8) {
            throw new IllegalArgumentException("O CEP deve ter 8 dígitos.");
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
            throw new IllegalArgumentException("A UF deve ter 2 letras.");
        }
        this.uf = uf;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " (" + email + ")";
    }
}