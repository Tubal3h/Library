package it.service;

import java.util.List;
import org.springframework.stereotype.Service;

import it.dto.EditionDto;
import it.entity.EditionJoin;
import it.repository.EditionRepository;

/**
 * Servizio per la gestione delle edizioni dei libri.
 * Un'edizione raggruppa più copie fisiche dello stesso titolo.
 */
@Service
public class EditionService {
    private final EditionRepository editionRepository;

    /**
     * Costruttore per EditionService.
     * 
     * @param editionRepository Repository per l'accesso ai dati delle edizioni
     */
    public EditionService(EditionRepository editionRepository) {
        this.editionRepository = editionRepository;
    }

    /**
     * Recupera la lista di tutte le edizioni con i dettagli aggregati.
     * 
     * @return Lista di EditionDto contenente i metadati completi dell'edizione
     */
    public List<EditionDto> getAllEditions() {
        List<EditionJoin> editions = editionRepository.getAllEditions();
        return editions.stream().map(edition -> {
            EditionDto dto = new EditionDto();
            dto.setEditionId(edition.getEditionId());
            dto.setBookId(edition.getBookId());
            dto.setBookName(edition.getBookName());
            dto.setAuthorName(edition.getAuthor());
            dto.setPublisherName(edition.getPublisher());
            dto.setCategoryName(edition.getCategory());
            dto.setPublicationDate(edition.getPublishingDate());
            dto.setIsbnCode(edition.getIsbn());
            dto.setQuantity(edition.getQuantity());
            return dto;
        }).toList();
    }
}
