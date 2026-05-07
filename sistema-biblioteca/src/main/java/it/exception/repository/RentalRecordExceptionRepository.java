package it.exception.repository;

import it.entity.RentalRecord;

public class RentalRecordExceptionRepository extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RentalRecordExceptionRepository(String message) {
        super(message);
    }

    public RentalRecordExceptionRepository() {
        super();
    }

    public RentalRecordExceptionRepository throwExceptionIfRentalNotFound(Integer rentalId) {
        if (rentalId == 0 || rentalId == null) {
            throw new RentalRecordExceptionRepository("Errore: ID noleggio non valido.");
        }
        return this;
    }

    public RentalRecordExceptionRepository throwExceptionIfUserIdIsInvalid(Integer userId) {
        if (userId == 0 || userId == null) {
            throw new RentalRecordExceptionRepository("Errore: ID utente non valido.");
        }
        return this;
    }

    public RentalRecordExceptionRepository throwExceptionIfRentalRecordIsInvalid(RentalRecord rental) {
        if (rental == null) {
            throw new RentalRecordExceptionRepository("Errore: Record di noleggio non valido.");
        }
        return this;
    }

    public RentalRecordExceptionRepository throwExceptionIfBookIdAndRentalIdAreInvalid(Integer bookId, Integer rentalId) {
        if (bookId == 0 || bookId == null) {
            throw new RentalRecordExceptionRepository("Errore: ID libro non valido.");
        }
        return throwExceptionIfRentalNotFound(rentalId);
    }

    public RentalRecordExceptionRepository throwExceptionIfBookIdIsInvalid(Integer bookId) {
        if (bookId == 0 || bookId == null) {
            throw new RentalRecordExceptionRepository("Errore: ID libro non valido.");
        }
        return this;
    }



}
