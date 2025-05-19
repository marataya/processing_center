package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id; //id (bigint): Уникальный идентификатор карты.

    @Column(name = "card_number", nullable = false, length = 50)
    private String cardNumber;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "holder_name", nullable = false, length = 50)
    private String holderName;

    @ManyToOne
    @JoinColumn(name = "card_status_id")
    private CardStatus cardStatus;

    @ManyToOne
    @JoinColumn(name = "payment_system_id")
    private PaymentSystem paymentSystem;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "received_from_issuing_bank", nullable = false)
    private Timestamp receivedFromIssuingBank;

    @Column(name = "sent_to_issuing_bank", nullable = false)
    private Timestamp sentToIssuingBank;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Account account = (Account) o;
        return getId() != null && Objects.equals(getId(), account.getId());
    }

    @Override
    public final int hashCode() {
        // This implementation is consistent with the equals method when ID is the basis of equality for persisted entities.
        // For transient entities (ID is null), it relies on the class hash code.
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
        // A common alternative for JPA entities:
        // return Objects.hash(getClass(), id); // If id can be null, this might not be ideal for transient instances
        // Or simply:
        // return 31; // For transient instances if ID is the sole business key for equality
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", holderName=" + holderName + '\'' +
                ", cardStatus=" + (cardStatus != null ? cardStatus.getId() : "null") + '\'' +
                ", paymentSystem=" + (paymentSystem != null ? paymentSystem.getId() : "null") + '\'' +
                ", account=" + (account != null ? account.getId() : "null") + '\'' +
                ", receivedFromIssuingBank=" + receivedFromIssuingBank + '\'' +
                ", sentToIssuingBank=" + sentToIssuingBank + '\'' +
                '}';
    }
}