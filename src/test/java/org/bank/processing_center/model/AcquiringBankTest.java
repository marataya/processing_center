// src/test/java/org/bank/processing_center/model/AcquiringBankTest.java
package org.bank.processing_center.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AcquiringBankTest {

    @Test
    void testDefaultConstructor() {
        AcquiringBank bank = new AcquiringBank();
        assertNull(bank.getId(), "ID should be null for default constructor");
        assertNull(bank.getBic(), "BIC should be null for default constructor");
        assertNull(bank.getAbbreviatedName(), "AbbreviatedName should be null for default constructor");
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String bic = "BANKBICXXX";
        String abbreviatedName = "AB Bank";

        AcquiringBank bank = new AcquiringBank(id, bic, abbreviatedName);

        assertEquals(id, bank.getId(), "ID should match constructor argument");
        assertEquals(bic, bank.getBic(), "BIC should match constructor argument");
        assertEquals(abbreviatedName, bank.getAbbreviatedName(), "AbbreviatedName should match constructor argument");
    }

    @Test
    void testGettersAndSetters() {
        AcquiringBank bank = new AcquiringBank();

        Long id = 2L;
        String bic = "TESTBIC123";
        String abbreviatedName = "Test AB";

        bank.setId(id);
        bank.setBic(bic);
        bank.setAbbreviatedName(abbreviatedName);

        assertEquals(id, bank.getId(), "Getter for ID should return set value");
        assertEquals(bic, bank.getBic(), "Getter for BIC should return set value");
        assertEquals(abbreviatedName, bank.getAbbreviatedName(), "Getter for AbbreviatedName should return set value");
    }

    @Test
    void testEqualsAndHashCode() {
        // Assuming the bug in equals (casting to IssuingBank) is fixed to cast to AcquiringBank.
        // If not fixed, these tests might behave unexpectedly or fail.

        AcquiringBank bank1 = new AcquiringBank(1L, "BIC001", "Bank One");
        AcquiringBank bank2 = new AcquiringBank(1L, "BIC002", "Bank Two"); // Same ID, different other fields
        AcquiringBank bank3 = new AcquiringBank(2L, "BIC001", "Bank Three"); // Different ID

        // Reflexivity
        assertEquals(bank1, bank1, "Bank should be equal to itself.");
        assertEquals(bank1.hashCode(), bank1.hashCode(), "HashCode should be consistent for the same object.");

        // Symmetry
        // Based on the provided equals (ID-based), bank1 and bank2 should be equal.
        assertEquals(bank1, bank2, "bank1 and bank2 should be equal (same ID).");
        assertEquals(bank2, bank1, "Symmetry: bank2 and bank1 should be equal (same ID).");
        assertEquals(bank1.hashCode(), bank2.hashCode(), "HashCodes should be equal for equal objects (same ID).");

        // Transitivity
        AcquiringBank bank1Clone = new AcquiringBank(1L, "BIC_CLONE", "Bank One Clone"); // Same ID
        assertEquals(bank1, bank1Clone, "Bank and its clone (same ID) should be equal.");
        assertEquals(bank2, bank1Clone, "Bank2 and clone of Bank1 (same ID) should be equal.");

        // Inequality
        assertNotEquals(bank1, bank3, "Banks with different IDs should not be equal.");
        // Hashcodes might be the same even if objects are not equal, which is fine.
        // In this specific hashCode impl (getClass().hashCode()), they will be the same.
        assertEquals(bank1.hashCode(), bank3.hashCode(), "HashCodes can be the same for non-equal objects with this specific hashCode impl.");

        // Inequality with null and different type
        assertNotEquals(null, bank1, "Bank should not be equal to null.");
        assertNotEquals(bank1, new Object(), "Bank should not be equal to an object of a different type.");

        // Test with null ID
        // According to AcquiringBank.equals(): `return getId() != null && Objects.equals(getId(), that.getId());`
        // If `this.getId()` is null, it returns false.
        AcquiringBank bankNoId1 = new AcquiringBank(null, "NO_ID_BIC1", "No ID Bank 1");
        AcquiringBank bankNoId2 = new AcquiringBank(null, "NO_ID_BIC1", "No ID Bank 1"); // Same fields, but ID is null
        AcquiringBank bankNoId3 = new AcquiringBank(null, "NO_ID_BIC2", "No ID Bank 2");

        assertNotEquals(bankNoId1, bankNoId2, "Banks with null IDs are not equal by current equals implementation.");
        // Hashcodes will be the same (getClass().hashCode())
        assertEquals(bankNoId1.hashCode(), bankNoId2.hashCode());
        assertNotEquals(bankNoId1, bankNoId3, "Banks with null IDs are not equal.");

        AcquiringBank bankWithId = new AcquiringBank(5L, "ID_BIC", "ID Bank");
        assertNotEquals(bankNoId1, bankWithId, "Bank with null ID is not equal to bank with non-null ID.");
        assertNotEquals(bankWithId, bankNoId1, "Bank with non-null ID is not equal to bank with null ID.");

        // Test comparison with an IssuingBank instance (to highlight the bug if not fixed)
        // If the bug IS fixed, this should result in 'false' due to different effective classes.
        // If the bug IS NOT fixed, this might lead to ClassCastException if IssuingBank doesn't have getId(),
        // or incorrect equality if it does.
        IssuingBank anIssuingBank = new IssuingBank(); // Assuming IssuingBank exists
        anIssuingBank.setId(1L); // Give it the same ID as bank1 for a more direct test of the bug
        assertNotEquals(bank1, anIssuingBank, "AcquiringBank should not be equal to an IssuingBank instance.");
    }

    @Test
    void testToString() {
        AcquiringBank bank = new AcquiringBank(10L, "MYBICBANK", "My AB");
        String bankString = bank.toString();

        // Expected: "AcquiringBank{id=10, bic='MYBICBANK', abbreviatedName=My AB}"
        // Note: abbreviatedName might be 'My AB' if it's not null, or null if it is.

        assertNotNull(bankString, "toString() should not return null.");
        assertTrue(bankString.startsWith("AcquiringBank{"), "toString() should start with class name.");
        assertTrue(bankString.contains("id=10"), "toString() should contain the id.");
        assertTrue(bankString.contains("bic='MYBICBANK'"), "toString() should contain the bic.");
        assertTrue(bankString.contains("abbreviatedName=My AB"), "toString() should contain the abbreviatedName.");
        assertTrue(bankString.endsWith("}"), "toString() should end with '}'.");

        AcquiringBank bankNullAbbr = new AcquiringBank(11L, "BICNULL", null);
        String bankNullAbbrString = bankNullAbbr.toString();
        // Expected: "AcquiringBank{id=11, bic='BICNULL', abbreviatedName=null}"
        assertTrue(bankNullAbbrString.contains("abbreviatedName=null"), "toString() should correctly represent null abbreviatedName.");

        AcquiringBank bankEmptyAbbr = new AcquiringBank(12L, "BICEMPTY", "");
        String bankEmptyAbbrString = bankEmptyAbbr.toString();
        // Expected: "AcquiringBank{id=12, bic='BICEMPTY', abbreviatedName=}"
        // The actual output for empty string depends on how `abbreviatedName` is handled in `toString()`.
        // The current `toString()` will output `abbreviatedName=` for an empty string.
        // If it should be `abbreviatedName=''` then the `toString()` method or this test needs adjustment.
        // For now, assuming it just concatenates the value.
        assertTrue(bankEmptyAbbrString.contains("abbreviatedName="), "toString() should handle empty abbreviatedName.");
        // More specific for empty string if it's not quoted:
        // assertTrue(bankEmptyAbbrString.contains("abbreviatedName=,"), "toString() should handle empty abbreviatedName.");
        // Or if it's quoted:
        // assertTrue(bankEmptyAbbrString.contains("abbreviatedName=''"), "toString() should handle empty abbreviatedName if quoted.");
        // Given the BIC is quoted, it's likely abbreviatedName should also be if it's a string.
        // However, the provided toString does not quote abbreviatedName.
        // So, "abbreviatedName=" + null results in "abbreviatedName=null"
        // and "abbreviatedName=" + "" results in "abbreviatedName="
        // Let's stick to what the current toString produces:
        assertTrue(bankEmptyAbbrString.contains("abbreviatedName="), "toString() should contain abbreviatedName prefix for empty string.");
        assertFalse(bankEmptyAbbrString.contains("abbreviatedName=null") && "".equals(bankEmptyAbbr.getAbbreviatedName()), "toString() should not show 'null' for empty string.");

    }
}