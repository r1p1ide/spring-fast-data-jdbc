package com.example.springfastdatajdbc;

import com.example.springfastdatajdbc.dto.TransferMoneyDto;
import com.example.springfastdatajdbc.exception.AccountNotFoundException;
import com.example.springfastdatajdbc.model.Account;
import com.example.springfastdatajdbc.repo.AccountRepository;
import com.example.springfastdatajdbc.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransferServiceIntegrationTests {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private TransferServiceImpl transferService;

    @Test
    public void moneyTransferredWithoutException() {
        Account sender = new Account();
        sender.setId(1);
        sender.setName("Alexander");
        sender.setBalance(new BigDecimal(1000));

        Account receiver = new Account();
        receiver.setId(2);
        receiver.setName("Dmitry");
        receiver.setBalance(new BigDecimal(1000));

        when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(sender));
        when(accountRepository.findById(2L)).thenReturn(java.util.Optional.of(receiver));

        TransferMoneyDto dto = new TransferMoneyDto();
        dto.setIdFrom(sender.getId());
        dto.setIdTo(receiver.getId());
        dto.setMoney(new BigDecimal(100));

        transferService.transferMoney(dto);

        verify(accountRepository).updateAccountBalance(new BigDecimal(900), 1L);
        verify(accountRepository).updateAccountBalance(new BigDecimal(1100), 2L);
    }

    @Test
    public void moneyTransferredWithException() {
        Account sender = new Account();
        sender.setId(1);
        sender.setName("Alexander");
        sender.setBalance(new BigDecimal(1000));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());

        TransferMoneyDto dto = new TransferMoneyDto();
        dto.setIdFrom(sender.getId());
        dto.setIdTo(2L);
        dto.setMoney(new BigDecimal(100));

        assertThrows(AccountNotFoundException.class, () -> transferService.transferMoney(dto));

        verify(accountRepository, never()).updateAccountBalance(any(), anyLong());
    }
}
