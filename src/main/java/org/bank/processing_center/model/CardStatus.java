package org.bank.processing_center.model;

public class CardStatus {
    private Long id;
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
