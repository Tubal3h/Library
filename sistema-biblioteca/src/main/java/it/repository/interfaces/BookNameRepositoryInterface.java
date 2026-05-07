package it.repository.interfaces;

import java.util.List;


import it.dto.BookNameDto;
import it.entity.BookName;
import it.exception.SelectAllBookNamesException;
import it.exception.repository.InsertBookNameException;

public interface BookNameRepositoryInterface {
	
	public List<BookName> getAllBookNames() throws SelectAllBookNamesException;
	public BookName getBookNameById(int titleId) throws SelectAllBookNamesException;
	public Boolean isTitleOnDb(String title);
	public void insertBookByTitle(String title) throws InsertBookNameException;
	public void updateBookTitle(BookNameDto bookNameDto); 
	public List<BookName> getBookNamesByTitle(String title);
}
