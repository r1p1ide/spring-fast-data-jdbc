package com.example.springfastdatajdbc.service.impl;

import com.example.springfastdatajdbc.dto.TransferMoneyDto;
import com.example.springfastdatajdbc.exception.AccountNotFoundException;
import com.example.springfastdatajdbc.model.Account;
import com.example.springfastdatajdbc.repo.AccountRepository;
import com.example.springfastdatajdbc.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void transferMoney(TransferMoneyDto dto) {

        Account senderAccount =
                accountRepository.findById(dto.getIdFrom()).orElseThrow(AccountNotFoundException::new);
        log.info("Sender's balance before transfer money {}", senderAccount.getBalance());

        Account receiverAccount =
                accountRepository.findById(dto.getIdTo()).orElseThrow(AccountNotFoundException::new);
        log.info("Receiver's balance before transfer money {}", receiverAccount.getBalance());

        BigDecimal senderBalanceAfterSubtract = senderAccount.getBalance().subtract(dto.getMoney());
        accountRepository.updateAccountBalance(senderBalanceAfterSubtract, senderAccount.getId());
        log.info("Sender's balance after transfer money {}", senderBalanceAfterSubtract);

        BigDecimal receiverBalanceAfterAdd = receiverAccount.getBalance().add(dto.getMoney());
        accountRepository.updateAccountBalance(receiverBalanceAfterAdd, receiverAccount.getId());
        log.info("Receiver's balance after transfer money {}", receiverBalanceAfterAdd);
    }
}
