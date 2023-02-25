package com.example.springfastdatajdbc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferMoneyDto {

    private long idFrom;
    private long idTo;
    private BigDecimal money;
}
