package br.edu.poo.m0013.database;

import br.edu.poo.m0013.util.Configuracao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoComBanco {

    public static Connection getConexao() throws SQLException {
        Configuracao config = Configuracao.getInstancia();

        String url = config.get("DB_URL");
        String usuario = config.get("DB_USER");
        String senha = config.get("DB_PASSWORD", "");

        return DriverManager.getConnection(url, usuario, senha);
    }
}