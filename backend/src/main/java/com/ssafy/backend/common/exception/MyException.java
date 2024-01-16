package com.ssafy.backend.common.exception;

import org.springframework.http.HttpStatus;

public class MyException extends RuntimeException{
    private HttpStatus status;
    public MyException(String msg, HttpStatus status){
        super(msg);
        this.status = status;
    }
}
