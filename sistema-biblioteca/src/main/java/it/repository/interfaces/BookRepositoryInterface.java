package it.repository.interfaces;

import java.util.List;

import it.entity.Book;
import it.entity.RentalRecord;
import it.exception.InsertBookNameException;

public interface BookRepositoryInterface {
	public String getAuthorFullNameByID(int authorId);
	public String getPublisherNameByID(int publisherId);
	public String getIsbnCodeByID(int isbnId);
	public String getCategoryNameByID(int categoryId);
	public int countAllBooks();
	public int countAllNotEliminatedBooks();
	public List<Book> getAllBooks();
	public int insertBookByIsbn(String isbn);
	public int deleteBookById(int id);
	public void insertBookByTitle(String title) throws InsertBookNameException;
	public List<RentalRecord> getBooksByEditionId(int editionId, boolean includeDeleted);
}
