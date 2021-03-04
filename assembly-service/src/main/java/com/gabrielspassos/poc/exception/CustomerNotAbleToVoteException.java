package com.gabrielspassos.poc.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotAbleToVoteException extends HttpException {

    public CustomerNotAbleToVoteException() {
        super(HttpStatus.NOT_FOUND, "Usuario nao habilitado para votar", "2");
    }

}
