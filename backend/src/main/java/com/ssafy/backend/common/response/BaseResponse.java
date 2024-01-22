package com.ssafy.backend.common.response;

import static com.ssafy.backend.common.response.BaseResponseStatus.SUCCESS;

public class BaseResponse<T> {
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    private T result;

    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }

    public BaseResponse(BaseResponseStatus status){
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public T getResult() {
        return result;
    }
}
