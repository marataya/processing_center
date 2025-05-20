package org.bank.processing_center.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Long id;             //Уникальный идентификатор счёта.

    @Column(name = "account_number", nullable = false, length = 50)
    @Size(max = 50, message = "Account number must not exceed 50")
    @NotBlank
    private String accountNumber;      //Номер счёта.

    @Column(name = "balance", nullable = false, precision = 19, scale = 4)
    @Digits(integer=15, fraction = 4, message = "15.4f type format is expected")
    @DecimalMin(value = "0.00", inclusive = true, message = "Balance must be non negative")
    @NotNull
    private BigDecimal balance;        //Баланс счёта.

    @ManyToOne
    @JoinColumn(name = "currency_id")
    @NotNull
    private Currency currency;     //Ссылка на валюту.

    @ManyToOne
    @JoinColumn(name = "issuing_bank_id")
    @NotNull
    private IssuingBank issuingBank;  //Ссылка на банк-эмитент

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Account that = (Account) o;
        return getId() != null && Objects.equals(getId(), that.getId());
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
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", currency=" + (currency != null ? currency.getId() : "null") + // Avoid N+1 in toString
                ", issuingBank=" + (issuingBank != null ? issuingBank.getId() : "null") + // Avoid N+1 in toString
                '}';
    }
}
