package com.vasche.shoestore.domain.exception;

/**
 * Это исключение выбрасывается, когда пользователь пытается обратиться к задаче, к которой не имеет доступа.
 */
public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException() {
        super();
    }
}
