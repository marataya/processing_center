package org.bank.processing_center.model;

import jakarta.persistence.*;
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
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "bic", nullable = false, unique = true)
    private String bic;

    @Column(name = "abbreviated_name")
    private String abbreviatedName;

}