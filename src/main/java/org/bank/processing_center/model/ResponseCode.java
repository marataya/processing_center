package org.bank.processing_center.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "response_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "error_code", unique = true, nullable = false)
    private String errorCode;

    @Column(name = "error_description")
    private String errorDescription;
    
    @Column(name = "error_level")
    private String errorLevel;
}