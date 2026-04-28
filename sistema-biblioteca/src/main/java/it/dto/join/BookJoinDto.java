package it.dto.join;


public class BookJoinDto {
    private EditionJoinDto editionJoinDto;
    
    public BookJoinDto() {
    }
    
    public BookJoinDto(
        EditionJoinDto editionJoinDto
    ) {
        this.editionJoinDto = editionJoinDto;
    }
    
    public EditionJoinDto getEdition() {
        return editionJoinDto;
    }

    public void setEdition(EditionJoinDto editionJoinDto) {
        this.editionJoinDto = editionJoinDto;
    }
    @Override
    public String toString() {
        return "BookDto [editionDto=" + editionJoinDto + "]";
    }    
}
