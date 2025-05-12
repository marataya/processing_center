package org.bank.processing_center.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false, unique = true)
    private Long id; //id (bigint): Уникальный идентификатор карты.

    @Column(name = "card_number", nullable = false, length = 50)
    private String cardNumber;
    
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
    
    @Column(name = "holder_name", nullable = false, length = 50)
    private String holderName;
    
    @ManyToOne
    @JoinColumn(name = "card_status_id")
    private CardStatus cardStatus;
    
    @ManyToOne
    @JoinColumn(name = "payment_system_id")
    private PaymentSystem paymentSystem;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
    @Column(name = "received_from_issuing_bank", nullable = false)
    private LocalDateTime receivedFromIssuingBank;
    
    @Column(name = "sent_to_issuing_bank", nullable = false)
    private LocalDateTime sentToIssuingBank;

}