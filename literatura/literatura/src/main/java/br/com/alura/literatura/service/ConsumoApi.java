package br.com.alura.literatura.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public String obterDados(String endereco) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .GET() // 
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro na API. Código HTTP: " + response.statusCode());
            }

            return response.body();

        } catch (Exception e) {
            System.out.println("Erro ao conectar na API: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
