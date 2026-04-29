package it.entity.join;

import it.entity.Edition;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */


/**
 * Entità che rappresenta la vista aggregata di un libro nel sistema,
 * ottenuta tramite JOIN tra le tabelle books, edition, books_names, author, publisher e category.
 */
public class BookJoin {

    private Edition edition;


    /**
     * Costruttore di default.
     */
    public BookJoin() {
    }

    /**
     * Costruttore con parametri completi.
     * 
     * @param edition
     */
    public BookJoin(Edition edition) {
        this.edition = edition;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition; 
    }
    
    @Override
    public String toString() {
        return "BookJoin [edition=" + edition + "]";
    }
}
