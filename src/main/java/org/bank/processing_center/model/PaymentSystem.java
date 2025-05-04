package org.bank.processing_center.model;

public class PaymentSystem {
    private Long id;
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
