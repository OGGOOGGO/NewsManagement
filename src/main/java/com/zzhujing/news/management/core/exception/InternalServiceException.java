package com.zzhujing.news.management.core.exception;


import lombok.Getter;
import com.zzhujing.news.management.core.result.ResultCode;

@Getter
public  class InternalServiceException extends RuntimeException {
    private ResultCode resultCode;

    public InternalServiceException(ResultCode resultCode) {
        super(resultCode.tip());
        this.resultCode = resultCode;
    }

    public InternalServiceException(ResultCode resultCode, Throwable t) {
        super(resultCode.tip(), t);
        this.resultCode = resultCode;
    }
}
