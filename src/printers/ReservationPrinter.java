package printers;



import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import dao.ReservationDao;
import helper.RoomTypes;
import models.Reservation;
import models.Room;

import static helper.Utils.calculateRoomCost;

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

    public static void printAvailabilities() {
        Date today = new Date(new java.util.Date().getTime());

        Reservation request = new Reservation(today, today, 0);
        HashMap<Room, Boolean> availabilities = ReservationDao.findAvailableRoom(request);

        System.out.printf("%-15s %10s\n", "Available Rooms for", today.toLocalDate());
        System.out.printf("%-10s %10s\n", "Room No", "Price");
        for (Room r: availabilities.keySet()) {
            if (availabilities.get(r) == true) {
                System.out.printf("%-10s %10.2f\n", r.getRoomNo(), calculateRoomCost(r));
            }
        }
    }

    public static void printUsedRooms() {
        Date today = new Date(new java.util.Date().getTime());

        Reservation request = new Reservation(today, today, 0);
        HashMap<Room, Boolean> availabilities = ReservationDao.findAvailableRoom(request);

        System.out.printf("%-10s %20s %20s %30s %20s %15s %15s %10s %10s\n",
            "Room Number",
            "Reservation ID",
            "Customer name",
            "E-mail address",
            "Phone number",
            "Check-in date",
            "Checkout date",
            "Room Type",
            "Price"
        );

        System.out.printf("%-15s %10s\n", "Used Rooms for", today.toLocalDate());
        for (Room r: availabilities.keySet()) {
            if (availabilities.get(r) == false) {
                Reservation plan = ReservationDao.findReservationByRoomNo(r, today);

                assert plan != null;
                System.out.printf("%-10s %21s %20s %30s %20s %15s %15s %10s %10.2f\n",
                    plan.getRoomNo(),
                    plan.getReservationId(),
                    plan.getFullName(),
                    plan.getEmailAddress(),
                    plan.getPhoneNum(),
                    plan.getCheckInDate(),
                    plan.getCheckOutDate(),
                    RoomTypes.getRoomTypeName(plan.getRoomType()),
                    plan.getPrice()
                );
            }
        }

    }
}
