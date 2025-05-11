package org.bank.processing_center.model;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number")
    private String accountNumber;
    @Column
    private Double balance;
    @Column(name = "currency_id")
    private Long currencyId;
    @Column(name = "issuing_bank_id")
    private Long issuingBankId;

    public Account() {
    }

    public Account(Long id, String accountNumber, Double balance, Long currencyId, Long issuingBankId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currencyId = currencyId;
        this.issuingBankId = issuingBankId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getIssuingBankId() {
        return issuingBankId;
    }

    public void setIssuingBankId(Long issuingBankId) {
        this.issuingBankId = issuingBankId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", currencyId=" + currencyId +
                ", issuingBankId=" + issuingBankId +
                '}';
    }
}
