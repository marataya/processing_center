package org.bank.processing_center.view;

// import org.bank.processing_center.model.Model;

import java.util.List;

/**
 * Handles console output for the application
 */
public class ConsoleView {

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String errorMessage) {
        System.err.println("Error: " + errorMessage);
    }

    public <T> void showList(List<T> items, String title) {
        System.out.println("\n=== " + title + " ===");
        if (items == null || items.isEmpty()) {
            System.out.println("Список пуст.");
        } else {
            for (T item : items) {
                System.out.println(item);
            }
        }
        System.out.println("========================\n");
    }

    public void showOperationResult(String operation, boolean success) {
        if (success) {
            System.out.println("Операция '" + operation + "' выполнена успешно.");
        } else {
            System.out.println("Операция '" + operation + "' не выполнена.");
        }
    }
}
