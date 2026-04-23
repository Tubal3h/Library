package it.exception;

public class NoEditionIdFoundException extends Exception {
    private int id;
    
    public NoEditionIdFoundException(int id) {
        this.id = id;
    }
    
    public String toString() {
        return "id non trovato: " + id;
    }
}
