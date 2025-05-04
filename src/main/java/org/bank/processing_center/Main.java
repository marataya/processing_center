package org.bank.processing_center;

import org.bank.processing_center.dao.base.Dao;
import org.bank.processing_center.dao.factory.DaoFactory;
import org.bank.processing_center.dao.jdbc.JDBCConfig;
import org.bank.processing_center.model.*;
import org.bank.processing_center.util.DatabaseInitializer;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Инициализация базы данных
            DatabaseInitializer initializer = new DatabaseInitializer();
            initializer.initializeDatabase();

            // Получение DAO через фабрику
            DaoFactory daoFactory = DaoFactory.getInstance();
            Dao<CardStatus, Long> cardStatusDao = daoFactory.getCardStatusDao();
            Dao<PaymentSystem, Long> paymentSystemDao = daoFactory.getPaymentSystemDao();
            Dao<Currency, Long> currencyDao = daoFactory.getCurrencyDao();
            Dao<IssuingBank, Long> issuingBankDao = daoFactory.getIssuingBankDao();
            Dao<Account, Long> accountDao = daoFactory.getAccountDao();
            Dao<Card, Long> cardDao = daoFactory.getCardDao();

            // Добавление статусов карт
            CardStatus activeStatus = new CardStatus(1L, "Active");
            CardStatus blockedStatus = new CardStatus(2L, "Blocked");
            CardStatus expiredStatus = new CardStatus(3L, "Expired");

            cardStatusDao.save(activeStatus);
            cardStatusDao.save(blockedStatus);
            cardStatusDao.save(expiredStatus);

            // Добавление платежных систем
            PaymentSystem visa = new PaymentSystem(1L, "VISA");
            PaymentSystem mastercard = new PaymentSystem(2L, "MasterCard");
            PaymentSystem mir = new PaymentSystem(3L, "MIR");

            paymentSystemDao.save(visa);
            paymentSystemDao.save(mastercard);
            paymentSystemDao.save(mir);

            // Добавление валют
            Currency rub = new Currency(1L, "643", "RUB", "Российский рубль");
            Currency usd = new Currency(2L, "840", "USD", "Доллар США");
            Currency eur = new Currency(3L, "978", "EUR", "Евро");

            currencyDao.save(rub);
            currencyDao.save(usd);
            currencyDao.save(eur);

            // Добавление банков-эмитентов
            IssuingBank sberbank = new IssuingBank(1L, "044525225", "45655", "Сбербанк");
            IssuingBank vtb = new IssuingBank(2L, "044525187", "45678", "ВТБ");

            issuingBankDao.save(sberbank);
            issuingBankDao.save(vtb);

            // Добавление счетов
            Account account1 = new Account(1L, "40817810099910004312", 10000.0, 1L, 1L);
            Account account2 = new Account(2L, "40817810099910004313", 5000.0, 1L, 2L);

            accountDao.save(account1);
            accountDao.save(account2);

            // Добавление карт
            Card card1 = new Card(
                    1L,
                    "4276550012345678",
                    LocalDate.of(2025, 12, 31),
                    "IVAN IVANOV",
                    1L,
                    1L,
                    1L,
                    Timestamp.valueOf(LocalDateTime.now()),
                    null
            );

            Card card2 = new Card(
                    2L,
                    "5469550087654321",
                    LocalDate.of(2024, 10, 31),
                    "PETR PETROV",
                    1L,
                    2L,
                    2L,
                    Timestamp.valueOf(LocalDateTime.now()),
                    null
            );

            cardDao.save(card1);
            cardDao.save(card2);

            // Вывод всех данных
            printAllData(daoFactory);

            // Обновление карты
            System.out.println("\nОбновление карты с ID 1:");
            cardDao.findById(1L).ifPresent(card -> {
                card.setHolderName("IVAN I IVANOV");
                cardDao.update(card);
                System.out.println("Карта после обновления:");
                cardDao.findById(1L).ifPresent(System.out::println);
            });

        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Закрытие соединения с БД
            JDBCConfig.closeConnection();
        }
    }

    private static void printAllData(DaoFactory daoFactory) {
        // Вывод всех статусов карт
        System.out.println("Все статусы карт:");
        List<CardStatus> allCardStatuses = daoFactory.getCardStatusDao().findAll();
        for (CardStatus status : allCardStatuses) {
            System.out.println(status);
        }

        // Вывод всех платежных систем
        System.out.println("\nВсе платежные системы:");
        List<PaymentSystem> allPaymentSystems = daoFactory.getPaymentSystemDao().findAll();
        for (PaymentSystem system : allPaymentSystems) {
            System.out.println(system);
        }

        // Вывод всех валют
        System.out.println("\nВсе валюты:");
        List<Currency> allCurrencies = daoFactory.getCurrencyDao().findAll();
        for (Currency currency : allCurrencies) {
            System.out.println(currency);
        }

        // Вывод всех банков-эмитентов
        System.out.println("\nВсе банки-эмитенты:");
        List<IssuingBank> allIssuingBanks = daoFactory.getIssuingBankDao().findAll();
        for (IssuingBank bank : allIssuingBanks) {
            System.out.println(bank);
        }

        // Вывод всех счетов
        System.out.println("\nВсе счета:");
        List<Account> allAccounts = daoFactory.getAccountDao().findAll();
        for (Account account : allAccounts) {
            System.out.println(account);
        }

        // Вывод всех карт
        System.out.println("\nВсе карты:");
        List<Card> allCards = daoFactory.getCardDao().findAll();
        for (Card card : allCards) {
            System.out.println(card);
        }
    }
}
