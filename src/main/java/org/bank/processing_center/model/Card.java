package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
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
    private Timestamp receivedFromIssuingBank;
    
    @Column(name = "sent_to_issuing_bank", nullable = false)
    private Timestamp sentToIssuingBank;

}