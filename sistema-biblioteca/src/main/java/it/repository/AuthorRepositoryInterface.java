package it.repository;

import java.util.List;

import it.entity.Author;
import it.exception.InsertAuthorException;

public interface AuthorRepositoryInterface {
	public List<Author> getAllAuthors();
	public void insertAuthorByNameAndLastName(String name, String lastName) throws InsertAuthorException ;
	public void updateAuthor(Author author)throws InsertAuthorException;
	public Boolean isAuthorPresent(String authorName, String authorLastName) throws InsertAuthorException;
}
