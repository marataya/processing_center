package org.bank.processing_center.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "cards")
public class Card {
    private String cardNumber;
    private LocalDate expirationDate;
    private String holderName;
    private Long cardStatusId;
    private Long paymentSystemId;
    private Long accountId;
    private Timestamp receivedFromIssuingBank;
    private Timestamp sentToIssuingBank;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Card() {
    }

    public Card(
        Long id,
        String cardNumber,
        LocalDate expirationDate,
        String holderName,
        Long cardStatusId,
        Long paymentSystemId,
                Long cardStatusId, Long paymentSystemId, Long accountId,
                Timestamp receivedFromIssuingBank, Timestamp sentToIssuingBank) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.holderName = holderName;
        this.cardStatusId = cardStatusId;
        this.paymentSystemId = paymentSystemId;
        this.accountId = accountId;
        this.receivedFromIssuingBank = receivedFromIssuingBank;
        this.sentToIssuingBank = sentToIssuingBank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Long getCardStatusId() {
        return cardStatusId;
    }

    public void setCardStatusId(Long cardStatusId) {
        this.cardStatusId = cardStatusId;
    }

    public Long getPaymentSystemId() {
        return paymentSystemId;
    }

    public void setPaymentSystemId(Long paymentSystemId) {
        this.paymentSystemId = paymentSystemId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Timestamp getReceivedFromIssuingBank() {
        return receivedFromIssuingBank;
    }

    public void setReceivedFromIssuingBank(Timestamp receivedFromIssuingBank) {
        this.receivedFromIssuingBank = receivedFromIssuingBank;
    }

    public Timestamp getSentToIssuingBank() {
        return sentToIssuingBank;
    }

    public void setSentToIssuingBank(Timestamp sentToIssuingBank) {
        this.sentToIssuingBank = sentToIssuingBank;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", holderName='" + holderName + '\'' +
                ", cardStatusId=" + cardStatusId +
                ", paymentSystemId=" + paymentSystemId +
                ", accountId=" + accountId +
                ", receivedFromIssuingBank=" + receivedFromIssuingBank +
                ", sentToIssuingBank=" + sentToIssuingBank +
                '}';
    }
}