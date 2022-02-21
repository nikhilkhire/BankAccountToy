package com.code.factory.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.code.factory.domain.Account;
import com.code.factory.domain.LoginCredentials;
import com.code.factory.domain.Transactions;
import com.code.factory.domain.UserProfileEntity;
import com.code.factory.enums.AccountType;
import com.code.factory.enums.TransactionType;
import com.code.factory.repositories.AccountRepository;
import com.code.factory.repositories.TransactionsRepository;
import com.code.factory.util.StandardJsonResponse;

@Service
public class AccountService{
    @Autowired
    AuthenticationService  authenticationService;
    @Autowired
    AccountRepository      accountRepository;
    @Autowired
    TransactionsRepository transactionsRepository;

    public Optional<Account> checkBalance(final LoginCredentials loginCredentials){

        final UserProfileEntity userProfile = this.authenticationService.checkUserAuthentication(loginCredentials);

        if(userProfile != null){
            return userProfile.getAccountList()
                    .stream()
                    .filter(account -> account.getIban().equals(loginCredentials.getIbanFrom()))
                    .findFirst();
        }else{
            return Optional.empty();
        }
    }

    public Boolean deposit(final LoginCredentials loginCredentials, final StandardJsonResponse jsonResponse){

        final UserProfileEntity userProfile = this.authenticationService.checkUserAuthentication(loginCredentials);

        if(userProfile != null){
            return this.deposit(loginCredentials.getIbanTo(), loginCredentials.getAmount(), jsonResponse);
        }else{
            return false;
        }
    }

    private boolean deposit(final String IBAN, final BigDecimal amount, final StandardJsonResponse jsonResponse){
        final Account account = this.accountRepository.getById(IBAN);
        if(account != null){
            account.setAmount(account.getAmount().add(amount));
            this.accountRepository.save(account);
            this.addTransaction(amount, account, TransactionType.DEPOSIT);
            return true;
        }else{
            jsonResponse.setSuccess(false, "Error", "Invalid deposit account details");
            jsonResponse.setHttpResponseCode(HttpStatus.NOT_ACCEPTABLE.value());
            return false;
        }
    }

    private void addTransaction(final BigDecimal amount, final Account account, final TransactionType type){
        final Transactions transactions = new Transactions();
        transactions.setAccount(account);
        transactions.setType(type.getId());
        transactions.setAmount(amount);
        this.transactionsRepository.save(transactions);
    }

    private boolean withdraw(
            final String ibanFrom,
            final String ibanTo,
            final BigDecimal amount,
            final UserProfileEntity userProfile,
            final StandardJsonResponse jsonResponse){
        final Optional<Account> account = userProfile.getAccountList()
                .stream()
                .filter(
                        acc -> !acc.getType().equals(AccountType.LOAN)
                                && this.isTransferringToReferenceAccount(acc, userProfile.getAccountList(), ibanTo)
                                && acc.getIban().equals(ibanFrom))
                .findFirst();
        if(account.isPresent() && account.get().getAmount().compareTo(amount) > 0){
            account.get().setAmount(account.get().getAmount().subtract(amount));
            this.accountRepository.save(account.get());
            this.addTransaction(amount, account.get(), TransactionType.WITHDRAW);
        }else{
            jsonResponse.setSuccess(false, "Error", "Invalid account/Insufficient funds");
            jsonResponse.setHttpResponseCode(HttpStatus.NOT_ACCEPTABLE.value());
            return false;
        }
        return true;
    }

    private boolean isTransferringToReferenceAccount(
            final Account acc,
            final List<Account> accountList,
            final String ibanTo){
        return acc.getType().equals(AccountType.SAVING) && accountList.stream()
                .anyMatch(
                        account -> account.getIban().equals(ibanTo) && account.getType().equals(AccountType.CHECKING));
    }

    public boolean transfer(final LoginCredentials loginCredentials, final StandardJsonResponse jsonResponse){

        final UserProfileEntity userProfile = this.authenticationService.checkUserAuthentication(loginCredentials);

        if(userProfile != null){
            if(this.withdraw(
                    loginCredentials.getIbanFrom(),
                    loginCredentials.getIbanTo(),
                    loginCredentials.getAmount(),
                    userProfile,
                    jsonResponse)){
                return this.deposit(loginCredentials.getIbanTo(), loginCredentials.getAmount(), jsonResponse);
            }
            return false;
        }else{
            jsonResponse.setSuccess(false, "Error", "User authentication failed");
            jsonResponse.setHttpResponseCode(HttpStatus.NOT_ACCEPTABLE.value());
            return false;
        }
    }

    public boolean fetchTransactionHistory(
            final LoginCredentials loginCredentials,
            final StandardJsonResponse jsonResponse,
            final HashMap<String, Object> responseData){

        final UserProfileEntity userProfile = this.authenticationService.checkUserAuthentication(loginCredentials);

        if(userProfile != null){

            final Optional<Account> account = userProfile.getAccountList()
                    .stream()
                    .filter(acc -> acc.getIban().equals(loginCredentials.getIbanFrom()))
                    .findFirst();
            if(account.isPresent()){
                final List<Transactions> transactions = this.transactionsRepository.findByIban(account.get().getIban());
                responseData.put("Transactions", transactions);
                jsonResponse.setData(responseData);
                return true;
            }else{
                jsonResponse.setSuccess(false, "Error", "Invalid IBAN");
                jsonResponse.setHttpResponseCode(HttpStatus.NOT_ACCEPTABLE.value());
                return false;
            }
        }else{
            jsonResponse.setSuccess(false, "Error", "User authentication failed");
            jsonResponse.setHttpResponseCode(HttpStatus.NOT_ACCEPTABLE.value());
            return false;
        }
    }
}