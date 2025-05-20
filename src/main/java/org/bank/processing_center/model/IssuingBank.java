package org.bank.processing_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "issuing_banks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssuingBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;              // Уникальный идентификатор банка-эмитента.

    @Column(name = "bic", nullable = false, length = 9)
    private String bic;                 //Банковский идентификатор (BIC).

    @Column(name = "bin", nullable = false, length = 5)
    private String bin;                 //Идентификатор банка-эмитента для карт (BIN).

    @Column(name = "abbreviated_name", nullable = false)
    private String abbreviatedName;     //Сокращённое название банка.

    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        IssuingBank that = (IssuingBank) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "IssuingBank{" +
                "id=" + id +
                ", bic='" + bic + '\'' +
                ", bin='" + bin + '\'' +
                ", abbreviated_name='" + abbreviatedName + '\'' +
                '}';
    }
}
