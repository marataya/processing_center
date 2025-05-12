package org.bank.processing_center.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "currencies")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              //Уникальный идентификатор валюты.

    @Column(name = "currency_digital_code", nullable = false, length = 3)
    private String currencyDigitalCode; //Цифровой код валюты (например, 840 для USD).

    @Column(name = "currency_letter_code", nullable = false, length = 3)
    private String currencyLetterCode;  //Буквенный код валюты (например, USD).

    @Column(name = "currency_digital_code_account", nullable = false, length = 3)
    private String currencyDigitalCodeAccount;  //Буквенный код валюты (например, USD).

    @Column(name = "currency_name", nullable = false, length = 255)
    private String currencyName;        //Название валюты.
}
