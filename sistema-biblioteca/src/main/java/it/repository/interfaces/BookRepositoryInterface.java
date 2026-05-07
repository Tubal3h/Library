package it.repository.interfaces;

import java.util.List;



import it.entity.Book;
import it.entity.RentalRecord;
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.repository.BookRepositoryException;

public interface BookRepositoryInterface {
	public String getAuthorFullNameById(int authorId) throws BookRepositoryException;
	public String getPublisherNameById(int publisherId) throws BookRepositoryException;
	public String getIsbnCodeById(int isbnId) throws BookRepositoryException;
	public String getCategoryNameById(int categoryId) throws BookRepositoryException;
	public int countAllBooks() throws BookRepositoryException, QueryIsNullOrNegativeExcepetion;
	public int countAllNotEliminatedBooks() throws BookRepositoryException, QueryIsNullOrNegativeExcepetion;
	public List<Book> getAllBooks() throws BookRepositoryException;
	public int insertBookByIsbn(String isbn) throws BookRepositoryException;
	public int deleteBookById(int id) throws BookRepositoryException;
	public int insertBookByTitleAndIsbn(String title, String isbn) throws BookRepositoryException;
	public List<RentalRecord> getBooksByEditionId(int editionId, boolean includeDeleted) throws BookRepositoryException;
}
