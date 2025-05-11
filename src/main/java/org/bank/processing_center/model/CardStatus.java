package org.bank.processing_center.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "card_statuses")
public class CardStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String cardStatusName;

    public CardStatus() {
    }

    public CardStatus(Long id, String cardStatusName) {
        this.id = id;
        this.cardStatusName = cardStatusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardStatusName() {
        return cardStatusName;
    }

    public void setCardStatusName(String cardStatusName) {
        this.cardStatusName = cardStatusName;
    }

    @Override
    public String toString() {
        return "CardStatus{" +
                "id=" + id +
                ", cardStatusName='" + cardStatusName + '\'' +
                '}';
    }
}
