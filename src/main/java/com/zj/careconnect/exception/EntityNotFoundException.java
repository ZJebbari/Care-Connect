package com.zj.careconnect.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(int id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
