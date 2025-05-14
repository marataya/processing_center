package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currencies")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;              //Уникальный идентификатор валюты.

    @Column(name = "currency_digital_code", nullable = false, length = 3)
    private String currencyDigitalCode; //Цифровой код валюты (например, 840 для USD).

    @Column(name = "currency_letter_code", nullable = false, length = 3)
    private String currencyLetterCode;  //Буквенный код валюты (например, USD).

    @Column(name = "currency_name", nullable = false, length = 255)
    private String currencyName;        //Название валюты.
}
