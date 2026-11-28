package br.edu.poo.m0013.ui;

import br.edu.poo.m0013.dao.ManhwaDAO;
import br.edu.poo.m0013.model.Manhwa;

import java.sql.SQLException;
import java.util.List;

public class MenuManhwa {

    private static final ManhwaDAO manhwaDAO = new ManhwaDAO();

    public static void cadastrar() throws SQLException {
        System.out.println("\n--- Novo manhwa no acervo ---");
        Manhwa manhwa = new Manhwa();
        manhwa.setTitulo(Entrada.lerTexto("Título: "));
        manhwa.setAutor(Entrada.lerTexto("Autor: "));
        manhwa.setGenero(Entrada.lerTextoOpcional("Gênero (Enter para pular): "));
        manhwa.setSinopse(Entrada.lerTextoOpcional("Sinopse (Enter para pular): "));
        manhwa.setQuantidadeCapitulos(Entrada.lerInteiroNaoNegativo("Quantidade de capítulos: "));
        manhwa.setStatus(Entrada.lerStatus());
        manhwaDAO.inserir(manhwa);
        System.out.println("Manhwa registrado no acervo!");
    }

    public static void listar() throws SQLException {
        System.out.println("\n--- Acervo de manhwas ---");
        List<Manhwa> manhwas = manhwaDAO.listarTodos();
        if (manhwas.isEmpty()) {
            System.out.println("O acervo está vazio.");
        }
        for (Manhwa manhwa : manhwas) {
            Exibicao.mostrar(manhwa);
        }
    }

    public static void atualizar() throws SQLException {
        int id = Entrada.lerInteiro("ID do manhwa a atualizar: ");
        Manhwa manhwa = manhwaDAO.buscarPorId(id);
        if (manhwa == null) {
            System.out.println("[!] Manhwa não encontrado.");
            return;
        }
        System.out.println("Aperte Enter para manter o valor atual.");

        String titulo = Entrada.lerTextoOpcional("Título [" + manhwa.getTitulo() + "]: ");
        if (titulo != null) {
            manhwa.setTitulo(titulo);
        }
        String autor = Entrada.lerTextoOpcional("Autor [" + manhwa.getAutor() + "]: ");
        if (autor != null) {
            manhwa.setAutor(autor);
        }
        String genero = Entrada.lerTextoOpcional("Gênero [" + manhwa.getGenero() + "]: ");
        if (genero != null) {
            manhwa.setGenero(genero);
        }
        String sinopse = Entrada.lerTextoOpcional("Sinopse (Enter mantém): ");
        if (sinopse != null) {
            manhwa.setSinopse(sinopse);
        }
        manhwa.setQuantidadeCapitulos(Entrada.lerInteiroOuManter(
                "Capítulos [" + manhwa.getQuantidadeCapitulos() + "]: ",
                manhwa.getQuantidadeCapitulos()));

        manhwa.setStatus(Entrada.lerStatusOuManter(manhwa.getStatus()));

        manhwaDAO.atualizar(manhwa);
        System.out.println("Manhwa atualizado!");
    }

    public static void remover() throws SQLException {
        int id = Entrada.lerInteiro("ID do manhwa a remover: ");
        Manhwa manhwa = manhwaDAO.buscarPorId(id);
        if (manhwa == null) {
            System.out.println("[!] Manhwa não encontrado.");
            return;
        }
        if (Entrada.confirmar("Remover \"" + manhwa.getTitulo() + "\" do acervo e dos favoritos?")) {
            manhwaDAO.deletar(id);
            System.out.println("Manhwa removido do acervo.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }


}