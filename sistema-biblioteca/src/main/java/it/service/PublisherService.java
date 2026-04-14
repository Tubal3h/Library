package it.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.dto.PublisherDto;
import it.repository.PublisherRepository;
import it.entity.Publisher;

@Service
public class PublisherService {
    
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.getAllPublishers().stream()
            .map(this::toPublisherDto)
            .toList();
    }

    private PublisherDto toPublisherDto(Publisher publisher) {
        PublisherDto dto = new PublisherDto();
        dto.setPublisherId(publisher.getPublisherId());
        dto.setPublisherName(publisher.getPublisherName());
        return dto;
    }
}
