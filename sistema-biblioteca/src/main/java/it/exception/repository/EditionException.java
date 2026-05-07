package it.exception.repository;


public class EditionException extends RuntimeException {
    public EditionException(String message) {
        super(message);
    }

    public EditionException() {
        super("Errore nell'operazione sulle edizioni");
    }

    public EditionException throwExceptionIfNotFound() {
        throw new EditionException("Nessuna edizione trovata");
    }

    public EditionException throwExceptionIfEditionIdIsInvalid(Integer editionId) {
        if (editionId == 0 || editionId == null) {
            throw new EditionException("Edizione ID non valido");
        }
        return this;
    }

    public EditionException throwExceptionIfBookTitleIdIsInvalid(Integer editionId, Integer bookNameId) {
        if (bookNameId == 0 || bookNameId == null) {
            throw new EditionException("Errore update edition Titolo");
        }
        return throwExceptionIfEditionIdIsInvalid(editionId);
    }

    public EditionException throwExceptionIfBookAuthorIdIsInvalid(Integer editionId, Integer authorId) {
        if (authorId == 0 || authorId == null) {
            throw new EditionException("Errore update edition Autore");
        }
        return throwExceptionIfEditionIdIsInvalid(editionId);
    }

    public EditionException throwExceptionIfBookPublisherIdIsInvalid(Integer editionId, Integer publisherId) {
        if (publisherId == 0 || publisherId == null) {
            throw new EditionException("Errore update edition Editore");
        }
        return throwExceptionIfEditionIdIsInvalid(editionId);
    }

    public EditionException throwExceptionIfBookCategoryIdIsInvalid(Integer editionId, Integer categoryId) {
        if (categoryId == 0 || categoryId == null) {
            throw new EditionException("Errore update edition Categoria");
        }
        return throwExceptionIfEditionIdIsInvalid(editionId);
    }

    public EditionException throwExceptionIfIsbnIsInvalid(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new EditionException("ISBN non valido");
        }
        return this;
    }

}
