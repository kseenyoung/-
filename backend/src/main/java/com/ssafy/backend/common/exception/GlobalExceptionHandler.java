package com.ssafy.backend.common.exception;

import com.ssafy.backend.common.utils.HttpResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<HttpResponseBody<?>> catchMyException(MyException e) {
        return new ResponseEntity(new HttpResponseBody<>("FAIL", e.getMessage()), e.getStatus());
    }

}