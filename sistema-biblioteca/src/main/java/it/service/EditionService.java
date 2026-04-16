package it.service;

import java.util.ArrayList;
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
    private List<EditionDto> getAllEditions() {
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

    /**
     * Recupera una lista di edizioni filtrata per nome del libro.
     * 
     * @param search Il termine di ricerca per il nome del libro
     * @return Lista di EditionDto contenente le informazioni condensate delle edizioni filtrati per nome del libro
     */
	public List<EditionDto> getEditionListByName(String search) {
		List<EditionDto> myList = getAllEditions();
		List<EditionDto> filteredList = new ArrayList<>();
		if(search != null && !search.isBlank()) {
			for(EditionDto edition : myList) {
				if(edition.getBookName().replaceAll("\\s+","").toLowerCase().equals(search.replaceAll("\\s+","").toLowerCase())) {
					filteredList.add(edition);
				}
			}	
		}
		if(filteredList.isEmpty() || filteredList == null) {
			return myList;
		}else {
			return filteredList;
		}
	}
}
