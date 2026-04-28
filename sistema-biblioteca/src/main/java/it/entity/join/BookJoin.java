package it.entity.join;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */


/**
 * Entità che rappresenta la vista aggregata di un libro nel sistema,
 * ottenuta tramite JOIN tra le tabelle books, edition, books_names, author, publisher e category.
 */
public class BookJoin {

    private EditionJoin edition;


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
    public BookJoin(EditionJoin edition) {
        this.edition = edition;
    }

    public EditionJoin getEdition() {
        return edition;
    }

    public void setEdition(EditionJoin edition) {
        this.edition = edition; 
    }
    
    @Override
    public String toString() {
        return "BookJoin [edition=" + edition + "]";
    }
}
