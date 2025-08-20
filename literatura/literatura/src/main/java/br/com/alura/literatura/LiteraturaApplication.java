package br.com.alura.literatura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.literatura.principal.Principal;
import br.com.alura.literatura.repository.AutorRepository;
import br.com.alura.literatura.repository.BookRepository;



@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner{
	
	@Autowired
	private BookRepository repositorio;
	
	@Autowired
    private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio, autorRepository);
		principal.ExibeMenu();
		
	}

}