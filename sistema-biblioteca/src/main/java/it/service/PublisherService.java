package it.service;

import java.util.List;
import org.springframework.stereotype.Service;

import it.dto.PublisherDto;
import it.repository.PublisherRepository;
import it.entity.Publisher;

/**
 * Servizio per la gestione degli editori dei libri.
 * Fornisce metodi per il recupero e la mappatura degli editori in DTO.
 */
@Service
public class PublisherService {
    
    private final PublisherRepository publisherRepository;

    /**
     * Costruttore per PublisherService.
     * 
     * @param publisherRepository Repository per l'accesso ai dati degli editori
     */
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    /**
     * Recupera la lista di tutti gli editori registrati nel sistema.
     * 
     * @return Lista di PublisherDto
     */
    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.getAllPublishers().stream()
            .map(this::toPublisherDto)
            .toList();
    }

    /**
     * Converte un'entità Publisher in un DTO PublisherDto.
     * 
     * @param publisher L'entità da convertire
     * @return Il DTO corrispondente
     */
    private PublisherDto toPublisherDto(Publisher publisher) {
        PublisherDto dto = new PublisherDto();
        dto.setPublisherId(publisher.getPublisherId());
        dto.setPublisherName(publisher.getPublisherName());
        return dto;
    }
}
