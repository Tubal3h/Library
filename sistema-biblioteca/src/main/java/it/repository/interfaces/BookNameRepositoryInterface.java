package it.repository.interfaces;

import java.util.List;

import it.entity.BookNames;
import it.exception.InsertBookNameException;

public interface BookNameRepositoryInterface {
	
	public List<BookNames> getAllBookNames();
	public BookNames getBookNameById(int titleId);
	public Boolean isTitleOnDb(String title);
	public void insertBookByTitle(String title) throws InsertBookNameException;
	public void updateBookTitle(int editionId, String editionTitle); 
	public List<BookNames> getBookNamesByTitle(String title);
}
