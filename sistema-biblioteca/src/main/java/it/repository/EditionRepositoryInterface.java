package it.repository;

import java.time.LocalDate;
import java.util.List;

import it.entity.Edition;
import it.entity.EditionJoin;

public interface EditionRepositoryInterface {
	public List<EditionJoin> getAllEditions();
	public Edition getEditionById(int editionId);
	public int insertEdition(String title, 
							 int authorId, 
							 int publisherId, 
							 LocalDate publishingDate, 
							 int categoryId, 
							 String isbn);
	
}
