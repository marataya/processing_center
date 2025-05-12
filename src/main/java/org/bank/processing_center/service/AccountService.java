package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Account;
import org.bank.processing_center.dao.factory.DaoFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AccountService implements Service<Account, Long> {

    private final Dao<Account, Long> accountDao;

    public AccountService(String daoType) {
        this.accountDao = DaoFactory.getInstance(daoType).getAccountDao();
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
     * 
     * @param accountId Account ID
     * @param amount    Amount to add (positive) or subtract (negative)
     * @return true if the operation was successful, false otherwise
     */
    public boolean updateBalance(Long accountId, double amount) {
        Optional<Account> accountOpt = accountDao.findById(accountId);

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);
            BigDecimal newBalanceBigDecimal = account.getBalance().add(amountBigDecimal);

            // Check if the balance would become negative
            if (newBalanceBigDecimal.compareTo(BigDecimal.ZERO) < 0) { // Using compareTo for BigDecimal comparison
                System.out.println("Недостаточно средств на счете: " + account.getAccountNumber());
                return false;
            }

            account.setBalance(newBalanceBigDecimal);
            accountDao.update(account);
            System.out.println(
                    "Баланс счета " + account.getAccountNumber() + " обновлен. Новый баланс: " + newBalanceBigDecimal);
            return true;
        } else {
            System.out.println("Счет с ID " + accountId + " не найден");
            return false;
        }
    }

    /**
     * Transfers money between accounts
     * 
     * @param fromAccountId Source account ID
     * @param toAccountId   Destination account ID
     * @param amount        Amount to transfer
     * @return true if the transfer was successful, false otherwise
     */
    public boolean transferMoney(Long fromAccountId, Long toAccountId, double amount) {
        BigDecimal transferAmount = BigDecimal.valueOf(amount);

        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Сумма перевода должна быть положительной");
            return false;
        }

        Optional<Account> fromAccountOpt = accountDao.findById(fromAccountId);
        Optional<Account> toAccountOpt = accountDao.findById(toAccountId);

        if (!fromAccountOpt.isPresent() || !toAccountOpt.isPresent()) {
            System.out.println("Один или оба счета не найдены");
            return false;
        }

        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        // Check if the source account has enough funds
        if (fromAccount.getBalance().compareTo(transferAmount) < 0) {
            System.out.println("Недостаточно средств на счете: " + fromAccount.getAccountNumber());
            return false;
        }

        // Update balances
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
        toAccount.setBalance(toAccount.getBalance().add(transferAmount));

            // Save changes
            accountDao.update(fromAccount);
            accountDao.update(toAccount);

            System.out.println("Перевод выполнен успешно. Со счета " + fromAccount.getAccountNumber() +
                    " на счет " + toAccount.getAccountNumber() + " переведено " + amount);
            return true;
    }
}
