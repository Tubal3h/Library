package it.repository.interfaces;

import java.util.List;

import it.entity.BookName;
import it.exception.InsertBookNameException;

public interface BookNameRepositoryInterface {
	
	public List<BookName> getAllBookNames();
	public BookName getBookNameById(int titleId);
	public Boolean isTitleOnDb(String title);
	public void insertBookByTitle(String title) throws InsertBookNameException;
	public void updateBookTitle(int editionId, String editionTitle); 
	public List<BookName> getBookNamesByTitle(String title);
}
