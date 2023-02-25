package com.example.springfastdatajdbc.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorDetails extends BaseResponse {

    private String message;

    public ErrorDetails(int status, LocalDateTime dateTime, boolean success, String message) {
        super(status, dateTime, success);
        this.message = message;
    }
}
