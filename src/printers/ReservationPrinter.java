package printers;

import java.sql.Connection;
import java.sql.SQLException;

public class ReservationPrinter {
    public static void printReservations(String email) {
        try {
            Connection conn = DBConnection.getConnection();

        } catch (SQLException e) {
            System.out.println(e);
        }


    }
}
