package com.vasche.shoestore.domain.exception;

/**
 * Это исключение выбрасывается, когда не удалось найти в БД какую-либо запись по id.
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
