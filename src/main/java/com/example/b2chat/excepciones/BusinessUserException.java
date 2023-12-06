package com.example.b2chat.excepciones;

import lombok.Getter;

@Getter
public class BusinessUserException extends RuntimeException{

    @Getter
    public enum Type {
        USER_NOT_FOUND("El usuario no existe");

        private final String message;

        public BusinessUserException build() {
            return new BusinessUserException(this);
        }

        Type(String message) {
            this.message = message;
        }

    }

    private final BusinessUserException.Type type;

    private BusinessUserException(BusinessUserException.Type type) {
        super(type.message);
        this.type = type;
    }

}
