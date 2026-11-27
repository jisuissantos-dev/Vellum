package br.edu.poo.m0013.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoComBanco {

    public static Connection getConexao() throws SQLException {
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DB_URL");
        String usuario = dotenv.get("DB_USER");
        String senha = dotenv.get("DB_PASSWORD", "");

        return DriverManager.getConnection(url, usuario, senha);
    }
}