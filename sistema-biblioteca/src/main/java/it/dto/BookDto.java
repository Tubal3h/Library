package it.dto;

public class BookDto {
    private EditionDto editionJoinDto;
    
    public BookDto() {
    }
    
    public BookDto(
        EditionDto editionJoinDto
    ) {
        this.editionJoinDto = editionJoinDto;
    }
    
    public EditionDto getEdition() {
        return editionJoinDto;
    }

    public void setEdition(EditionDto editionJoinDto) {
        this.editionJoinDto = editionJoinDto;
    }
    @Override
    public String toString() {
        return "BookDto [editionDto=" + editionJoinDto + "]";
    }    
}
