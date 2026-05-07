package it.repository.interfaces;

import java.util.List;


import it.entity.Book;
import it.entity.RentalRecord;
import it.exception.repository.InsertBookNameException;

public interface BookRepositoryInterface {
	public String getAuthorFullNameById(int authorId);
	public String getPublisherNameById(int publisherId);
	public String getIsbnCodeById(int isbnId);
	public String getCategoryNameById(int categoryId);
	public int countAllBooks();
	public int countAllNotEliminatedBooks();
	public List<Book> getAllBooks();
	public int insertBookByIsbn(String isbn);
	public int deleteBookById(int id);
	public int insertBookByTitleAndIsbn(String title, String isbn) throws InsertBookNameException;
	public List<RentalRecord> getBooksByEditionId(int editionId, boolean includeDeleted);
}
