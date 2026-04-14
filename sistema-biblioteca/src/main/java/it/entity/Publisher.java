package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Entità che rappresenta una casa editrice nel sistema.
 */
public class Publisher {
    private int publisherId;
    private String publisherName;

    /**
     * Costruttore di default.
     */
    public Publisher() {
    }

    /**
     * Costruttore con parametri.
     * 
     * @param publisherId ID della casa editrice
     * @param publisherName Nome della casa editrice
     */
    public Publisher(int publisherId, String publisherName) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }
    
    /**
     * Costruttore con solo il nome.
     * 
     * @param publisherName Nome della casa editrice
     */
    public Publisher(String publisherName) {
        this.publisherName = publisherName;
    }

    /**
     * @return ID della casa editrice
     */
    public int getPublisherId() {
        return publisherId;
    }

    /**
     * @param publisherId ID della casa editrice
     */
    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    /**
     * @return Nome della casa editrice
     */
    public String getPublisherName() {
        return publisherName;
    }

    /**
     * @param publisherName Nome della casa editrice
     */
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public String toString() {
        return "Publisher [publisherId=" + publisherId + ", publisherName=" + publisherName + "]";
    }
}


