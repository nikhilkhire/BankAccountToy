package com.code.factory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.factory.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String>{}
