package org.bank.processing_center.mapper;

import org.bank.processing_center.dao.jdbc.AcquiringBankJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.TerminalJDBCDaoImpl;
import org.bank.processing_center.model.SalesPoint;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesPointMapper {

    // Placeholder DAOs (replace with actual injected DAOs in a real application)
    private AcquiringBankJDBCDaoImpl acquiringBankDao = new AcquiringBankJDBCDaoImpl();
    private TerminalJDBCDaoImpl terminalDao = new TerminalJDBCDaoImpl(); // This field is declared but not used in this method

    public SalesPoint mapResultSetToSalesPoint(ResultSet resultSet) throws SQLException {
        SalesPoint salesPoint = new SalesPoint();
        salesPoint.setId(resultSet.getLong("id"));
        salesPoint.setPosName(resultSet.getString("pos_name"));
        salesPoint.setPosAddress(resultSet.getString("pos_address"));
        salesPoint.setPosInn(resultSet.getString("pos_inn"));

        Long acquiringBankId = resultSet.getLong("acquiring_bank_id");
        if (!resultSet.wasNull()) {
            // Fetch AcquiringBank using its DAO (assuming AcquiringBankJDBCDaoImpl has findById)
            acquiringBankDao.findById(acquiringBankId).ifPresent(salesPoint::setAcquiringBank);
        }

        return salesPoint;
    }
}