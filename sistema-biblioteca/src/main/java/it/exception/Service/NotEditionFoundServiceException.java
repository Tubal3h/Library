package it.exception.Service;

public class NotEditionFoundServiceException extends RuntimeException {
    public NotEditionFoundServiceException(String message) {
        super(message);
    }
}
