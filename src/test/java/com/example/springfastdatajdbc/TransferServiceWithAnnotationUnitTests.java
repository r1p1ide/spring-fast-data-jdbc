package com.example.springfastdatajdbc;


import com.example.springfastdatajdbc.dto.TransferMoneyDto;
import com.example.springfastdatajdbc.exception.AccountNotFoundException;
import com.example.springfastdatajdbc.model.Account;
import com.example.springfastdatajdbc.repo.AccountRepository;
import com.example.springfastdatajdbc.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransferServiceWithAnnotationUnitTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Test
    @DisplayName("Test transferMoney() without exception")
    public void moneyTransferredWithoutException() {

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

    @Test
    @DisplayName("Test transferMoney() with AccountNotFoundException")
    public void moneyTransferredWithException() {

        Account senderAccount = new Account();
        senderAccount.setId(1);
        senderAccount.setName("Alexander");
        senderAccount.setBalance(new BigDecimal(1000));

        given(accountRepository.findById(senderAccount.getId())).willReturn(Optional.of(senderAccount));
        given(accountRepository.findById(2L)).willReturn(Optional.empty());

        TransferMoneyDto request = new TransferMoneyDto();
        request.setIdFrom(senderAccount.getId());
        request.setIdTo(2L);
        request.setMoney(new BigDecimal(100));
        assertThrows(AccountNotFoundException.class, () -> transferService.transferMoney(request));

        verify(accountRepository, never()).updateAccountBalance(any(), anyLong());
    }
}
