package comparator;

import models.Room;

import java.util.Comparator;

public class RoomComparator {
    public static class CompareByRoomNo implements Comparator<Room> {

        @Override
        public int compare(Room r1, Room r2) {
            if (r1.getRoomNo() == r2.getRoomNo()) {
                return 0;
            }

            return r1.getRoomNo() > r2.getRoomNo() ? 1 : -1;
        }
    }
}
