package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import helper.DBConnection;
import models.Reservation;


public class ReservationDao {
	
	
	public static ArrayList<Reservation> getAllReservations(String email){
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement prst = conn.prepareStatement("SELECT * FROM Reservation INNER JOIN Customer "
					+ "ON Reservation.customer_id = Customer.email_address INNER JOIN Room "
					+ "ON Reservation.room_id = Room.room_id WHERE customer_id = ?");
			prst.setString(1, email);
			
			ResultSet rs = prst.executeQuery();
			
			while(rs.next()) {
				String fullName = rs.getString("full_name");
				String phoneNum = rs.getString("phone_num");
				String roomType = rs.getString("room_type");
				int reservationId = rs.getInt(1);
				int roomId = rs.getInt(2);
				String customerId = rs.getString(3);
				Date checkInDate = rs.getDate(4);
				Date checkOutDate = rs.getDate(5);
				double price = rs.getDouble(6);
				int person = rs.getInt(7);
				Reservation reservation = new Reservation(fullName, phoneNum, roomType, reservationId, roomId, customerId, checkInDate, 
						checkOutDate, price, person);
				reservations.add(reservation);
			}	
		} catch (SQLException e) {
			System.err.println(e);
		}
		
		return reservations;
		
	}
	
 
}
