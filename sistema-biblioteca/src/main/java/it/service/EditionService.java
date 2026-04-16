package it.service;

import java.util.List;


import org.springframework.stereotype.Service;

import it.dto.EditionDto;
import it.entity.EditionJoin;
import it.repository.EditionRepository;

@Service
public class EditionService {
    private final EditionRepository editionRepository;

    public EditionService(EditionRepository editionRepository) {
        this.editionRepository = editionRepository;
    }

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
