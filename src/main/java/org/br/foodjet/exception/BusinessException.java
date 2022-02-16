package org.br.foodjet.exception;

import org.br.foodjet.resource.response.ErrorDetailTO;

public class BusinessException extends RuntimeException {

    ErrorDetailTO error;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, ErrorDetailTO errorDTO) {
        super(message);
        this.error = errorDTO;
    }

    public BusinessException(String message, String type) {

    }
}