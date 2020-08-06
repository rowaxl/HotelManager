package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import helper.KeyValue;
import helper.Utils;
import models.Reservation;
import models.Room;

import java.util.HashMap;

public class UtilsTest {
    @Test
    @DisplayName("Test calculateRoomCost(room): Success")
    void testCalculateRoomCost() {
        Room singleRoomLowFloor = new Room(201, 2, "S");
        Room twinRoomLowFloor = new Room(502, 5, "T");

        // calculation: base rate(S: 120 / T: 170) * floor rate(~5th: 1 + floor / 10, over 5th: 1.5 + floor / 10)
        assertEquals(120 * (1.0 + 0.2), Utils.calculateRoomCost(singleRoomLowFloor));
        assertEquals(170 * (1.0 + 0.5), Utils.calculateRoomCost(twinRoomLowFloor));

        Room singleRoomHighFloor = new Room(1201, 12, "S");
        Room twinRoomHighFloor = new Room(602, 6, "T");

        assertEquals(120 * (1.5 + 1.2), Utils.calculateRoomCost(singleRoomHighFloor));
        assertEquals(170 * (1.5 + 0.6), Utils.calculateRoomCost(twinRoomHighFloor));
    }

    @Test
    @DisplayName("Test calculateRoomCost(room): Failed")
    void testCalculateRoomCostFailed() {
        Room roomFloorZero = new Room(201, 0, "S");
        Room roomNegativeFloor = new Room(201, -10, "S");

        // When exception occurred, function return 0
        assertEquals(0, Utils.calculateRoomCost(roomFloorZero));
        assertEquals(0, Utils.calculateRoomCost(roomNegativeFloor));
    }

    @Test
    @DisplayName("Test findCheapestPlanAndRoom(availability): Success")
    void testFindCheapestPlan() {
        HashMap<Room, Boolean> availability = new HashMap<>();

        availability.put(new Room(501, 5, "T"), true);
        availability.put(new Room(301, 3, "S"), true);
        availability.put(new Room(202, 2, "S"), true);
        availability.put(new Room(401, 4, "T"), true);
        availability.put(new Room(203, 2, "T"), true);

        KeyValue<Room, Reservation> cheapestPlan = Utils.findCheapestPlanAndRoom(availability);
        assertNotNull(cheapestPlan);

        Room selectedRoom = cheapestPlan.getKey();
        Reservation plan = cheapestPlan.getValue();

        assertEquals(202, selectedRoom.getRoomNo());
        assertEquals(2, selectedRoom.getFloor());
        assertEquals("S", selectedRoom.getRoomType());

        assertEquals(Utils.calculateRoomCost(new Room(202, 2, "S")), plan.getPrice());

        HashMap<Room, Boolean> availabilityWithFulledRoom = new HashMap<>();

        availabilityWithFulledRoom.put(new Room(501, 5, "T"), true);
        availabilityWithFulledRoom.put(new Room(301, 3, "S"), false);
        availabilityWithFulledRoom.put(new Room(202, 2, "S"), false);
        availabilityWithFulledRoom.put(new Room(401, 4, "T"), true);
        availabilityWithFulledRoom.put(new Room(203, 2, "T"), false);

        KeyValue<Room, Reservation> cheapestPlanFromFulledRoom = Utils.findCheapestPlanAndRoom(availabilityWithFulledRoom);
        assertNotNull(cheapestPlanFromFulledRoom);

        Room selectedRoom2 = cheapestPlanFromFulledRoom.getKey();
        Reservation plan2 = cheapestPlanFromFulledRoom.getValue();

        assertEquals(401, selectedRoom2.getRoomNo());
        assertEquals(4, selectedRoom2.getFloor());
        assertEquals("T", selectedRoom2.getRoomType());

        assertEquals(Utils.calculateRoomCost(new Room(401, 4, "T")), plan2.getPrice());
    }

    @Test
    @DisplayName("Test findCheapestPlanAndRoom(availability): Failed")
    void testFindCheapestPlanFailed() {
        HashMap<Room, Boolean> empty = new HashMap<>();

        KeyValue<Room, Reservation> cheapestPlan = Utils.findCheapestPlanAndRoom(empty);
        assertNull(cheapestPlan);

        HashMap<Room, Boolean> availabilityWithOnlyFulledRoom = new HashMap<>();

        availabilityWithOnlyFulledRoom.put(new Room(501, 5, "T"), false);
        availabilityWithOnlyFulledRoom.put(new Room(301, 3, "S"), false);
        availabilityWithOnlyFulledRoom.put(new Room(202, 2, "S"), false);
        availabilityWithOnlyFulledRoom.put(new Room(401, 4, "T"), false);
        availabilityWithOnlyFulledRoom.put(new Room(203, 2, "T"), false);

        KeyValue<Room, Reservation> notAvailable = Utils.findCheapestPlanAndRoom(availabilityWithOnlyFulledRoom);
        assertNull(notAvailable);
    }
}
