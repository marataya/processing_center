// /home/nemo/dev/ayaibergenov_jan1_processingcenter/src/test/java/org/bank/processing_center/service/CardServiceTest.java
package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Card;
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

@ExtendWith(MockitoExtension.class) // Integrates Mockito with JUnit 5
class CardServiceTest {

    @Mock // Create a mock instance of the Dao
    private Dao<Card, Long> cardDao;

    @InjectMocks // Inject the mock Dao into the CardService instance
    private CardService cardService;

    // We'll create a mock Card for tests involving validation,
    // and potentially a real Card for basic CRUD tests if needed,
    // but mocking the Card is essential for testing the service's validation logic flow.
    private Card mockCard;
    private final Long TEST_CARD_ID = 1L;

    @BeforeEach
    void setUp() {
        // Initialize a mock Card before each test
        mockCard = mock(Card.class); // Create a mock of the Card class
    }

    @Test
    void testConstructor() {
        // Verify that the Dao is correctly injected
        assertNotNull(cardService);
        // The other tests will implicitly verify this by checking if dao methods are called.
    }

    @Test
    void testCreateTable() {
        // Call the service method
        cardService.createTable();

        // Verify that the corresponding method on the mocked DAO was called exactly once
        verify(cardDao, times(1)).createTable();
    }

    @Test
    void testDropTable() {
        cardService.dropTable();
        verify(cardDao, times(1)).dropTable();
    }

    @Test
    void testClearTable() {
        cardService.clearTable();
        verify(cardDao, times(1)).clearTable();
    }

    // --- Tests for save method ---

    @Test
    void testSave_validCard() {
        // Arrange: Mock the card's validateCard method to return true
        when(mockCard.validateCard(mockCard)).thenReturn(true);

        // Act: Call the service save method
        cardService.save(mockCard);

        // Assert: Verify that the DAO's save method was called with the valid card
        verify(cardDao, times(1)).save(mockCard);
        // Verify that validateCard was called exactly once
        verify(mockCard, times(1)).validateCard(mockCard);
    }

    @Test
    void testSave_invalidCard() {
        // Arrange: Mock the card's validateCard method to return false
        when(mockCard.validateCard(mockCard)).thenReturn(false);

        // Act: Call the service save method
        cardService.save(mockCard);

        // Assert: Verify that the DAO's save method was NOT called
        verify(cardDao, never()).save(any(Card.class));
        // Verify that validateCard was called exactly once
        verify(mockCard, times(1)).validateCard(mockCard);
        // Note: We don't test System.err.println directly in unit tests.
    }

    @Test
    void testSave_nullCard() {
        // Act: Call the service save method with null
        cardService.save(null);

        // Assert: Verify that the DAO's save method was NOT called
        verify(cardDao, never()).save(any(Card.class));
        // Verify that validateCard was NOT called on the null object (would cause NPE)
        // This is implicitly tested by the fact that no exception is thrown and save is not called.
        // We can also explicitly verify no interaction with the mockCard if we were testing a non-null invalid scenario.
    }


    @Test
    void testDelete() {
        cardService.delete(TEST_CARD_ID);
        verify(cardDao, times(1)).delete(TEST_CARD_ID);
    }

    @Test
    void testFindAll() {
        // Arrange: Prepare the mock response
        Card anotherMockCard = mock(Card.class);
        List<Card> expectedCards = Arrays.asList(mockCard, anotherMockCard);

        // When findAll is called on the DAO, return the list of mocks
        when(cardDao.findAll()).thenReturn(expectedCards);

        // Act: Call the service method
        List<Card> actualCards = cardService.findAll();

        // Assert: Verify the DAO method was called and the correct list is returned
        verify(cardDao, times(1)).findAll();
        assertEquals(expectedCards, actualCards); // This assertion calls equals() on the mocks, which calls getId()
        assertEquals(2, actualCards.size());
    }

    @Test
    void testFindById_found() {
        // Arrange: Prepare the mock response for finding the card
        when(cardDao.findById(TEST_CARD_ID)).thenReturn(Optional.of(mockCard));

        // Act: Call the service method
        Optional<Card> foundCardOpt = cardService.findById(TEST_CARD_ID);

        // Assert: Verify the DAO method was called and the card is present
        verify(cardDao, times(1)).findById(TEST_CARD_ID);
        assertTrue(foundCardOpt.isPresent());
        assertEquals(mockCard, foundCardOpt.get()); // This assertion calls equals() on the mocks, which calls getId()
    }

    @Test
    void testFindById_notFound() {
        // Arrange: Prepare the mock response for not finding the card
        Long nonExistentId = 99L;
        when(cardDao.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act: Call the service method
        Optional<Card> foundCardOpt = cardService.findById(nonExistentId);

        // Assert: Verify the DAO method was called and the card is not present
        verify(cardDao, times(1)).findById(nonExistentId);
        assertFalse(foundCardOpt.isPresent());
    }

    // --- Tests for update method ---

    @Test
    void testUpdate_validCard() {
        // Arrange: Mock the card's validateCard method to return true
        when(mockCard.validateCard(mockCard)).thenReturn(true);

        // Act: Call the service update method
        cardService.update(mockCard);

        // Assert: Verify that the DAO's update method was called with the valid card
        verify(cardDao, times(1)).update(mockCard);
        // Verify that validateCard was called exactly once
        verify(mockCard, times(1)).validateCard(mockCard);
    }

    @Test
    void testUpdate_invalidCard() {
        // Arrange: Mock the card's validateCard method to return false
        when(mockCard.validateCard(mockCard)).thenReturn(false);

        // Act: Call the service update method
        cardService.update(mockCard);

        // Assert: Verify that the DAO's update method was NOT called
        verify(cardDao, never()).update(any(Card.class));
        // Verify that validateCard was called exactly once
        verify(mockCard, times(1)).validateCard(mockCard);
        // Note: We don't test System.err.println directly in unit tests.
    }

    @Test
    void testUpdate_nullCard() {
        // Act: Call the service update method with null
        cardService.update(null);

        // Assert: Verify that the DAO's update method was NOT called
        verify(cardDao, never()).update(any(Card.class));
        // Verify that validateCard was NOT called on the null object
    }
}