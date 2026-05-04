package it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale dell'applicazione Sistema Biblioteca.
 * Avvia il contesto Spring Boot e configura automaticamente i componenti.
 */
@SpringBootApplication
public class SistemaBibliotecaApplication {

	/**
	 * Punto di ingresso principale dell'applicazione.
	 *
	 * @param args Argomenti passati da riga di comando

	 */
	public static void main(String[] args) {
		SpringApplication.run(SistemaBibliotecaApplication.class, args);
	}

}
