package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.helper.exception.DaoException;
import org.bank.processing_center.model.Account;

import java.math.BigDecimal;
import java.util.List;

public class AccountService implements Service<Account, Long> {

    private final Dao<Account, Long> accountDao;

    public AccountService(Dao<Account, Long> dao) {
        this.accountDao = dao;
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
    public Account save(Account account) {
        return accountDao.save(account);
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
    public Account findById(Long id) {
        return accountDao.findById(id).orElseThrow(() -> new DaoException("Account with ID " + id + " not found"));
    }

    @Override
    public Account update(Account account) {
        return accountDao.update(account);
    }

    /**
     * Updates the balance of an account
     *
     * @param accountId Account ID
     * @param amount    Amount to add (positive) or subtract (negative)
     * @return true if the operation was successful, false otherwise
     */
    public boolean updateBalance(Long accountId, double amount) {
        try {
            Account account = accountDao.findById(accountId).orElseThrow(() -> new DaoException("Account not found"));
            BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);
            BigDecimal newBalanceBigDecimal = account.getBalance().add(amountBigDecimal);

            // Check if the balance would become negative
            if (newBalanceBigDecimal.compareTo(BigDecimal.ZERO) < 0) { // Using compareTo for BigDecimal comparison
                System.out.println("Недостаточно средств на счете: " + account.getAccountNumber());
                return false;
            }

            account.setBalance(newBalanceBigDecimal);
            accountDao.update(account);
            System.out.println("Баланс счета " + account.getAccountNumber() + " обновлен. Новый баланс: " + newBalanceBigDecimal);
            return true;
        } catch (Exception e) {
            System.out.println("Счет с ID " + accountId + " не найден");
            return false;
        }
    }
}
