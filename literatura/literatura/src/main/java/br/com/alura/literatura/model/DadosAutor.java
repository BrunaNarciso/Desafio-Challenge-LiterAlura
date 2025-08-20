 package br.com.alura.literatura.model;

import java.text.DateFormat;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(@JsonAlias ("Nome") String nome,
                         @JsonAlias ("Ano de Nascimento") Integer anoDeNascimento,
                         @JsonAlias ("Ano de falecimento") Integer anoDeFalecimento) {

}                                      