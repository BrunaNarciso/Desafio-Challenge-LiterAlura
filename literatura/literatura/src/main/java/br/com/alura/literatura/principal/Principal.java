package br.com.alura.literatura.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.util.comparator.Comparators;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.literatura.model.Autor;
import br.com.alura.literatura.model.DadosAutor;
import br.com.alura.literatura.model.DadosLivro;
import br.com.alura.literatura.model.Livro;
import br.com.alura.literatura.repository.AutorRepository;
import br.com.alura.literatura.repository.BookRepository;
import br.com.alura.literatura.service.ConsumoApi;
import br.com.alura.literatura.service.ConverteDados;

public class Principal {
    
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    
    //private List<DadosAutor> dadosAutor = new ArrayList<>();
   //private List<Autor> series = new ArrayList<>();
    
    private BookRepository repositorio;
    private AutorRepository autorRepository;

    

    public Principal(BookRepository repositorio, AutorRepository autorRepository) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
    }

    public void ExibeMenu() {

        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livros pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados 
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    """;
        
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
            case 1:
                buscarLivrosWeb();
                break;
            case 2:
                listarLivrosBuscados();
                break;
            case 3:
                listarAutoresBuscados();
                break;        

            case 4:
                 buscarAutoresVivosEmAno();
                break;
                
            case 5:
                ListarLivrosPorIdioma();
                break;
            
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida");
            }
        }
    }

    private void ListarLivrosPorIdioma() {
        
        System.out.println("Insira o idioma para realizar a busca:"
                         + "es - Espanhol"
                         + "en - Inglês"
                         + "fr - francês"
                         + "pt - português");
        var idioma = leitura.nextLine();       
        List<Livro> livros =  repositorio.findByIdiomasContainingIgnoreCase(idioma);
        
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma: " + idioma);
        } else {
            System.out.println("Livros encontrados:");
            livros.forEach(livro -> 
                System.out.println("Título: " + livro.getTitulo() + 
                                 " | Autor: " + livro.getAutor().getNome() +
                                 " | Idioma: " + livro.getIdiomas())
            );
        }
        
    }
    
    
    private void buscarAutoresVivosEmAno() {
        System.out.println("Digite o ano para verificar autores vivos: ");
        var ano = leitura.nextInt();
        leitura.nextLine();
        
        List<Autor> autores = repositorio.findAutoresVivosNoAno(ano);
        
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado no ano " + ano);
        } else {
            System.out.println("Autores vivos em " + ano + ":");
            autores.forEach(autor -> 
                System.out.println("Nome: " + autor.getNome() + 
                                 " | Nascimento: " + autor.getAnoNascimento() +
                                 " | Falecimento: " + autor.getAnoDeFalecimento())
            );
        }
    }
    
    
	private void listarAutoresBuscados() {

		List<Livro> livros = repositorio.findAll();
		if (livros.isEmpty()) {
			System.out.println("Nenhum livro cadastrado no banco de dados.");
			return;
		}

		System.out.println("Autores registrados:");
		livros.stream().map(Livro::getAutor).distinct().sorted(Comparator.comparing(Autor::getNome))
				.forEach(autor -> System.out.println("Nome: " + autor.getNome() + " | Nascimento: "
						+ autor.getAnoNascimento() + " | Falecimento: " + autor.getAnoDeFalecimento()));

	}
    
    
    
	private void listarLivrosBuscados() {

		List<Livro> livros = repositorio.findAll();
		if (livros.isEmpty()) {
			System.out.println("Nenhum livro cadastrado no banco de dados.");
			return;
		}

		System.out.println("Livros registrados:");
		livros.stream().sorted(Comparator.comparing(Livro::getTitulo)).forEach(livro -> System.out.println("Título: "
				+ livro.getTitulo() + " | Autor: " + livro.getAutor().getNome() + " | Idioma: " + livro.getIdiomas()));

	}
	
	private void buscarLivrosWeb() {
	    System.out.println("Digite o título do livro para busca: ");
	    var tituloLivro = leitura.nextLine();
	    
	    try {
	        String url = ENDERECO + tituloLivro.replace(" ", "%20");
	        System.out.println("Buscando na URL: " + url);
	        
	        var json = consumo.obterDados(url);
	        
	        var node = new ObjectMapper().readTree(json);
	        var resultadosNode = node.get("results");
	        
	        if (resultadosNode == null || resultadosNode.isEmpty() || !resultadosNode.isArray()) {
	            System.out.println("Nenhum livro encontrado com o título: " + tituloLivro);
	            return;
	        }
	        
	        DadosLivro[] resultados = conversor.obterDados(resultadosNode.toString(), DadosLivro[].class);
	        
	        if (resultados.length == 0) {
	            System.out.println("Nenhum livro encontrado com o título: " + tituloLivro);
	            return;
	        }
	        
	        DadosLivro dados = resultados[0];
	        System.out.println("Livro encontrado: " + dados.titulo());
	        
	        String nomeAutor = dados.getNomeAutor();
	        Integer anoNascimento = dados.getAnoNascimentoAutor();
	        Integer anoFalecimento = dados.getAnoFalecimentoAutor();
	        String categoria = dados.getPrimeiraCategoria();
	        String idioma = dados.getPrimeiroIdioma();
	        
	        System.out.println("Autor: " + nomeAutor);
	        System.out.println("Ano de nascimento: " + anoNascimento);
	        System.out.println("Ano de falecimento: " + anoFalecimento);
	        System.out.println("Categoria: " + categoria);
	        System.out.println("Idioma: " + idioma);
	        
	        Optional<Livro> livroExistente = repositorio.findByTituloContainingIgnoreCase(dados.titulo());
	        if (livroExistente.isPresent()) {
	            System.out.println("Livro já cadastrado no banco de dados.");
	            return;
	        }
	        
	        Optional<Autor> autorExistente = autorRepository.findByNomeContainingIgnoreCase(nomeAutor);
	        Autor autor;
	        
	        if (autorExistente.isPresent()) {
	            autor = autorExistente.get();
	            System.out.println("Autor encontrado no banco: " + autor.getNome());
	        } else {
	            autor = new Autor(nomeAutor, anoNascimento, anoFalecimento);
	            autor = autorRepository.save(autor); // Salva o autor primeiro
	            System.out.println("Novo autor salvo: " + autor.getNome());
	        }
	        
	        Livro livro = new Livro(dados.titulo(), categoria, idioma, dados.numeroDeDownloads(), autor);
	        repositorio.save(livro);
	        
	        System.out.println("Livro salvo com sucesso!");
	        
	    } catch (Exception e) {
	        System.out.println("Erro ao buscar livro: " + e.getMessage());
	        e.printStackTrace();
	    
	}
}
	
}
