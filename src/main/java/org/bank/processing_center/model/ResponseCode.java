package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "response_code")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "error_code", unique = true, nullable = false)
    private String errorCode;

    @Column(name = "error_description")
    private String errorDescription;
    
    @Column(name = "error_level")
    private String errorLevel;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ResponseCode that = (ResponseCode) o;
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