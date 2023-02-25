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

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void transferMoney(TransferMoneyDto dto) {

        Account sendersAccount =
                accountRepository.findById(dto.getIdFrom()).orElseThrow(AccountNotFoundException::new);
        log.info("Sender's balance before transfer money {}", sendersAccount.getBalance());

        Account receiversAccount =
                accountRepository.findById(dto.getIdTo()).orElseThrow(AccountNotFoundException::new);
        log.info("Receiver's balance before transfer money {}", receiversAccount.getBalance());

        sendersAccount.setBalance(sendersAccount.getBalance().subtract(dto.getMoney()));
        accountRepository.save(sendersAccount);
        log.info("Sender's balance after transfer money {}", sendersAccount.getBalance());

        receiversAccount.setBalance(receiversAccount.getBalance().add(dto.getMoney()));
        accountRepository.save(receiversAccount);
        log.info("Receiver's balance after transfer money {}", receiversAccount.getBalance());
    }
}
