package it.repository.interfaces;

import java.util.List;


import it.dto.BookNameDto;
import it.entity.BookName;
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.repository.BookNamesRepositoryException;

public interface BookNameRepositoryInterface {
	
	public List<BookName> getAllBookNames() throws BookNamesRepositoryException;
	public BookName getBookNameById(int titleId) throws BookNamesRepositoryException;
	public Boolean isTitleOnDb(String title)throws QueryIsNullOrNegativeExcepetion, BookNamesRepositoryException;
	public void insertBookByTitle(String title) throws BookNamesRepositoryException;
	public void updateBookTitle(BookNameDto bookNameDto) throws BookNamesRepositoryException; 
	public List<BookName> getBookNamesByTitle(String title) throws BookNamesRepositoryException;
}
