package com.vasche.shoestore.domain.exception;

/**
 * Это исключение выбрасывается, когда в JDBC произошли какие-либо ошибки в процессе доставания объекта из БД.
 */
public class ResourceMappingException extends RuntimeException{
    public ResourceMappingException(String message) {
        super(message);
    }
}
