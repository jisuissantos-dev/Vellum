package br.edu.poo.m0013.model;

import br.edu.poo.m0013.exception.DadoInvalidoException;

public class Manhwa extends Entidade {

    private static final long serialVersionUID = 1L;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new DadoInvalidoException("O título não pode ficar vazio.");
        }
        this.titulo = titulo.trim();
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            throw new DadoInvalidoException("O autor não pode ficar vazio.");
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
            throw new DadoInvalidoException("A quantidade de capítulos não pode ser negativa.");
        }
        this.quantidadeCapitulos = quantidadeCapitulos;
    }

    public StatusManhwa getStatus() {
        return status;
    }

    public void setStatus(StatusManhwa status) {
        if (status == null) {
            throw new DadoInvalidoException("O status não pode ser nulo.");
        }
        this.status = status;
    }

    @Override
    public String resumo() {
        return getId() + " - " + titulo + " (" + autor + ") - "
                + quantidadeCapitulos + " capítulos - " + status;
    }

    @Override
    public String detalhes() {
        String texto = "";
        if (genero != null) {
            texto = "    Gênero: " + genero;
        }
        if (sinopse != null) {
            if (!texto.isEmpty()) {
                texto = texto + System.lineSeparator();
            }
            texto = texto + "    Sinopse: " + sinopse;
        }
        return texto;
    }
}