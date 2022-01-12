package org.br.foodjet.exception;

import org.br.foodjet.resource.response.ErrorDetailTO;

public class GenericException extends RuntimeException {

    ErrorDetailTO error;

    public GenericException() {
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, ErrorDetailTO errorDTO) {
        super(message);
        this.error = errorDTO;
    }
}