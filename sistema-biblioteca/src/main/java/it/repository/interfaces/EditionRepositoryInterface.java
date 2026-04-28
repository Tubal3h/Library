package it.repository.interfaces;

import java.time.LocalDate;
import java.util.List;

import it.entity.Edition;
import it.entity.EditionJoin;

public interface EditionRepositoryInterface {
	public List<EditionJoin> getAllEditions();
	public Edition findById(int editionId);
	public void insertEdition(String title, 
							 String authorName,
							 String authotLastName,
							 String publisher, 
							 LocalDate publishingDate, 
							 String category, 
							 String isbn);
	
	public void updateBookTitleId(int editionId, int bookNameId);
	public void updateAuthorId(int editionId, int authorId);
	public void updatePublisherId(int editionId, int publisherId);
	public void updateCategoryId(int editionId, int categoryId);
}
