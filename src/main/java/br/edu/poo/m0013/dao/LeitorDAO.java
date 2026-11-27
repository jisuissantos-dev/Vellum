package br.edu.poo.m0013.dao;

import br.edu.poo.m0013.database.ConexaoComBanco;
import br.edu.poo.m0013.model.Leitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeitorDAO {

    public void inserir(Leitor leitor) throws SQLException {
        String sql = "INSERT INTO leitor (nome, email, cep, logradouro, bairro, cidade, uf) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, leitor.getNome());
            ps.setString(2, leitor.getEmail());
            ps.setString(3, leitor.getCep());
            ps.setString(4, leitor.getLogradouro());
            ps.setString(5, leitor.getBairro());
            ps.setString(6, leitor.getCidade());
            ps.setString(7, leitor.getUf());
            ps.executeUpdate();
        }
    }

    public List<Leitor> listarTodos() throws SQLException {
        String sql = "SELECT * FROM leitor ORDER BY id";
        List<Leitor> leitores = new ArrayList<>();
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                leitores.add(montarLeitor(rs));
            }
        }
        return leitores;
    }

    public Leitor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM leitor WHERE id = ?";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarLeitor(rs);
                }
            }
        }
        return null;
    }

    public void atualizar(Leitor leitor) throws SQLException {
        String sql = "UPDATE leitor SET nome = ?, email = ?, cep = ?, logradouro = ?, "
                + "bairro = ?, cidade = ?, uf = ? WHERE id = ?";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, leitor.getNome());
            ps.setString(2, leitor.getEmail());
            ps.setString(3, leitor.getCep());
            ps.setString(4, leitor.getLogradouro());
            ps.setString(5, leitor.getBairro());
            ps.setString(6, leitor.getCidade());
            ps.setString(7, leitor.getUf());
            ps.setInt(8, leitor.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM leitor WHERE id = ?";
        try (Connection conexao = ConexaoComBanco.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Leitor montarLeitor(ResultSet rs) throws SQLException {
        Leitor leitor = new Leitor();
        leitor.setId(rs.getInt("id"));
        leitor.setNome(rs.getString("nome"));
        leitor.setEmail(rs.getString("email"));
        leitor.setCep(rs.getString("cep"));
        leitor.setLogradouro(rs.getString("logradouro"));
        leitor.setBairro(rs.getString("bairro"));
        leitor.setCidade(rs.getString("cidade"));
        leitor.setUf(rs.getString("uf"));
        return leitor;
    }
}