package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.CardStatus;
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
class CardStatusServiceTest {

    @Mock
    private Dao<CardStatus, Long> cardStatusDao; // Mock the DAO used by the actual service

    @InjectMocks
    private CardStatusService cardStatusService; // The actual service under test

    private CardStatus sampleStatus1;
    private CardStatus sampleStatus2;

    @BeforeEach
    void setUp() {
        // Initialize sample CardStatus objects based on the actual model
        // org.bank.processing_center.model.CardStatus
        sampleStatus1 = new CardStatus(1L, "ACTIVE");
        sampleStatus2 = new CardStatus(2L, "BLOCKED");
    }

    @Test
    void createTable_shouldCallDaoCreateTable() {
        // No return value, just verify interaction
        doNothing().when(cardStatusDao).createTable();

        cardStatusService.createTable();

        verify(cardStatusDao).createTable();
    }

    @Test
    void dropTable_shouldCallDaoDropTable() {
        doNothing().when(cardStatusDao).dropTable();

        cardStatusService.dropTable();

        verify(cardStatusDao).dropTable();
    }

    @Test
    void clearTable_shouldCallDaoClearTable() {
        doNothing().when(cardStatusDao).clearTable();

        cardStatusService.clearTable();

        verify(cardStatusDao).clearTable();
    }

    @Test
    void save_shouldCallDaoSaveAndReturnSavedStatus() {
        CardStatus statusToSave = new CardStatus(null, "PENDING"); // ID is null before save
        CardStatus savedStatusMock = new CardStatus(3L, "PENDING"); // What DAO.save would return

        when(cardStatusDao.save(any(CardStatus.class))).thenReturn(savedStatusMock);

        CardStatus result = cardStatusService.save(statusToSave);

        assertNotNull(result);
        assertEquals(savedStatusMock.getId(), result.getId());
        assertEquals(savedStatusMock.getStatusName(), result.getStatusName());
        verify(cardStatusDao).save(statusToSave);
    }

    @Test
    void delete_shouldCallDaoDelete() {
        Long statusIdToDelete = 1L;
        doNothing().when(cardStatusDao).delete(statusIdToDelete);

        cardStatusService.delete(statusIdToDelete);

        verify(cardStatusDao).delete(statusIdToDelete);
    }

    @Test
    void findAll_shouldCallDaoFindAllAndReturnList() {
        List<CardStatus> expectedStatuses = Arrays.asList(sampleStatus1, sampleStatus2);
        when(cardStatusDao.findAll()).thenReturn(expectedStatuses);

        List<CardStatus> actualStatuses = cardStatusService.findAll();

        assertEquals(expectedStatuses, actualStatuses);
        assertEquals(2, actualStatuses.size());
        verify(cardStatusDao).findAll();
    }

    @Test
    void findAll_whenNoStatuses_shouldReturnEmptyList() {
        when(cardStatusDao.findAll()).thenReturn(Collections.emptyList());

        List<CardStatus> actualStatuses = cardStatusService.findAll();

        assertTrue(actualStatuses.isEmpty());
        verify(cardStatusDao).findAll();
    }

    @Test
    void findById_whenStatusExists_shouldCallDaoFindByIdAndReturnStatus() {
        Long statusId = 1L;
        when(cardStatusDao.findById(statusId)).thenReturn(sampleStatus1);

        CardStatus foundStatus = cardStatusService.findById(statusId);

        assertEquals(sampleStatus1, foundStatus);
        verify(cardStatusDao).findById(statusId);
    }

    @Test
    void findById_whenStatusNotExists_shouldCallDaoFindByIdAndReturnNull() {
        Long nonExistentId = 99L;
        when(cardStatusDao.findById(nonExistentId)).thenReturn(null);

        CardStatus foundStatus = cardStatusService.findById(nonExistentId);

        assertNull(foundStatus);
        verify(cardStatusDao).findById(nonExistentId);
    }

    @Test
    void update_shouldCallDaoUpdateAndReturnUpdatedStatus() {
        CardStatus statusToUpdate = new CardStatus(1L, "UPDATED_ACTIVE");
        // Assume DAO returns the updated object or the same object if it's mutated and saved
        when(cardStatusDao.update(any(CardStatus.class))).thenReturn(statusToUpdate);

        CardStatus updatedStatus = cardStatusService.update(statusToUpdate);

        assertEquals(statusToUpdate, updatedStatus);
        verify(cardStatusDao).update(statusToUpdate);
    }

    @Test
    void findByName_whenStatusExists_shouldReturnStatus() {
        List<CardStatus> allStatuses = Arrays.asList(sampleStatus1, sampleStatus2, new CardStatus(3L, "INACTIVE"));
        when(cardStatusDao.findAll()).thenReturn(allStatuses);

        CardStatus foundStatus = cardStatusService.findByName("BLOCKED");

        assertNotNull(foundStatus);
        assertEquals("BLOCKED", foundStatus.getStatusName());
        assertEquals(2L, foundStatus.getId()); // sampleStatus2
        verify(cardStatusDao).findAll(); // findByName calls findAll
    }

    @Test
    void findByName_whenStatusExistsCaseInsensitive_shouldReturnStatus() {
        List<CardStatus> allStatuses = Arrays.asList(sampleStatus1, sampleStatus2);
        when(cardStatusDao.findAll()).thenReturn(allStatuses);

        CardStatus foundStatus = cardStatusService.findByName("active"); // Lowercase

        assertNotNull(foundStatus);
        assertEquals("ACTIVE", foundStatus.getStatusName());
        assertEquals(1L, foundStatus.getId()); // sampleStatus1
        verify(cardStatusDao).findAll();
    }

    @Test
    void findByName_whenStatusNotExists_shouldReturnNull() {
        List<CardStatus> allStatuses = Arrays.asList(sampleStatus1, sampleStatus2);
        when(cardStatusDao.findAll()).thenReturn(allStatuses);

        CardStatus foundStatus = cardStatusService.findByName("UNKNOWN_STATUS");

        assertNull(foundStatus);
        verify(cardStatusDao).findAll();
    }

    @Test
    void findByName_whenDaoReturnsEmptyList_shouldReturnNull() {
        when(cardStatusDao.findAll()).thenReturn(Collections.emptyList());

        CardStatus foundStatus = cardStatusService.findByName("ACTIVE");

        assertNull(foundStatus);
        verify(cardStatusDao).findAll();
    }

    @Test
    void getStatusNameById_whenStatusExists_shouldReturnStatusName() {
        Long statusId = 1L;
        when(cardStatusDao.findById(statusId)).thenReturn(sampleStatus1);

        String statusName = cardStatusService.getStatusNameById(statusId);

        assertEquals("ACTIVE", statusName);
        verify(cardStatusDao).findById(statusId);
    }

    @Test
    void getStatusNameById_whenStatusNotExists_shouldReturnNull() {
        Long nonExistentId = 99L;
        when(cardStatusDao.findById(nonExistentId)).thenReturn(null);

        String statusName = cardStatusService.getStatusNameById(nonExistentId);

        assertNull(statusName);
        verify(cardStatusDao).findById(nonExistentId);
    }

}