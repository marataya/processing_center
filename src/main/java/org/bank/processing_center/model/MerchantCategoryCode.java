package org.bank.processing_center.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "merchant_category_code")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MerchantCategoryCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mcc", length = 4)
    @Size(min = 4, max = 4, message = "MCC must be exactly 4 characters long") // Add Size annotation for validation
    @NotBlank
    private String mcc;

    @Column(name = "mcc_name")
    @NotBlank
    private String mccName;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MerchantCategoryCode that = (MerchantCategoryCode) o;
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
}