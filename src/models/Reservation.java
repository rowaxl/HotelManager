package models;

import java.sql.Date;

public class Reservation {
	
	private String fullName;
	private String phoneNum;
	private String roomType;
	private int reservationId;
	private int roomNo;
	private String emailAddress;
	private Date checkInDate;
	private Date checkOutDate;
	private double price;
	private int person;
	
	public Reservation(String fullName, String phoneNum, String roomType, int reservationId, 
			int roomNo, String emailAddress, Date checkInDate, Date checkOutDate,
			double price, int person) {
		super();
		this.reservationId = reservationId;
		this.roomNo = roomNo;
		this.emailAddress = emailAddress;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.price = price;
		this.person = person;
	}
	
	public Reservation(int reservationId, int roomNo, String emailAddress, Date checkInDate, Date checkOutDate,
			double price, int person) {
		super();
		this.reservationId = reservationId;
		this.roomNo = roomNo;
		this.emailAddress = emailAddress;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.price = price;
		this.person = person;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public String getRoomType() {
		return roomType;
	}

	public int getReservationId() {
		return reservationId;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public double getPrice() {
		return price;
	}

	public int getPerson() {
		return person;
	}

}
