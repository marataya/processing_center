package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "transaction_name")
    private String transactionName;

    // Consider adding fetch = FetchType.LAZY for performance
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    // Consider adding fetch = FetchType.LAZY for performance
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    // Consider adding fetch = FetchType.LAZY for performance
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    // Consider adding fetch = FetchType.LAZY for performance
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal_id")
    private Terminal terminal;

    // Consider adding fetch = FetchType.LAZY for performance
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "response_code_id")
    private ResponseCode responseCode;

    @Column(name = "authorization_code", length=6)
    private String authorizationCode;

    @Column(name = "received_from_issuing_bank")
    private LocalDateTime receivedFromIssuingBank;

    @Column(name = "sent_to_issuing_bank")
    private LocalDateTime sentToIssuingBank;

    // Add other fields as necessary

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        // Check effective class, considering Hibernate proxies
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Transaction that = (Transaction) o; // Correct cast to Transaction
        // Equality based on ID for persisted entities
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        // This implementation is consistent with the equals method when ID is the basis of equality for persisted entities.
        // For transient entities (ID is null), it relies on the class hash code.
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                .hashCode()
                : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", sum=" + sum +
                ", transactionName='" + transactionName + '\'' +
                // Print IDs of related entities to avoid lazy loading issues
                ", account=" + (account != null ? account.getId() : "null") +
                ", transactionType=" + (transactionType != null ? transactionType.getId() : "null") +
                ", card=" + (card != null ? card.getId() : "null") +
                ", terminal=" + (terminal != null ? terminal.getId() : "null") +
                ", responseCode=" + (responseCode != null ? responseCode.getId() : "null") +
                ", authorizationCode='" + authorizationCode + '\'' +
                ", receivedFromIssuingBank=" + receivedFromIssuingBank +
                ", sentToIssuingBank=" + sentToIssuingBank +
                '}';
    }
}