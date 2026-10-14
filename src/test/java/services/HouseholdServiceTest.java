package services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HouseholdServiceTest {

    @Test
    public void koderErAltid6TegnLange() {
        for (int i = 0; i < 50; i++) {
            String code = HouseholdService.code();
            assertEquals(6, code.length());
        }
    }

    @Test
    public void koderBestårKunAfTilladteTegn() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < 50; i++) {
            String code = HouseholdService.code();
            for (char c : code.toCharArray()) {
                assertTrue(alphabet.indexOf(c) >= 0, "Uventet tegn: " + c);
            }
        }
    }
}