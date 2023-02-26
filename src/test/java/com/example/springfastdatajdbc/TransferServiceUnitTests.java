package com.example.springfastdatajdbc;

import com.example.springfastdatajdbc.dto.TransferMoneyDto;
import com.example.springfastdatajdbc.model.Account;
import com.example.springfastdatajdbc.repo.AccountRepository;
import com.example.springfastdatajdbc.service.TransferService;
import com.example.springfastdatajdbc.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TransferServiceUnitTests {

    @Test
    @DisplayName("Test transferMoney() without exception")
    public void moneyTransferredWithoutException() {
        AccountRepository accountRepository = mock(AccountRepository.class);
        TransferService transferService = new TransferServiceImpl(accountRepository);

        Account senderAccount = new Account();
        senderAccount.setId(1);
        senderAccount.setName("Alexander");
        senderAccount.setBalance(new BigDecimal(1000));

        Account receiverAccount = new Account();
        receiverAccount.setId(2);
        receiverAccount.setName("Dmitry");
        receiverAccount.setBalance(new BigDecimal(1000));

        given(accountRepository.findById(senderAccount.getId())).willReturn(Optional.of(senderAccount));
        given(accountRepository.findById(receiverAccount.getId())).willReturn(Optional.of(receiverAccount));

        TransferMoneyDto request = new TransferMoneyDto();
        request.setIdFrom(senderAccount.getId());
        request.setIdTo(receiverAccount.getId());
        request.setMoney(new BigDecimal(100));
        transferService.transferMoney(request);

        verify(accountRepository).updateAccountBalance(new BigDecimal(900), 1);
        verify(accountRepository).updateAccountBalance(new BigDecimal(1100), 2);
    }
}
