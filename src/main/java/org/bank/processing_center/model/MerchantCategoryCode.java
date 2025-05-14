package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "merchant_category_code")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MerchantCategoryCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "mcc", length = 4, nullable = false, unique = true)
    private String mcc;

    @Column(name = "mcc_name", nullable = false)
    private String mccName;

}