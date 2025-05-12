package org.bank.processing_center.mapper;

import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.Currency;
import org.bank.processing_center.model.IssuingBank;
import org.bank.processing_center.dao.jdbc.CurrencyJDBCDaoImpl; // Placeholder - replace with actual import
import org.bank.processing_center.dao.jdbc.IssuingBankJDBCDaoImpl; // Placeholder - replace with actual import

import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.Optional; // Import Optional

public class AccountMapper {

    // Placeholder DAOs - replace with actual dependency injection if using a framework
    private CurrencyJDBCDaoImpl currencyDao = new CurrencyJDBCDaoImpl();
    private IssuingBankJDBCDaoImpl issuingBankDao = new IssuingBankJDBCDaoImpl();

    public Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String accountNumber = resultSet.getString("account_number");
        BigDecimal balance = resultSet.getBigDecimal("balance");
        Long currencyId = resultSet.getLong("currency_id");
        Long issuingBankId = resultSet.getLong("issuing_bank_id");

        // Fetch Currency and IssuingBank objects using placeholder DAOs
        Optional<Currency> currencyOptional = currencyDao.findById(currencyId);
        Optional<IssuingBank> issuingBankOptional = issuingBankDao.findById(issuingBankId);

        // Handle cases where Currency or IssuingBank might not be found
        Currency currency = currencyOptional.orElse(null); // Or throw an exception
        IssuingBank issuingBank = issuingBankOptional.orElse(null); // Or throw an exception

        // Create and return Account object using the fetched objects
        return new Account(id, accountNumber, balance, currency, issuingBank);
    }
}