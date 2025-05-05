package org.bank.processing_center.service;

import org.bank.processing_center.dao.base.Dao;
import org.bank.processing_center.model.Account;

import java.util.List;
import java.util.Optional;

public class AccountService implements Service<Account, Long> {

    private final Dao<Account, Long> accountDao;

    public AccountService(Dao<Account, Long> accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void createTable() {
        accountDao.createTable();
    }

    @Override
    public void dropTable() {
        accountDao.dropTable();
    }

    @Override
    public void clearTable() {
        accountDao.clearTable();
    }

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public void delete(Long id) {
        accountDao.delete(id);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    /**
     * Updates the balance of an account
     * @param accountId Account ID
     * @param amount Amount to add (positive) or subtract (negative)
     * @return true if the operation was successful, false otherwise
     */
    public boolean updateBalance(Long accountId, double amount) {
        Optional<Account> accountOpt = accountDao.findById(accountId);

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            double newBalance = account.getBalance() + amount;

            // Check if the balance would become negative
            if (newBalance < 0) {
                System.out.println("Недостаточно средств на счете: " + account.getAccountNumber());
                return false;
            }

            account.setBalance(newBalance);
            accountDao.update(account);
            System.out.println("Баланс счета " + account.getAccountNumber() + " обновлен. Новый баланс: " + newBalance);
            return true;
        } else {
            System.out.println("Счет с ID " + accountId + " не найден");
            return false;
        }
    }

    /**
     * Transfers money between accounts
     * @param fromAccountId Source account ID
     * @param toAccountId Destination account ID
     * @param amount Amount to transfer
     * @return true if the transfer was successful, false otherwise
     */
    public boolean transferMoney(Long fromAccountId, Long toAccountId, double amount) {
        if (amount <= 0) {
            System.out.println("Сумма перевода должна быть положительной");
            return false;
        }

        Optional<Account> fromAccountOpt = accountDao.findById(fromAccountId);
        Optional<Account> toAccountOpt = accountDao.findById(toAccountId);

        if (fromAccountOpt.isPresent() && toAccountOpt.isPresent()) {
            Account fromAccount = fromAccountOpt.get();
            Account toAccount = toAccountOpt.get();

            // Check if the source account has enough funds
            if (fromAccount.getBalance() < amount) {
                System.out.println("Недостаточно средств на счете: " + fromAccount.getAccountNumber());
                return false;
            }

            // Update balances
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            // Save changes
            accountDao.update(fromAccount);
            accountDao.update(toAccount);

            System.out.println("Перевод выполнен успешно. Со счета " + fromAccount.getAccountNumber() +
                    " на счет " + toAccount.getAccountNumber() + " переведено " + amount);
            return true;
        } else {
            System.out.println("Один или оба счета не найдены");
            return false;
        }
    }
}
