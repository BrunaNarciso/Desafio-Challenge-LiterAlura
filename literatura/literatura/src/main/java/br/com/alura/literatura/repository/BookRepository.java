package br.com.alura.literatura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.literatura.model.Autor;
import br.com.alura.literatura.model.Livro;

public interface BookRepository extends JpaRepository<Livro, Long>{
    
    Optional<Livro> findByTituloContainingIgnoreCase(String nomeLivro);

    @Query("SELECT l.autor FROM Livro l WHERE UPPER(l.autor.nome) LIKE UPPER(CONCAT('%', :nomeAutor, '%'))")
    List<Autor> findAutoresByNomeContainingIgnoreCase(@Param("nomeAutor") String nomeAutor);

    List<Livro> findByIdiomasContainingIgnoreCase(String idioma);

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoDeFalecimento IS NULL OR a.anoDeFalecimento >= :ano)")
    List<Autor> findAutoresVivosNoAno(@Param("ano") Integer ano);
}                                                                                 