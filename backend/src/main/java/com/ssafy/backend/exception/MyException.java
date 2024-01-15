package com.ssafy.backend.exception;

import org.springframework.http.HttpStatus;

public class MyException extends Exception{
    private HttpStatus status;
    public MyException(String msg, HttpStatus status){
        super(msg);
        this.status = status;
    }
}
