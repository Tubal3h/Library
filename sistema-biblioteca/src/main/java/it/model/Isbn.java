package it.model;

public class Isbn {
    private int isbn_id;
    private String code;

    public Isbn() {
    }

    public Isbn(String code) {
        this.code = code;
    }

    public int getIsbn_id() {
        return isbn_id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return "Isbn [isbn_id=" + isbn_id + ", code=" + code + "]";
    }
}
