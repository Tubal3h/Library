package it.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.getAllPublishers().stream()
            .map(this::toPublisherDto)
            .toList();
    }

    /**
     * Recupera un publisher tramite il suo ID.
     * 
     * @param id L'ID del publisher da recuperare
     * @return Il publisher corrispondente
     */
    @Transactional(readOnly = true)
    public PublisherDto getPublisherById(int id) {
        return toPublisherDto(publisherRepository.getPublisherById(id));
    }

    /**
     * Recupera l'ID di un publisher tramite il suo nome.
     * 
     * @param name Il nome del publisher da recuperare
     * @return L'ID del publisher corrispondente
     */
    public int getPublisherId(String name) {
        return publisherRepository.getAllPublishers().stream()
                .filter(p -> p.getPublisherName().equalsIgnoreCase(name))
                .findFirst()
                .get()
                .getPublisherId();
    }

    @Transactional
    public int insertAndGetPublisherId(String name) {
        try {
            publisherRepository.insertPublisherByPubliserName(name);
        } catch (Exception e) {}
        return getPublisherId(name);
    }

    /**
     * Aggiunge un nuovo publisher al database.
     * 
     * @param publisherDto Il publisher da aggiungere
     */
    public void addPublisher(PublisherDto publisherDto) {
        if (publisherDto == null || publisherDto.getPublisherName() == null) {
            throw new IllegalArgumentException("L'editore non può essere null");
        }

        if(publisherDto.getPublisherName().isBlank()) {
            throw new IllegalArgumentException("L'editore non può essere vuoto");
        }

        Publisher publisher = toPublisherEntity(publisherDto);

        publisherRepository.insertPublisherByPubliserName(publisher.getPublisherName());
    }

    /**
     * Elimina un publisher dal database.
     * 
     * @param publisherDto Il publisher da eliminare
     */
    public void deletePublisher(PublisherDto publisherDto) {
        if (publisherDto == null || publisherDto.getPublisherName() == null) {
            throw new IllegalArgumentException("L'editore non può essere null");
        }

        if(publisherDto.getPublisherName().isBlank()) {
            throw new IllegalArgumentException("L'editore non può essere vuoto");
        }

        Publisher publisher = toPublisherEntity(publisherDto);

        publisherRepository.deletePublisher(publisher);
    }

    /**
     * Aggiorna un publisher nel database.
     * 
     * @param publisherDto Il publisher da aggiornare
     */
    public void updatePublisher(PublisherDto publisherDto) {
        if (publisherDto == null || publisherDto.getPublisherName() == null) {
            throw new IllegalArgumentException("L'editore non può essere null");
        }

        if(publisherDto.getPublisherName().isBlank()) {
            throw new IllegalArgumentException("L'editore non può essere vuoto");
        }

        Publisher publisher = toPublisherEntity(publisherDto);

        publisherRepository.updatePublisher(publisher);
    }

    /**
     * Verifica se un publisher esiste nel database.
     * 
     * @param publisherDto Il publisher da verificare
     * @return True se il publisher esiste, false altrimenti
     */
    public boolean isPublisherPresent(PublisherDto publisherDto) {
        if (publisherDto == null || publisherDto.getPublisherName() == null) {
            throw new IllegalArgumentException("L'editore non può essere null");
        }

        if(publisherDto.getPublisherName().isBlank()) {
            throw new IllegalArgumentException("L'editore non può essere vuoto");
        }

        Publisher publisher = toPublisherEntity(publisherDto);

        return publisherRepository.isPublisherPresent(publisher);
    }

    /**
     * Converte un DTO PublisherDto in un'entità Publisher.
     * 
     * @param publisherDto Il DTO da convertire
     * @return L'entità Publisher corrispondente
     */
    private Publisher toPublisherEntity(PublisherDto publisherDto) {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(publisherDto.getPublisherId());
        publisher.setPublisherName(publisherDto.getPublisherName());
        return publisher;
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
