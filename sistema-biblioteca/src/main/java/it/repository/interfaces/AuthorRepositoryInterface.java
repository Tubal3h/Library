package it.repository.interfaces;

import java.util.List;


import it.entity.Author;
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.repository.AuthorRepositoryException;


public interface AuthorRepositoryInterface {
	public List<Author> getAllAuthors() throws AuthorRepositoryException;
	public void insertAuthorByNameAndLastName(String name, String lastName) throws AuthorRepositoryException ;
	public void updateAuthor(Author author)throws AuthorRepositoryException;
	public Boolean isAuthorPresent(String authorName, String authorLastName) throws AuthorRepositoryException, QueryIsNullOrNegativeExcepetion;
    public void insertAuthor(String authorName, String authorLastName) throws AuthorRepositoryException;
    public Author getAuthorById(int authorId) throws AuthorRepositoryException;
}
