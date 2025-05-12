package org.bank.processing_center.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "acquiring_bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquiringBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acquiring_bank_id", nullable = false, unique = true)
    private Long acquiringBankId;

    @Column(name = "bic", nullable = false, unique = true)
    private String bic;

    @Column(name = "abbreviated_name")
    private String abbreviatedName;

}