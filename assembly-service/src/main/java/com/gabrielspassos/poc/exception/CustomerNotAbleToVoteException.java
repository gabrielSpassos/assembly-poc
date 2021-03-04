package com.gabrielspassos.poc.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotAbleToVoteException extends HttpException {

    public CustomerNotAbleToVoteException() {
        super(HttpStatus.BAD_REQUEST, "Usuario nao habilitado para votar", "2");
    }

}
