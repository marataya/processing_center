package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.AcquiringBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcquiringBankServiceTest {

    @Mock
    private Dao<AcquiringBank, Long> acquiringBankDao;

    @InjectMocks
    private AcquiringBankService acquiringBankService;

    private AcquiringBank testAcquiringBank;
    private final Long TEST_BANK_ID = 1L;
    private final String TEST_BANK_BIC = "TESTBIC123";
    private final String TEST_BANK_NAME = "Test Bank Name";

    @BeforeEach
    void setUp() {
        testAcquiringBank = new AcquiringBank();
        testAcquiringBank.setId(TEST_BANK_ID);
        testAcquiringBank.setBic(TEST_BANK_BIC);
        testAcquiringBank.setAbbreviatedName(TEST_BANK_NAME);
    }

    @Test
    void testConstructor() {
        assertNotNull(acquiringBankService);
    }

    @Test
    void testCreateTable() {
        acquiringBankService.createTable();
        verify(acquiringBankDao, times(1)).createTable();
    }

    @Test
    void testDropTable() {
        acquiringBankService.dropTable();
        verify(acquiringBankDao, times(1)).dropTable();
    }

    @Test
    void testClearTable() {
        acquiringBankService.clearTable();
        verify(acquiringBankDao, times(1)).clearTable();
    }

    @Test
    void testSave() {
        AcquiringBank bankToSave = new AcquiringBank();
        bankToSave.setBic("NEWSBIC");
        bankToSave.setAbbreviatedName("New Bank");

        acquiringBankService.save(bankToSave);
        verify(acquiringBankDao, times(1)).save(bankToSave);
    }

    @Test
    void testDelete() {
        acquiringBankService.delete(TEST_BANK_ID);
        verify(acquiringBankDao, times(1)).delete(TEST_BANK_ID);
    }

    @Test
    void testFindAll() {
        AcquiringBank anotherBank = new AcquiringBank();
        anotherBank.setId(2L);
        anotherBank.setBic("OTHERBIC");
        List<AcquiringBank> expectedBanks = Arrays.asList(testAcquiringBank, anotherBank);
        when(acquiringBankDao.findAll()).thenReturn(expectedBanks);

        List<AcquiringBank> actualBanks = acquiringBankService.findAll();

        verify(acquiringBankDao, times(1)).findAll();
        assertEquals(expectedBanks, actualBanks);
        assertEquals(2, actualBanks.size());
    }

    @Test
    void testFindById_found() {
        when(acquiringBankDao.findById(TEST_BANK_ID)).thenReturn(Optional.of(testAcquiringBank));

        Optional<AcquiringBank> foundBankOpt = acquiringBankService.findById(TEST_BANK_ID);

        verify(acquiringBankDao, times(1)).findById(TEST_BANK_ID);
        assertTrue(foundBankOpt.isPresent());
        assertEquals(testAcquiringBank, foundBankOpt.get());
        assertEquals(TEST_BANK_BIC, foundBankOpt.get().getBic());
    }

    @Test
    void testFindById_notFound() {
        Long nonExistentId = 99L;
        when(acquiringBankDao.findById(nonExistentId)).thenReturn(Optional.empty());

        Optional<AcquiringBank> foundBankOpt = acquiringBankService.findById(nonExistentId);

        verify(acquiringBankDao, times(1)).findById(nonExistentId);
        assertFalse(foundBankOpt.isPresent());
    }

    @Test
    void testUpdate() {
        AcquiringBank bankToUpdate = new AcquiringBank();
        bankToUpdate.setId(TEST_BANK_ID);
        bankToUpdate.setBic("UPDATEDBIC");
        bankToUpdate.setAbbreviatedName("Updated Bank Name");

        acquiringBankService.update(bankToUpdate);
        verify(acquiringBankDao, times(1)).update(bankToUpdate);
    }
}