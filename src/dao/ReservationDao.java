package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import helper.DBConnection;
import models.Reservation;
import models.Room;


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

	public static HashMap<Room, Boolean> findAvailableRoom(Reservation request) {
		HashMap<Room, Boolean> availability = new HashMap<>();

		try {
			Connection connection = DBConnection.getConnection();
			Statement s = connection.createStatement();
			ResultSet rooms = s.executeQuery("SELECT * FROM Room");

			PreparedStatement ps = connection.prepareStatement(
				"SELECT count(*) as count FROM Reservation WHERE room_id = ?" +
					" AND(" +
							"(check_in_date <= ? AND check_out_date > ? )" +
							"OR (check_in_date < ? AND check_out_date >= ?)" +
							"OR (check_in_date <= ? AND check_out_date > ?)" +
							"OR (check_in_date < ? AND check_out_date > ?)" +
					")"
			);

			while(rooms.next()) {
				ps.setInt(1, rooms.getInt("room_id"));
				ps.setDate(2, request.getCheckInDate());
				ps.setDate(3, request.getCheckInDate());
				ps.setDate(6, request.getCheckInDate());
				ps.setDate(9, request.getCheckInDate());

				ps.setDate(4, request.getCheckOutDate());
				ps.setDate(5, request.getCheckOutDate());
				ps.setDate(7, request.getCheckOutDate());
				ps.setDate(8, request.getCheckOutDate());
				ResultSet reservations = ps.executeQuery();

				boolean isAvailable = reservations.next() && reservations.getInt("count") < 1;

				availability.put(
						new Room(rooms.getInt("room_id"), rooms.getInt("floor"), rooms.getString("room_type"))
						,isAvailable);
			}
		} catch (SQLException e) {
			System.err.println(e.getClass() + " " + e.getMessage());
		}

		return availability;
	}
}
