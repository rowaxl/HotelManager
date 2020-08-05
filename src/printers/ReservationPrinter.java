package printers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import helper.DBConnection;
import models.Reservation;

public class ReservationPrinter {
    public static void printReservations(String email) {
        try {
            Connection conn = DBConnection.getConnection();
            
            Date d1 = new Date();
            Reservation reserv1 = new Reservation(2, 100, "abc@gmail.com", d1, d1,10.95, 1); 
            System.out.println("Room Number: "+reserv1.getRoomNo());
            System.out.println("Email Address: "+reserv1.getEmailAddress());
            System.out.println("Reservation: "+reserv1.getReservationId());
            System.out.println("Checkin date: "+reserv1.getCheckInDate());
            System.out.println("Checkout date: "+reserv1.getCheckOutDate());
            System.out.println("Price: "+reserv1.getPrice());
            System.out.println("Person: "+reserv1.getPerson());

        } catch (SQLException e) {
            System.out.println(e);
        }


    }
}
