package it.repository.interfaces;

import java.util.List;

<<<<<<< HEAD

import it.entity.join.BookRecordJoin;
import it.entity.Book;
=======
import it.entity.Book;
import it.entity.RentalRecord;
>>>>>>> db0141d310098044398ca7b76a7bca1344b8f6d3
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
