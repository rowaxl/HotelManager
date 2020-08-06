package test;

import helper.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import helper.FloorRate;

public class FloorRateTest {
    @Test
    @DisplayName("Test getFloorRate(): Success")
    void testGetFloorRate() {
        int lowFloor = 4;
        int highFloor = 10;

        assertDoesNotThrow(() -> FloorRate.getFloorRate(lowFloor));
        assertDoesNotThrow(() -> FloorRate.getFloorRate(highFloor));
        try {
            double rateLowFloor = FloorRate.getFloorRate(lowFloor);
            double rateHighFloor = FloorRate.getFloorRate(highFloor);
            assertEquals(1.4, rateLowFloor);
            assertEquals(2.5, rateHighFloor);
        } catch (InvalidInputException e) {
            System.err.println(e);
        }
    }

    @Test
    @DisplayName("Test getFloorRate(): Failed")
    void testGetFloorRateFailed() {
        int floorZero = 0;
        int negativeFloor = -10;

        assertThrows(InvalidInputException.class, () -> FloorRate.getFloorRate(floorZero));
        assertThrows(InvalidInputException.class, () -> FloorRate.getFloorRate(negativeFloor));

        try {
            double rateLowFloor = FloorRate.getFloorRate(floorZero);
        } catch (InvalidInputException e) {
            assertEquals("Floor cannot be zero or negative", e.getMessage());
        }

        try {
            double rateHighFloor = FloorRate.getFloorRate(negativeFloor);
        } catch (InvalidInputException e) {
            assertEquals("Floor cannot be zero or negative", e.getMessage());
        }
    }
}
