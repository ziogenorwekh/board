package com.portfolio.boardproject.vo;

import lombok.Getter;

import java.util.Date;

@Getter
public class ExceptionResponseVO {


    private String message;
    private Date timestamp;

    public ExceptionResponseVO(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
