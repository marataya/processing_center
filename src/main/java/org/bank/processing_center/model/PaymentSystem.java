package org.bank.processing_center.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_systems")
public class PaymentSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "paymentSystemName")
    private String paymentSystemName;

    public PaymentSystem() {
    }

    public PaymentSystem(Long id, String paymentSystemName) {
        this.id = id;
        this.paymentSystemName = paymentSystemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentSystemName() {
        return paymentSystemName;
    }

    public void setPaymentSystemName(String paymentSystemName) {
        this.paymentSystemName = paymentSystemName;
    }

    @Override
    public String toString() {
        return "PaymentSystem{" +
                "id=" + id +
                ", paymentSystemName='" + paymentSystemName + '\'' +
                '}';
    }
}
