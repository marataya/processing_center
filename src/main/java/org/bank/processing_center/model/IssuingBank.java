package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "issuing_banks")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IssuingBank {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // Уникальный идентификатор банка-эмитента.

    @Column(name = "bic", nullable = false, length = 9)
    private String bic;                 //Банковский идентификатор (BIC).

    @Column(name = "bin", nullable = false, length = 5)
    private String bin;                 //Идентификатор банка-эмитента для карт (BIN).

    @Column(name = "abbreviated_name", nullable = false, length = 255)
    private String abbreviatedName;     //Сокращённое название банка.
}
