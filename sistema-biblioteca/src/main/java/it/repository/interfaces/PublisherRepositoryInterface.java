package it.repository.interfaces;

import java.util.List;

import it.entity.Publisher;
import it.exception.repository.InsertPublisherException;

public interface PublisherRepositoryInterface {
	
	public List<Publisher> getAllPublishers();
	public void insertPublisherByPubliserName(String publisherName) throws InsertPublisherException;
	public void updatePublisher(Publisher publisher);
	public int deletePublisher(Publisher publisher);
	public Boolean isPublisherPresent(Publisher publisher);
}
