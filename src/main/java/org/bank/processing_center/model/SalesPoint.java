package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_point")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalesPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "pos_name")
    private String posName;

    @Column(name = "pos_address")
    private String posAddress;

    @Column(name = "pos_inn")
    private String posInn;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "acquiring_bank_id")
    private AcquiringBank acquiringBank;
}