package it.repository;

import java.util.List;

import it.entity.BookName;
import it.exception.InsertBookNameException;

public interface BookNameRepositoryInterface {
	
	public List<BookName> getAllBookNames();
	public String getBookNameById(int titleId);
	public int insertBookByTitle(String title) throws InsertBookNameException;
	public void updateBookTitle(int editionId, String editionTitle); 
	public List<BookName> getBookNamesByTitle(String title);
}
