package com.example.springfastdatajdbc.controller;

import com.example.springfastdatajdbc.dto.TransferMoneyDto;
import com.example.springfastdatajdbc.service.TransferService;
import com.example.springfastdatajdbc.util.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<BaseResponse> transferMoney(@RequestBody TransferMoneyDto dto) {

        transferService.transferMoney(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse(200, LocalDateTime.now(), true));
    }
}
