package br.edu.poo.m0013.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializadorBanco {

    public static void criarTabelas() throws SQLException {
        String leitor = "CREATE TABLE IF NOT EXISTS leitor ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "nome VARCHAR(100) NOT NULL, "
                + "email VARCHAR(100) NOT NULL UNIQUE, "
                + "cep CHAR(8), "
                + "logradouro VARCHAR(100), "
                + "bairro VARCHAR(100), "
                + "cidade VARCHAR(50), "
                + "uf CHAR(2))";

        String manhwa = "CREATE TABLE IF NOT EXISTS manhwa ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "titulo VARCHAR(100) NOT NULL, "
                + "autor VARCHAR(100) NOT NULL, "
                + "genero VARCHAR(100), "
                + "sinopse TEXT, "
                + "quantidade_capitulos INT NOT NULL DEFAULT 0, "
                + "status ENUM('EM_PUBLICACAO', 'FINALIZADA') NOT NULL)";

        String favorito = "CREATE TABLE IF NOT EXISTS favorito ("
                + "leitor_id INT NOT NULL, "
                + "manhwa_id INT NOT NULL, "
                + "data_adicao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                + "PRIMARY KEY (leitor_id, manhwa_id), "
                + "FOREIGN KEY (leitor_id) REFERENCES leitor(id) ON DELETE CASCADE, "
                + "FOREIGN KEY (manhwa_id) REFERENCES manhwa(id) ON DELETE CASCADE)";

        try (Connection conexao = ConexaoComBanco.getConexao();
             Statement comando = conexao.createStatement()) {
            comando.execute(leitor);
            comando.execute(manhwa);
            comando.execute(favorito);
        }
    }
}