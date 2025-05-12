package org.bank.processing_center.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;             //Уникальный идентификатор счёта.

    @Column(name = "account_number", nullable = false, length = 50)
    private String accountNumber;      //Номер счёта.

    @Column(name = "balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;        //Баланс счёта.

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;     //Ссылка на валюту.

    @ManyToOne
    @JoinColumn(name = "issuing_bank_id")
    private IssuingBank issuingBank;  //Ссылка на банк-эмитент
}
