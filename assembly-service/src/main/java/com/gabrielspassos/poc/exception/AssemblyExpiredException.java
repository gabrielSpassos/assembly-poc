package com.gabrielspassos.poc.exception;

import org.springframework.http.HttpStatus;

public class AssemblyExpiredException extends HttpException {

    public AssemblyExpiredException() {
        super(HttpStatus.BAD_REQUEST, "Assembleia expirada", "4");
    }
}
