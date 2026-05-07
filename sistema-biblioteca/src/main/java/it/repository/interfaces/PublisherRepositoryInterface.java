package it.repository.interfaces;

import java.util.List;

import it.entity.Publisher;
import it.exception.QueryIsNullOrNegativeExcepetion;
import it.exception.repository.PublisherExceptionRepository;

public interface PublisherRepositoryInterface {
	
	public List<Publisher> getAllPublishers()throws PublisherExceptionRepository;
	public void insertPublisherByPubliserName(String publisherName) throws PublisherExceptionRepository;
	public void updatePublisher(Publisher publisher) throws PublisherExceptionRepository;
	public int deletePublisher(Publisher publisher) throws PublisherExceptionRepository;
	public Boolean isPublisherPresent(Publisher publisher) throws PublisherExceptionRepository, QueryIsNullOrNegativeExcepetion;
}
