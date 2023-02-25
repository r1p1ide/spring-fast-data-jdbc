package com.example.springfastdatajdbc.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    private int status;
    private LocalDateTime dateTime;
    private boolean success;
}
