package org.bank.processing_center.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

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

    @Column(name = "card_number", nullable = false, length = 16)
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
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

    public boolean validateCardNumber() {
        if (cardNumber == null) {
            return false;
        }
        String cleanedCardNumber = cardNumber.replaceAll("[^\\d]", "");
        return cleanedCardNumber.length() == 16 && Pattern.matches("\\d{16}", cleanedCardNumber);
    }

    public boolean validateExpirationDate() {
        if (expirationDate == null) {
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        return expirationDate.isAfter(currentDate);
    }

    public boolean validateHolderName() {
        if (holderName == null) {
            return false;
        }
        return holderName.matches("^[a-zA-Z\\s]+$");
    }

    /**
     * Checks if a card is expired
     *
     * @param card Card to check
     * @return true if the card is expired, false otherwise
     */
    public boolean isCardExpired(Card card) {
        return this.getExpirationDate().isBefore(java.time.LocalDate.now());
    }

    /**
     * Masks a card number for display (e.g., **** **** **** 1234)
     *
     * @param cardNumber Full card number
     * @return Masked card number
     */
    public String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return cardNumber;
        }

        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
        return "**** **** **** " + lastFourDigits;
    }

    /**
     * Validates a card number using the Luhn algorithm
     *
     * @param cardNumber Card number to validate
     * @return true if the card number is valid, false otherwise
     */
    public boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null) {
            return false;
        }

        // Remove any non-digit characters
        String digitsOnly = cardNumber.replaceAll("\\D", "");

        if (digitsOnly.length() < 13 || digitsOnly.length() > 19) {
            // Most card numbers are between 13 and 19 digits
            return false;
        }

        // Luhn algorithm implementation
        int sum = 0;
        boolean alternate = false;

        // Process digits from right to left
        for (int i = digitsOnly.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(digitsOnly.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = digit - 9; // Same as summing the digits (e.g., 12 -> 1+2 = 3, which is 12-9)
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        // If the sum is divisible by 10, the card number is valid
        return sum % 10 == 0;
    }

    /**
     * Validates a card before saving or updating
     *
     * @param card Card to validate
     * @return true if the card is valid, false otherwise
     */
    public boolean validateCard(Card card) {
        if (card == null) {
            System.err.println("Ошибка: Карта не может быть null");
            return false;
        }

        if (card.getCardNumber() == null || card.getCardNumber().isEmpty()) {
            System.err.println("Ошибка: Номер карты не может быть пустым");
            return false;
        }

        if (!isValidCardNumber(card.getCardNumber())) {
            System.err.println("Ошибка: Недействительный номер карты (не прошел проверку по алгоритму Луна): " + card.getCardNumber());
            return false;
        }

        if (card.getExpirationDate() == null) {
            System.err.println("Ошибка: Дата истечения срока действия не может быть null");
            return false;
        }

        if (isCardExpired(card)) {
            System.err.println("Ошибка: Срок действия карты истек: " + card.getExpirationDate());
            return false;
        }

        if (card.getHolderName() == null || card.getHolderName().isEmpty()) {
            System.err.println("Ошибка: Имя держателя карты не может быть пустым");
            return false;
        }

        return true;
    }
}