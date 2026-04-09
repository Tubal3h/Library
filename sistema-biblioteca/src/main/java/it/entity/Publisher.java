package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

public class Publisher {
    private int publisherId;
    private String publisherName;

    public Publisher() {
    }

    public Publisher(int publisherId, String publisherName) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }
    
    public Publisher(String publisherName) {
        this.publisherName = publisherName;
    }
    /**
     * @return ID della casa editrice
     */
    public int getPublisherId() {
        return publisherId;
    }
    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }
    /**
     * @return Nome della casa editrice
     */
    public String getPublisherName() {
        return publisherName;
    }
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    @Override
    public String toString() {
        return "Publisher [publisherId=" + publisherId + ", publisherName=" + publisherName + "]";
    }
}

