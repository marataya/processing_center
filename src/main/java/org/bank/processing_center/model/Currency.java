package org.bank.processing_center.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "currency")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;              //Уникальный идентификатор валюты.

    @Column(name = "currency_digital_code", nullable = false, length = 3)
    @Size(min = 3, max = 3, message = "Digital code must be exactly 3 characters long")
    @NotBlank
    private String currencyDigitalCode; //Цифровой код валюты (например, 840 для USD).

    @Column(name = "currency_letter_code", nullable = false)
    @NotBlank
    @Size(min = 3, max = 3, message = "Letter code must be exactly 3 characters long")
    private String currencyLetterCode;  //Буквенный код валюты (например, USD).

    @Column(name = "currency_name", nullable = false)
    @NotBlank
    private String currencyName;        //Название валюты.

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Currency account = (Currency) o;
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
}
