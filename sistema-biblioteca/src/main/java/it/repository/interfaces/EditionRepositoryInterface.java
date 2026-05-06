package it.repository.interfaces;

import java.time.LocalDate;
import java.util.List;

import it.entity.Edition;
import it.exception.Repository.InsertEditionException;
import it.exception.Repository.EditionException;

/**
 * Interfaccia per il repository delle edizioni dei libri.
 * Definisce le operazioni CRUD per le edizioni dei libri.
 */
public interface EditionRepositoryInterface {

	/**
	 * Recupera la lista di tutte le edizioni dei libri.
	 *
	 * @return Lista di tutte le edizioni
	 * @throws EditionException se nessuna edizione è presente nel database
	 */
	public List<Edition> getAllEditions() throws EditionException;

	/**
	 * Recupera un'edizione specifica tramite il suo ID.
	 *
	 * @param editionId ID dell'edizione
	 * @return Oggetto Edition corrispondente all'ID fornito
	 * @throws EditionException se nessuna edizione è presente nel database
	 */
	public Edition findById(int editionId) throws EditionException;
	/**
	 * Inserisce una nuova edizione dei libri nel database.
	 * @param title Titolo del libro
	 * @param authorName Nome dell'autore
	 * @param authotLastName Cognome dell'autore
	 * @param publisher Casa editrice
	 * @param publishingDate Data di pubblicazione
	 * @param category Categoria del libro
	 * @param isbn Codice ISBN
	 * @throws InsertEditionException se l'edizione non viene inserita correttamente
	 */
	public void insertEdition(String title, 
							 String authorName,
							 String authotLastName,
							 String publisher, 
							 LocalDate publishingDate, 
							 String category, 
							 String isbn) throws InsertEditionException;
	/**
	 * Aggiorna il titolo dell'edizione.
	 * @param editionId ID dell'edizione
	 * @param bookNameId ID del titolo
	 * @throws EditionException se nessuna edizione è presente nel database
	 */
	public void updateBookTitleId(int editionId, int bookNameId) throws EditionException;
	/**
	 * Aggiorna l'autore dell'edizione.
	 * @param editionId ID dell'edizione
	 * @param authorId ID dell'autore
	 * @throws EditionException se nessuna edizione è presente nel database
	 */
	public void updateAuthorId(int editionId, int authorId) throws EditionException;
	/**
	 * Aggiorna la casa editrice dell'edizione.
	 * @param editionId ID dell'edizione
	 * @param publisherId ID della casa editrice
	 * @throws EditionException se nessuna edizione è presente nel database
	 */
	public void updatePublisherId(int editionId, int publisherId) throws EditionException;
	/**
	 * Aggiorna la categoria dell'edizione.
	 * @param editionId ID dell'edizione
	 * @param categoryId ID della categoria
	 * @throws EditionException se nessuna edizione è presente nel database
	 */
	public void updateCategoryId(int editionId, int categoryId) throws EditionException;
}
