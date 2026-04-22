package it.repository;

import java.util.List;

import it.entity.Publisher;
import it.exception.InsertPublisherException;

public interface PublisherRepositoryInterface {
	
	public List<Publisher> getAllPublishers();
	public int insertPublisherByPubliserName(String publisherName) throws InsertPublisherException;
}
