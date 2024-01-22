package com.ssafy.backend.common.exception;

import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.common.response.BaseResponseStatus;
import com.ssafy.backend.common.utils.HttpResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<HttpResponseBody<?>> catchMyException(MyException e) {
        return new ResponseEntity(new HttpResponseBody<>("FAIL", e.getMessage()), e.getStatus());
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> baseException(BaseException e) {
        log.warn("Handle CommonException: {}", e.getStatus());
        return new BaseResponse<>(e.getStatus());
    }

}