package test;

import helper.InvalidInputException;
import helper.RoomRate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoomRateTest {
    @Test
    @DisplayName("Test RoomRate class")
    void testRoomRate() {
        assertEquals(120, RoomRate.SINGLE.getPrice());
        assertEquals(170, RoomRate.TWIN.getPrice());
    }
}
