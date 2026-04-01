package it.model;

public class Publisher {
    private int publisher_id;
    private String publisher_name;
    
    public Publisher(String publisher_name) {
        this.publisher_name = publisher_name;
    }
    public int getPublisher_id() {
        return publisher_id;
    }
    public String getPublisher_name() {
        return publisher_name;
    }
    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }
    @Override
    public String toString() {
        return "Publisher [publisher_id=" + publisher_id + ", publisher_name=" + publisher_name + "]";
    }
}
