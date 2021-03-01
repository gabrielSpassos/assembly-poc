package com.gabrielspassos.poc.controller.v1;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public interface BaseVersion {

    int OK = 200;
    String OK_MESSAGE = "Successful operation.";
    int BAD_REQUEST = 400;
    String BAD_REQUEST_MESSAGE = "Input has something wrong";
    int NOT_FOUND = 404;
    String NOT_FOUND_MESSAGE = "Operation not found something";
}
