package printers;



import java.util.ArrayList;
import dao.ReservationDao;
import helper.RoomTypes;
import models.Reservation;

public class ReservationPrinter {
    public static void printReservations(String email) {
    	ArrayList<Reservation> reservations = ReservationDao.getAllReservations(email);
    	
    	System.out.printf("%-15s %20s %30s %10s %20s %20s %15s %15s %10s\n",
                "Reservation ID",
                "Customer name",
                "E-mail address",
                "Persons",
                "Check-in date",
                "Checkout date",
                "Room Number",
                "Room Type",
                "Price"
            );
            for (Reservation reservation: reservations) {
                System.out.printf("%-15s %20s %30s %10s %20s %20s %15s %15s %10s\n",
                    reservation.getReservationId(),
                    reservation.getFullName(),
                    reservation.getEmailAddress(),
                    reservation.getPerson(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate(),
                    reservation.getRoomNo(),
                    RoomTypes.getRoomTypeName(reservation.getRoomType()),
                    reservation.getPrice()
                );
            }
        
         

    }
}
