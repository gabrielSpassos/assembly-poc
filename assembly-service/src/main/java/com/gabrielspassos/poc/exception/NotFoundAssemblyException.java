package com.gabrielspassos.poc.exception;

import org.springframework.http.HttpStatus;

public class NotFoundAssemblyException extends HttpException {

    public NotFoundAssemblyException() {
        super(HttpStatus.NOT_FOUND, "Nao encontrado assembleia", "1");
    }

}
