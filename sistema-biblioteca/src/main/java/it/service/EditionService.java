package it.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import it.dto.EditionDto;
import it.dto.EditionJoinDto;
import it.entity.Edition;
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
    private List<EditionJoinDto> getAllEditions() {
        List<EditionJoin> editions = editionRepository.getAllEditions();
        return editions.stream().map(edition -> {
            EditionJoinDto dto = new EditionJoinDto();
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
	public List<EditionJoinDto> getEditionListByName(String search) {
		List<EditionJoinDto> myList = getAllEditions();
		List<EditionJoinDto> filteredList = new ArrayList<>();
		
		if(search != null && !search.isBlank()) {
			String [] strings = search.toLowerCase().trim().split("\\s+");
			for(EditionJoinDto edition : myList) {
				String title = edition.getBookName();
				String author = edition.getAuthorName();
				String category = edition.getCategoryName();
				String myEdition = edition.getPublisherName();
				String isbn = edition.getIsbnCode();
				String finalBook = (title + " " + author + " " + myEdition + " " + category + " " + isbn).toLowerCase();
				boolean allMatch = true;
				for(String s : strings) {
					if(!(finalBook.contains(s))) {
						allMatch = false;
						break;
					}
				}
				
				if(allMatch) {
					filteredList.add(edition);
				}
			}	
		}else {
			return myList;
		}
		if(filteredList.isEmpty() || filteredList == null) {
			return myList;
		}else {
			return filteredList;
		}
	}

    public void updateTitleId(EditionDto editionDto) {
        editionRepository.updateBookTitleId(editionDto.getEditionId(), editionDto.getBookNameId());
    }

    public Edition getEditionById(int editionId) {
        return editionRepository.findById(editionId);
    }

    public void updateAuthorId(int editionId, int authorId) {
        Edition edition = editionRepository.findById(editionId);
        edition.setAuthorId(authorId);
        editionRepository.updateAuthorId(edition.getEditionId(), edition.getAuthorId());
    }

    public void updatePublisherId(int editionId, int publisherId) {
        Edition edition = editionRepository.findById(editionId);
        edition.setPublisherId(publisherId);
        editionRepository.updatePublisherId(edition.getEditionId(), edition.getPublisherId());
    }

    public void updateCategoryId(int editionId, int categoryId) {
        Edition edition = editionRepository.findById(editionId);
        edition.setCategoryId(categoryId);
        editionRepository.updateCategoryId(edition.getEditionId(), edition.getCategoryId());
    }

    // private EditionDto convertEditionDto(Edition edition) {
    //     EditionDto dto = new EditionDto();
    //     dto.setEditionId(edition.getEditionId());
    //     dto.setBookNameId(edition.getBookNameId());
    //     dto.setAuthorId(edition.getAuthorId());
    //     dto.setPublisherId(edition.getPublisherId());
    //     dto.setCategoryId(edition.getCategoryId());
    //     dto.setPublishingDate(edition.getPublishingDate());
    //     dto.setIsbn(edition.getIsbn());
    //     return dto;
    // }

    // private EditionJoinDto convertEdition(EditionJoin edition) {
    //     EditionJoinDto dto = new EditionJoinDto();
    //     dto.setEditionId(edition.getEditionId());
    //     dto.setBookId(edition.getBookId());
    //     dto.setBookName(edition.getBookName());
    //     dto.setAuthorName(edition.getAuthor());
    //     dto.setPublisherName(edition.getPublisher());
    //     dto.setCategoryName(edition.getCategory());
    //     dto.setPublicationDate(edition.getPublishingDate());
    //     dto.setIsbnCode(edition.getIsbn());
    //     dto.setQuantity(edition.getQuantity());
    //     return dto;
    // }

    // private Edition convertEditionDto(EditionDto dto) {
    //     Edition edition = new Edition();
    //     edition.setEditionId(dto.getEditionId());
    //     edition.setBookNameId(dto.getBookNameId());
    //     edition.setPublishingDate(dto.getPublishingDate());
    //     edition.setIsbn(dto.getIsbn());
    //     edition.setAuthorId(dto.getAuthorId());
    //     edition.setCategoryId(dto.getCategoryId());
    //     edition.setPublisherId(dto.getPublisherId());
    //     return edition;
    // }

    // private EditionJoin convertEditionDto(EditionJoinDto editionJoinDto) {
    //     EditionJoin editionJoin = new EditionJoin();
    //     editionJoin.setEditionId(editionJoinDto.getEditionId());
    //     editionJoin.setBookId(editionJoinDto.getBookId());
    //     editionJoin.setBookName(editionJoinDto.getBookName());
    //     editionJoin.setAuthor(editionJoinDto.getAuthorName());
    //     editionJoin.setPublisher(editionJoinDto.getPublisherName());
    //     editionJoin.setCategory(editionJoinDto.getCategoryName());
    //     editionJoin.setPublishingDate(editionJoinDto.getPublicationDate());
    //     editionJoin.setIsbn(editionJoinDto.getIsbnCode());
    //     editionJoin.setStatus(editionJoinDto.getStatus());
    //     return editionJoin;
    // }
}
