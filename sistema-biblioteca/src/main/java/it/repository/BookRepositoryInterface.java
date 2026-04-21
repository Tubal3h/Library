package it.repository;

import java.util.List;

import it.entity.BookJoin;
import it.exception.InsertBookNameException;

public interface BookRepositoryInterface {
	public String getAuthorFullNameByID(int authorId);
	public String getPublisherNameByID(int publisherId);
	public String getIsbnCodeByID(int isbnId);
	public String getCategoryNameByID(int categoryId);
	public int countBooks();
	public List<BookJoin> getAllBooks();
	public int insertBookByIsbn(String isbn);
	public int deleteBookById(int id);
	public int insertBookByTitle(String title) throws InsertBookNameException;
	public List<BookJoin> getBooksByEditionId(int editionId, boolean includeDeleted);
}
