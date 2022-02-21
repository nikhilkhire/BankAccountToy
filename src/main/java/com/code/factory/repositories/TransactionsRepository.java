package com.code.factory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.code.factory.domain.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long>{

    @Query(value = "SELECT t FROM Transactions t WHERE t.account.iban=?1")
    List<Transactions> findByIban(String iban);
}
