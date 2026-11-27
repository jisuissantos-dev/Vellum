package br.edu.poo.m0013.dao;

import br.edu.poo.m0013.database.ConexaoComBanco;
import br.edu.poo.m0013.model.Manhwa;
import br.edu.poo.m0013.model.StatusManhwa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManhwaDAO {

    public void inserir(Manhwa manhwa) throws SQLException {
        String sql = "INSERT INTO manhwa (titulo, autor, genero, sinopse, quantidade_capitulos, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, manhwa.getTitulo());
            ps.setString(2, manhwa.getAutor());
            ps.setString(3, manhwa.getGenero());
            ps.setString(4, manhwa.getSinopse());
            ps.setInt(5, manhwa.getQuantidadeCapitulos());
            ps.setString(6, manhwa.getStatus().name());
            ps.executeUpdate();
        }
    }

    public List<Manhwa> listarTodos() throws SQLException {
        String sql = "SELECT * FROM manhwa ORDER BY id";
        List<Manhwa> manhwas = new ArrayList<>();
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                manhwas.add(montarManhwa(rs));
            }
        }
        return manhwas;
    }

    public Manhwa buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM manhwa WHERE id = ?";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarManhwa(rs);
                }
            }
        }
        return null;
    }

    public void atualizar(Manhwa manhwa) throws SQLException {
        String sql = "UPDATE manhwa SET titulo = ?, autor = ?, genero = ?, sinopse = ?, "
                + "quantidade_capitulos = ?, status = ? WHERE id = ?";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, manhwa.getTitulo());
            ps.setString(2, manhwa.getAutor());
            ps.setString(3, manhwa.getGenero());
            ps.setString(4, manhwa.getSinopse());
            ps.setInt(5, manhwa.getQuantidadeCapitulos());
            ps.setString(6, manhwa.getStatus().name());
            ps.setInt(7, manhwa.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM manhwa WHERE id = ?";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    static Manhwa montarManhwa(ResultSet rs) throws SQLException {
        Manhwa manhwa = new Manhwa();
        manhwa.setId(rs.getInt("id"));
        manhwa.setTitulo(rs.getString("titulo"));
        manhwa.setAutor(rs.getString("autor"));
        manhwa.setGenero(rs.getString("genero"));
        manhwa.setSinopse(rs.getString("sinopse"));
        manhwa.setQuantidadeCapitulos(rs.getInt("quantidade_capitulos"));
        manhwa.setStatus(StatusManhwa.valueOf(rs.getString("status")));
        return manhwa;
    }
}