package it.repository;

import java.time.LocalDate;
import java.util.List;

import it.entity.Edition;
import it.entity.EditionJoin;

public interface EditionRepositoryInterface {
	public List<EditionJoin> getAllEditions();
	public Edition getEditionById(int editionId);
	public int insertEdition(String title, 
							 String authorName,
							 String authotLastName,
							 String publisher, 
							 LocalDate publishingDate, 
							 String category, 
							 String isbn);
	
}
