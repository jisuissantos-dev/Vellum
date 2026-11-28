package br.edu.poo.m0013.api;

import br.edu.poo.m0013.util.Configuracao;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCepClient {

    public static EnderecoViaCep buscar(String cep) throws IOException {
        if (cep == null || cep.length() != 8) {
            throw new IllegalArgumentException("O CEP deve ter 8 dígitos.");
        }

        String urlBase = Configuracao.getInstancia().get("API_VIACEP_URL");
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(urlBase + cep + "/json/"))
                .GET()
                .build();

        try {
            HttpClient cliente = HttpClient.newHttpClient();
            HttpResponse<String> resposta =
                    cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

            if (resposta.statusCode() != 200) {
                return null;
            }

            EnderecoViaCep endereco = new Gson().fromJson(resposta.body(), EnderecoViaCep.class);
            if (endereco == null || endereco.isErro()) {
                return null;
            }
            return endereco;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("A consulta ao ViaCEP foi interrompida.", e);
        }
    }
}