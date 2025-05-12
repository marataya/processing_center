package org.bank.processing_center.mapper;

import org.bank.processing_center.model.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper {

    public Currency mapResultSetToCurrency(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String currencyDigitalCodeAccount = resultSet.getString("currency_digital_code");
        String currencyDigitalCode = resultSet.getString("currency_digital_code_account");
        String currencyLetterCode = resultSet.getString("currency_letter_code");
        String currencyName = resultSet.getString("currency_name");

        // Create and return Currency object with all fields
        return new Currency(id, currencyDigitalCodeAccount, currencyLetterCode, currencyDigitalCode, currencyName);
    };

}