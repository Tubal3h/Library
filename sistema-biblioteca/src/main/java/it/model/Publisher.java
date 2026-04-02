package it.model;

public class Publisher {
    private int publisherId;
    private String publisherName;
    
    public Publisher(String publisherName) {
        this.publisherName = publisherName;
    }
    public int getPublisherId() {
        return publisherId;
    }
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
