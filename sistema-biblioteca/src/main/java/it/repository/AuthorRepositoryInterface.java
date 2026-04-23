package it.repository;

import java.util.List;

import it.entity.Author;
import it.exception.InsertAuthorException;

public interface AuthorRepositoryInterface {
	public List<Author> getAllAuthors();
	public int insertAuthorByNameAndLastName(String name, String lastName) throws InsertAuthorException ;
	public void updateAuthor(Author author)throws InsertAuthorException;
	public boolean isAuthorPresent(String authorName, String authorLastName) throws InsertAuthorException;
}
