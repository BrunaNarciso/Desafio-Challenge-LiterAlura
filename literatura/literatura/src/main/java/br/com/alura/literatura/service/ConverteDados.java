package br.com.alura.literatura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON: " + e.getMessage());
        }
    }

    public <T> T obterDados(String json, String campo, Class<T> classe) {
        try {
            var node = mapper.readTree(json);
            var resultados = node.get(campo);
            return mapper.treeToValue(resultados, classe);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON: " + e.getMessage());
        }
    }
}