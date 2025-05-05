package org.bank.processing_center.view;

import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.Card;
import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.model.PaymentSystem;

import java.util.List;

/**
 * Handles console output for the application
 */
public class ConsoleView {

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String errorMessage) {
        System.err.println("ERROR: " + errorMessage);
    }

    public void showCardList(List<Card> cards) {
        System.out.println("\n=== Список карт ===");
        if (cards.isEmpty()) {
            System.out.println("Список карт пуст.");
        } else {
            for (Card card : cards) {
                System.out.println(card);
            }
        }
        System.out.println("===================\n");
    }

    public void showAccountList(List<Account> accounts) {
        System.out.println("\n=== Список счетов ===");
        if (accounts.isEmpty()) {
            System.out.println("Список счетов пуст.");
        } else {
            for (Account account : accounts) {
                System.out.println(account);
            }
        }
        System.out.println("=====================\n");
    }

    public void showCardStatusList(List<CardStatus> statuses) {
        System.out.println("\n=== Статусы карт ===");
        if (statuses.isEmpty()) {
            System.out.println("Список статусов пуст.");
        } else {
            for (CardStatus status : statuses) {
                System.out.println(status);
            }
        }
        System.out.println("===================\n");
    }

    public void showPaymentSystemList(List<PaymentSystem> systems) {
        System.out.println("\n=== Платежные системы ===");
        if (systems.isEmpty()) {
            System.out.println("Список платежных систем пуст.");
        } else {
            for (PaymentSystem system : systems) {
                System.out.println(system);
            }
        }
        System.out.println("========================\n");
    }

    public void showCardDetails(Card card) {
        if (card == null) {
            System.out.println("Карта не найдена.");
            return;
        }

        System.out.println("\n=== Детали карты ===");
        System.out.println("ID: " + card.getId());
        System.out.println("Номер карты: " + card.getCardNumber());
        System.out.println("Срок действия: " + card.getExpirationDate());
        System.out.println("Держатель: " + card.getHolderName());
        System.out.println("ID статуса карты: " + card.getCardStatusId());
        System.out.println("ID платежной системы: " + card.getPaymentSystemId());
        System.out.println("ID счета: " + card.getAccountId());
        System.out.println("Получено от банка-эмитента: " + card.getReceivedFromIssuingBank());
        System.out.println("Отправлено в банк-эмитент: " + card.getSentToIssuingBank());
        System.out.println("====================\n");
    }

    public void showOperationResult(String operation, boolean success) {
        if (success) {
            System.out.println("Операция '" + operation + "' выполнена успешно.");
        } else {
            System.out.println("Операция '" + operation + "' не выполнена.");
        }
    }
}
