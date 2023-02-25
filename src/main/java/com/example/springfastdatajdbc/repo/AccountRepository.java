package com.example.springfastdatajdbc.repo;

import com.example.springfastdatajdbc.model.Account;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

import static com.example.springfastdatajdbc.util.strings.SqlQueries.UPDATE_ACCOUNT_BALANCE_SQL;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query(value = UPDATE_ACCOUNT_BALANCE_SQL)
    @Modifying
    void updateAccountBalance(BigDecimal balance, long id);
}
