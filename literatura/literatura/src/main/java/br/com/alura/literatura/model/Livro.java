package br.com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name= "livros")

public class Livro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String titulo;
    private String categoria;
    private String idiomas;
    private String numeroDeDownloads;
    
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    
    public Livro() {
    	
    }
    
    public Livro(String titulo, String categoria, String idiomas, String numeroDeDownloads, Autor autor) {
    	this.titulo = titulo;
    	this.categoria = categoria;
    	this.idiomas = idiomas ;
    	this.numeroDeDownloads = numeroDeDownloads;
    	this.autor = autor;
    		
    	
    	
    }

	public Livro(DadosLivro dados, Autor autor2) {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(String idiomas) {
		this.idiomas = idiomas;
	}

	public String getNumeroDeDownloads() {
		return numeroDeDownloads;
	}

	public void setNumeroDeDownloads(String numeroDeDownloads) {
		this.numeroDeDownloads = numeroDeDownloads;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", categoria=" + categoria + ", idiomas=" + idiomas
				+ ", numeroDeDownloads=" + numeroDeDownloads + ", autor=" + autor + "]";
	}

	
	


}            