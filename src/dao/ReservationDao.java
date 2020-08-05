package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import helper.DBConnection;
import models.Reservation;


public class ReservationDao {
	
	public static ArrayList<Reservation> getAllReservations(){
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reservation INNER JOIN Customer "
					+ "ON Reservation.customer_id = Customer.email_address INNER JOIN Room "
					+ "ON Reservation.room_id = Room.room_id WHERE check_in_date = ?");
			
			while(rs.next()) {
				int reservationId = rs.getInt(1);
				int roomId = rs.getInt(2);
				String customerId = rs.getString(3);
				Date checkInDate = rs.getDate(4);
				Date checkOutDate = rs.getDate(5);
				double price = rs.getDouble(6);
				int person = rs.getInt(7);
				Reservation reservation = new Reservation(reservationId, roomId, customerId, checkInDate, 
						checkOutDate, price, person);
				reservations.add(reservation);
			}	
		} catch (SQLException e) {
			System.err.println(e);
		}
		
		return reservations;
		
	}
 
}
