package com.gabrielspassos.poc.exception;

import org.springframework.http.HttpStatus;

public class AssemblyStatusInvalidException extends HttpException {

    public AssemblyStatusInvalidException() {
        super(HttpStatus.BAD_REQUEST, "Assembleia em status invalido para operacao", "3");
    }
}
