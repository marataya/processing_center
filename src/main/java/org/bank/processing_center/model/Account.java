package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
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
