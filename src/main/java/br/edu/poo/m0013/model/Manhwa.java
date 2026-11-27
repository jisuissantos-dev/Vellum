package br.edu.poo.m0013.model;

public class Manhwa {
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private String sinopse;
    private int quantidadeCapitulos;
    private StatusManhwa status;

    public Manhwa() {
    }

    public Manhwa(String titulo, String autor, String genero, String sinopse,
                  int quantidadeCapitulos, StatusManhwa status) {
        setTitulo(titulo);
        setAutor(autor);
        setGenero(genero);
        setSinopse(sinopse);
        setQuantidadeCapitulos(quantidadeCapitulos);
        setStatus(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título não pode ficar vazio.");
        }
        this.titulo = titulo.trim();
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("O autor não pode ficar vazio.");
        }
        this.autor = autor.trim();
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public int getQuantidadeCapitulos() {
        return quantidadeCapitulos;
    }

    public void setQuantidadeCapitulos(int quantidadeCapitulos) {
        if (quantidadeCapitulos < 0) {
            throw new IllegalArgumentException("A quantidade de capítulos não pode ser negativa.");
        }
        this.quantidadeCapitulos = quantidadeCapitulos;
    }

    public StatusManhwa getStatus() {
        return status;
    }

    public void setStatus(StatusManhwa status) {
        if (status == null) {
            throw new IllegalArgumentException("O status não pode ser nulo.");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return id + " - " + titulo + " (" + autor + ") - "
                + quantidadeCapitulos + " capítulos - " + status;
    }
}