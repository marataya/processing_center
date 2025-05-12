package org.bank.processing_center.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "terminal")
@Data
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "terminal_id")
    private String terminalId;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "mcc_id")
    private MerchantCategoryCode mcc;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pos_id")
    private SalesPoint pos;
}