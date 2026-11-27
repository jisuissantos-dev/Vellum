package br.edu.poo.m0013.dao;

import br.edu.poo.m0013.database.ConexaoComBanco;
import br.edu.poo.m0013.model.Manhwa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritoDAO {

    public void adicionar(int leitorId, int manhwaId) throws SQLException {
        String sql = "INSERT INTO favorito (leitor_id, manhwa_id) VALUES (?, ?)";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, leitorId);
            ps.setInt(2, manhwaId);
            ps.executeUpdate();
        }
    }

    public void remover(int leitorId, int manhwaId) throws SQLException {
        String sql = "DELETE FROM favorito WHERE leitor_id = ? AND manhwa_id = ?";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, leitorId);
            ps.setInt(2, manhwaId);
            ps.executeUpdate();
        }
    }

    public List<Manhwa> listarPorLeitor(int leitorId) throws SQLException {
        String sql = "SELECT m.* FROM manhwa m "
                + "JOIN favorito f ON f.manhwa_id = m.id "
                + "WHERE f.leitor_id = ? "
                + "ORDER BY f.data_adicao, m.titulo";
        List<Manhwa> favoritos = new ArrayList<>();
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, leitorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    favoritos.add(ManhwaDAO.montarManhwa(rs));
                }
            }
        }
        return favoritos;
    }
}