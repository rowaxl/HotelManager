package comparator;

import models.Reservation;

import java.util.Comparator;

public class ReservationComparator {
    public static class CompareByPrice implements Comparator<Reservation> {

        @Override
        public int compare(Reservation r1, Reservation r2) {
            if (r1.getPrice() == r2.getPrice()) {
                return 0;
            }

            return r1.getPrice() > r2.getPrice() ? 1 : -1;
        }
    }
}
