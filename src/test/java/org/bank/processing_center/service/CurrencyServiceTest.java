package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private Dao<Currency, Long> currencyDao;

    @InjectMocks
    private CurrencyService currencyService;

    private Currency sampleCurrency1;
    private Currency sampleCurrency2;

    @BeforeEach
    void setUp() {
        sampleCurrency1 = new Currency(1L, "840", "USD", "US Dollar");
        sampleCurrency2 = new Currency(2L, "978", "EUR", "Euro");
    }

    @Test
    void createTable_shouldCallDaoCreateTable() {
        doNothing().when(currencyDao).createTable();

        currencyService.createTable();

        verify(currencyDao).createTable();
    }

    @Test
    void dropTable_shouldCallDaoDropTable() {
        doNothing().when(currencyDao).dropTable();

        currencyService.dropTable();

        verify(currencyDao).dropTable();
    }

    @Test
    void clearTable_shouldCallDaoClearTable() {
        doNothing().when(currencyDao).clearTable();

        currencyService.clearTable();

        verify(currencyDao).clearTable();
    }

    @Test
    void save_shouldCallDaoSaveAndReturnSavedCurrency() {
        Currency currencyToSave = new Currency(3L, "777", "GBP", "British Pound");
        Currency savedCurrencyMock = new Currency(3L, "777", "GBP", "British Pound");

        when(currencyDao.save(any(Currency.class))).thenReturn(savedCurrencyMock);

        Currency result = currencyService.save(currencyToSave);

        assertNotNull(result);
        assertEquals(savedCurrencyMock.getId(), result.getId());
        assertEquals(savedCurrencyMock.getCurrencyName(), result.getCurrencyName());
        assertEquals(savedCurrencyMock.getCurrencyName(), result.getCurrencyName());
        verify(currencyDao).save(currencyToSave);
    }

    @Test
    void delete_shouldCallDaoDelete() {
        Long currencyIdToDelete = 1L;
        doNothing().when(currencyDao).delete(currencyIdToDelete);

        currencyService.delete(currencyIdToDelete);

        verify(currencyDao).delete(currencyIdToDelete);
    }

    @Test
    void findAll_shouldCallDaoFindAllAndReturnList() {
        List<Currency> expectedCurrencies = Arrays.asList(sampleCurrency1, sampleCurrency2);
        when(currencyDao.findAll()).thenReturn(expectedCurrencies);

        List<Currency> actualCurrencies = currencyService.findAll();

        assertEquals(expectedCurrencies, actualCurrencies);
        assertEquals(2, actualCurrencies.size());
        verify(currencyDao).findAll();
    }

    @Test
    void findAll_whenNoCurrencies_shouldReturnEmptyList() {
        when(currencyDao.findAll()).thenReturn(Collections.emptyList());

        List<Currency> actualCurrencies = currencyService.findAll();

        assertTrue(actualCurrencies.isEmpty());
        verify(currencyDao).findAll();
    }

    @Test
    void findById_whenCurrencyExists_shouldCallDaoFindByIdAndReturnCurrency() {
        Long currencyId = 1L;
        when(currencyDao.findById(currencyId)).thenReturn(sampleCurrency1);

        Currency foundCurrency = currencyService.findById(currencyId);

        assertEquals(sampleCurrency1, foundCurrency);
        verify(currencyDao).findById(currencyId);
    }

    @Test
    void findById_whenCurrencyNotExists_shouldCallDaoFindByIdAndReturnNull() {
        Long nonExistentId = 99L;
        when(currencyDao.findById(nonExistentId)).thenReturn(null);

        Currency foundCurrency = currencyService.findById(nonExistentId);

        assertNull(foundCurrency);
        verify(currencyDao).findById(nonExistentId);
    }

    @Test
    void update_shouldCallDaoUpdateAndReturnUpdatedCurrency() {
        Currency currencyToUpdate = new Currency(1L, "840", "USD", "Доллар США");
        when(currencyDao.update(any(Currency.class))).thenReturn(currencyToUpdate);

        Currency updatedCurrency = currencyService.update(currencyToUpdate);

        assertEquals(currencyToUpdate, updatedCurrency);
        verify(currencyDao).update(currencyToUpdate);
    }
}
