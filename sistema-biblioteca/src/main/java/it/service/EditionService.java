package it.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import it.dto.AuthorDto;
import it.dto.BookNameDto;
import it.dto.CategoryDto;
import it.dto.EditionDto;
import it.dto.PublisherDto;
import it.entity.Edition;
import it.exception.repository.EditionException;
import it.exception.service.EditionServiceException;
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
    private List<EditionDto> getAllEditions() throws EditionServiceException{
        try {
        List<Edition> editions = editionRepository.getAllEditions();
        return editions.stream().map(edition -> {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setAuthorName(edition.getAuthor().getAuthorName());
            authorDto.setAuthorLastName(edition.getAuthor().getAuthorLastName());

            BookNameDto bookNameDto = new BookNameDto();
            bookNameDto.setTitle(edition.getBookName().getTitle());

            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryName(edition.getCategory().getCategoryName());
            
            PublisherDto publisherDto = new PublisherDto();
            publisherDto.setPublisherName(edition.getPublisher().getPublisherName());
            
            EditionDto editionDto = new EditionDto();
            editionDto.setEditionId(edition.getEditionId());
            editionDto.setBookNameDto(bookNameDto);
            editionDto.setAuthorDto(authorDto);
            editionDto.setPublisherDto(publisherDto);
            editionDto.setCategoryDto(categoryDto);
            editionDto.setPublishingDate(edition.getPublishingDate());
            editionDto.setIsbn(edition.getIsbn());
            editionDto.setQuantity(edition.getQuantity());
            return editionDto;
        }).toList();
        } catch (EditionException e) {
            throw new EditionServiceException(e.getMessage());
        }

    }

    /**
     * Recupera una lista di edizioni filtrata per nome del libro.
     * 
     * @param search Il termine di ricerca per il nome del libro
     * @return Lista di EditionDto contenente le informazioni condensate delle edizioni filtrati per nome del libro
     */
	public List<EditionDto> getEditionListByName(String search) throws EditionServiceException {
		
        try {
        List<EditionDto> myList = getAllEditions();
		List<EditionDto> filteredList = new ArrayList<>();
		
		if(search != null && !search.isBlank()) {
			String [] strings = search.toLowerCase().trim().split("\\s+");
			for(EditionDto edition : myList) {
				String title = edition.getBookNameDto().getTitle();
				String authorName = edition.getAuthorDto().getAuthorName();
				String authorLastName = edition.getAuthorDto().getAuthorLastName();
				String category = edition.getCategoryDto().getCategoryName();
				String myEdition = edition.getPublisherDto().getPublisherName();
				String isbn = edition.getIsbn();
				String finalBook = (title + " " + authorName + " " + authorLastName + " " + myEdition + " " + category + " " + isbn).toLowerCase();
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
        } catch (EditionException e) {
            throw new EditionServiceException(e.getMessage());
        }
	}

    public Edition getEditionById(int editionId) throws EditionServiceException{
        try {
            return editionRepository.findById(editionId);
        } catch (EditionException e) {
            throw new EditionServiceException(e.getMessage());
        }
    }

    public void updateTitleId(EditionDto editionDto) throws EditionServiceException{
        try {
            editionRepository.updateBookTitleId(editionDto.getEditionId(), editionDto.getBookNameDto().getBookNameId());
        } catch (EditionException e) {
            throw new EditionServiceException(e.getMessage());
        }
    }

    public void updateAuthorId(EditionDto editionDto) throws EditionServiceException{
        try {
            editionRepository.updateAuthorId(editionDto.getEditionId(), editionDto.getAuthorDto().getAuthorId());
        } catch (EditionException e) {
            throw new EditionServiceException(e.getMessage());
        }
    }

    public void updatePublisherId(EditionDto editionDto) throws EditionServiceException{
        try {
            editionRepository.updatePublisherId(editionDto.getEditionId(), editionDto.getPublisherDto().getPublisherId());
        } catch (EditionException e) {
            throw new EditionServiceException(e.getMessage());
        }
    }

    public void updateCategoryId(EditionDto editionDto) throws EditionServiceException{
        try {
            editionRepository.updateCategoryId(editionDto.getEditionId(), editionDto.getCategoryDto().getCategoryId());
        } catch (EditionException e) {
            throw new EditionServiceException(e.getMessage());
        }
    }

}
