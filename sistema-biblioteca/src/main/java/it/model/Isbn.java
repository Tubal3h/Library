package it.model;

public class Isbn {
    private int isbnId;
    private String code;

    public Isbn() {
    }

    public Isbn(String code) {
        this.code = code;
    }

    public int getIsbnId() {
        return isbnId;
    }

    public void setIsbnId(int isbnId) {
        this.isbnId = isbnId;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return "Isbn [isbnId=" + isbnId + ", code=" + code + "]";
    }
}
