package com.gabrielspassos.poc.exception;

import org.springframework.http.HttpStatus;

public class InvalidVoteChoiceException extends HttpException {

    public InvalidVoteChoiceException() {
        super(HttpStatus.BAD_REQUEST, "Escolha de voto invalida", "7");
    }
}
