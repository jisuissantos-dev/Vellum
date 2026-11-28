package br.edu.poo.m0013.ui;

import br.edu.poo.m0013.dao.FavoritoDAO;
import br.edu.poo.m0013.dao.LeitorDAO;
import br.edu.poo.m0013.dao.ManhwaDAO;
import br.edu.poo.m0013.model.Leitor;
import br.edu.poo.m0013.model.Manhwa;

import java.sql.SQLException;
import java.util.List;

public class MenuFavorito {

    private static final LeitorDAO leitorDAO = new LeitorDAO();
    private static final ManhwaDAO manhwaDAO = new ManhwaDAO();
    private static final FavoritoDAO favoritoDAO = new FavoritoDAO();

    public static void favoritar() throws SQLException {
        int leitorId = Entrada.lerInteiro("ID do leitor: ");
        Leitor leitor = leitorDAO.buscarPorId(leitorId);
        if (leitor == null) {
            System.out.println("[!] Leitor não encontrado.");
            return;
        }
        int manhwaId = Entrada.lerInteiro("ID do manhwa: ");
        Manhwa manhwa = manhwaDAO.buscarPorId(manhwaId);
        if (manhwa == null) {
            System.out.println("[!] Manhwa não encontrado.");
            return;
        }
        favoritoDAO.adicionar(leitorId, manhwaId);
        System.out.println(leitor.getNome() + " guardou \"" + manhwa.getTitulo()
                + "\" no grimório de favoritos.");
    }

    public static void verFavoritos() throws SQLException {
        int leitorId = Entrada.lerInteiro("ID do leitor: ");
        Leitor leitor = leitorDAO.buscarPorId(leitorId);
        if (leitor == null) {
            System.out.println("[!] Leitor não encontrado.");
            return;
        }
        System.out.println("\n--- Grimório de " + leitor.getNome() + " ---");
        List<Manhwa> favoritos = favoritoDAO.listarPorLeitor(leitorId);
        if (favoritos.isEmpty()) {
            System.out.println("Nenhum favorito ainda.");
        }
        for (Manhwa manhwa : favoritos) {
            System.out.println(manhwa);
        }
    }

    public static void removerFavorito() throws SQLException {
        int leitorId = Entrada.lerInteiro("ID do leitor: ");
        int manhwaId = Entrada.lerInteiro("ID do manhwa: ");
        favoritoDAO.remover(leitorId, manhwaId);
        System.out.println("Favorito removido (se existia).");
    }
}