package br.com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias("title") String titulo,
                         @JsonAlias("authors") JsonNode autores,
                         @JsonAlias("bookshelves") JsonNode categorias,
                         @JsonAlias("languages") JsonNode idiomas,
                         @JsonAlias("download_count") String numeroDeDownloads) {

    public String getNomeAutor() {
        if (autores != null && autores.isArray() && autores.size() > 0) {
            return autores.get(0).get("name").asText();
        }
        return "Autor desconhecido";
    }
    
    public Integer getAnoNascimentoAutor() {
        if (autores != null && autores.isArray() && autores.size() > 0) {
            var autorNode = autores.get(0);
            return autorNode.has("birth_year") ? autorNode.get("birth_year").asInt() : null;
        }
        return null;
    }
    
    public Integer getAnoFalecimentoAutor() {
        if (autores != null && autores.isArray() && autores.size() > 0) {
            var autorNode = autores.get(0);
            return autorNode.has("death_year") ? autorNode.get("death_year").asInt() : null;
        }
        return null;
    }
    
    public String getPrimeiraCategoria() {
        if (categorias != null && categorias.isArray() && categorias.size() > 0) {
            return categorias.get(0).asText();
        }
        return "Categoria desconhecida";
    }
    
    public String getPrimeiroIdioma() {
        if (idiomas != null && idiomas.isArray() && idiomas.size() > 0) {
            return idiomas.get(0).asText();
        }
        return "Idioma desconhecido";
    }
}
























