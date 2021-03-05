package com.gabrielspassos.poc.exception;

import org.springframework.http.HttpStatus;

public class CustomerInvalidVoteException extends HttpException {

    public CustomerInvalidVoteException() {
        super(HttpStatus.BAD_REQUEST, "Voto invalido", "5");
    }
}
