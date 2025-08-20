package br.com.alura.literatura.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "autor")
public class Autor {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)

	private String nome;
	private Integer anoNascimento;
	private Integer anoDeFalecimento;
	
	//@Enumerated(EnumType.STRING)
	//private Categoria genero;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Livro> livros = new ArrayList <>();
	
	public Autor() {}

	public Autor(String nome, Integer anoNascimento, Integer anoFalecimento) {
		
		this.nome = nome;
		this.anoNascimento = anoNascimento;
		this.anoDeFalecimento = anoFalecimento;
	}

	public Autor(DadosAutor dadosAutor) {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getAnoNascimento() {
		return anoNascimento;
	}

	public void setAnoNascimento(Integer anoNascimento) {
		this.anoNascimento = anoNascimento;
	}

	public Integer getAnoDeFalecimento() {
		return anoDeFalecimento;
	}

	public void setAnoDeFalecimento(Integer anoDeFalecimento) {
		this.anoDeFalecimento = anoDeFalecimento;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		livros.forEach(l -> l.setAutor(this));
		this.livros = livros;
	}

	@Override
	public String toString() {
		return "Autor [id=" + id + ", nome=" + nome + ", anoNascimento=" + anoNascimento + ", anoDeFalecimento="
				+ anoDeFalecimento + ", livros=" + livros + "]";
	}
	
	
	
	

}