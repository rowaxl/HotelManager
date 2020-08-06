package helper;

import comparator.ReservationComparator;
import models.Reservation;
import models.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Utils {
    public static KeyValue<Room, Reservation> findCheapestPlanAndRoom(HashMap<Room, Boolean> availability) {
        KeyValue<Room, Reservation> result = new KeyValue<>();

        if (availability.size() < 1) {
            return null;
        }

        ArrayList<Reservation> plans = new ArrayList<>();

        for (Room r: availability.keySet()) {
            if (availability.get(r)) {
                plans.add(
                    new Reservation(r.getRoomNo(), calculateRoomCost(r))
                );
            }
        }

        // sort availability low to high by price
        Collections.sort(plans, new ReservationComparator.CompareByPrice());
        Reservation cheapestPlan = plans.get(0);

        // find Room instance from availability
        for (Room r: availability.keySet()) {
            if (cheapestPlan.getRoomNo() == r.getRoomNo()) {
                result.setKey(r);
                result.setValue(cheapestPlan);
            }
        }

        return result;
    }

    public static double calculateRoomCost(Room r) {
        FloorRate floorRate = FloorRate.getFloorRate(r.getFloor());
        RoomTypes roomType = RoomTypes.getRoomType(r.getRoomType());
        double baseRate;

        if (roomType.equals(RoomTypes.SINGLE)) {
            baseRate = RoomRate.SINGLE.getPrice();
        } else {
            baseRate = RoomRate.TWIN.getPrice();
        }

        return baseRate * (floorRate.getRate() + ((double) r.getFloor() / 10));
    }
}
