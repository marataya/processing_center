package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "sales_point")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalesPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "pos_name")
    private String posName;

    @Column(name = "pos_address")
    private String posAddress;

    @Column(name = "pos_inn")
    private String posInn;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "acquiring_bank_id")
    private AcquiringBank acquiringBank;

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
}