package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import helper.DBConnection;
import helper.InvalidInputException;
import helper.RoomTypes;
import models.Customer;
import models.Reservation;
import models.Room;


public class ReservationDao {
	public static ArrayList<Reservation> getAllReservations(String email){
		ArrayList<Reservation> reservations = new ArrayList<>();
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement prst = conn.prepareStatement("SELECT * FROM Reservation "
					+ "INNER JOIN Customer  ON Reservation.customer_id = Customer.email_address "
					+ "INNER JOIN Room ON Reservation.room_id = Room.room_id "
					+ "WHERE customer_id = ?"
			);
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
			RoomTypes roomTypeQuery = RoomTypes.getRoomTypeFromPersons(request.getPerson());

			PreparedStatement s;
			if (roomTypeQuery == RoomTypes.ALL) {
				s = connection.prepareStatement("SELECT * FROM Room");
			} else {
				s = connection.prepareStatement("SELECT * FROM Room WHERE room_type = ?");
				s.setString(1, roomTypeQuery.toString());
			}

			ResultSet rooms = s.executeQuery();

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
					new Room(
						rooms.getInt("room_id"),
						rooms.getInt("floor"),
						rooms.getString("room_type")
					)
					,isAvailable
				);
			}
		} catch (SQLException e) {
			System.err.println(e.getClass() + " " + e.getMessage());
		}

		return availability;
	}

	public static void addNewReservation(Reservation plan, Customer customer, Room room) {
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement prs = connection.prepareStatement("INSERT INTO Reservation "
					+ "(room_id, customer_id, check_in_date, check_out_date, price, person) "
					+ "VALUES (?, ?, ?, ?, ?, ?)"
			);

			prs.setInt(1, room.getRoomNo());
			prs.setString(2, customer.getEmailAddress());
			prs.setDate(3, plan.getCheckInDate());
			prs.setDate(4, plan.getCheckOutDate());
			prs.setDouble(5, plan.getPrice());
			prs.setInt(6, plan.getPerson());

			prs.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	public static boolean deleteReservation(Reservation r) {
		boolean result = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement checkReservation = con.prepareStatement("SELECT count(*) FROM Reservation WHERE reservation_id = ?");
            checkReservation.setInt(1, r.getReservationId());

			ResultSet rs = checkReservation.executeQuery();
			boolean exists = false;
			if (rs.next()) {
				exists = rs.next() && rs.getInt("count") > 0;
			} else {
            	throw new SQLException("Unhandled SQL Exception");
			}

            if (!exists) {
            	throw new InvalidInputException("Reservation ID not exists");
			}

            PreparedStatement ps = con.prepareStatement("DELETE FROM Reservation WHERE reservation_id = ?");
            ps.setInt(1, r.getReservationId());
            ps.executeUpdate();
            con.close();

			result = true;
        } catch (SQLException | InvalidInputException e) {
            System.err.println(e.getMessage());
        }

		return result;
	}

	public static Reservation findReservationByRoomNo(Room room, Date date) {
		try {
			Connection connection = DBConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Reservation "
					+ "INNER JOIN Customer  ON Reservation.customer_id = Customer.email_address "
					+ "INNER JOIN Room ON Reservation.room_id = Room.room_id "
					+ "WHERE Reservation.room_id = ?"
					+ " AND check_in_date <= ? AND check_out_date > ?"
			);

			ps.setInt(1, room.getRoomNo());
			ps.setDate(2, date);
			ps.setDate(3, date);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				return null;
			}

			String fullName = rs.getString("full_name");
			String phoneNum = rs.getString("phone_num");
			String roomType = rs.getString("room_type");
			int reservationId = rs.getInt("reservation_id");
			int roomId = rs.getInt("room_id");
			String customerId = rs.getString("customer_id");
			Date checkInDate = rs.getDate("check_in_date");
			Date checkOutDate = rs.getDate("check_out_date");
			double price = rs.getDouble("price");
			int person = rs.getInt("person");

			return new Reservation(fullName, phoneNum, roomType, reservationId, roomId, customerId, checkInDate,
					checkOutDate, price, person);
		} catch (SQLException e) {
			System.err.println(e);
			return null;
		}
	}

	public static HashMap<Room, HashMap<Customer, Reservation>> getReservationsByDate(Date date, String type) {
		HashMap<Room, HashMap<Customer, Reservation>> result = new HashMap<>();
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement prst = conn.prepareStatement("SELECT * FROM Reservation INNER JOIN Customer ON Reservation.customer_id = Customer.email_address INNER JOIN Room ON Reservation.room_id = Room.room_id WHERE " + type + "_date = ?");
			prst.setDate(1, date);
			ResultSet rs = prst.executeQuery();
			while(rs.next()) {
				HashMap<Customer, Reservation> custoerWithRservation = new HashMap<>();
				custoerWithRservation.put(
						new Customer(rs.getString("email_address"), rs.getString("full_name"), rs.getString("phone_num")),
						new Reservation(rs.getDate("check_in_date"),rs.getDate("check_out_date"), rs.getDouble("price"), rs.getInt("person"))
				);
				result.put(
						new Room(rs.getInt("room_id"), rs.getInt("floor"), rs.getString("room_type")),
						custoerWithRservation
				);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return result;
	}

}
