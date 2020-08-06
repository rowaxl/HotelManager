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
		this.fullName = fullName;
		this.phoneNum = phoneNum;
		this.roomType = roomType;
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

	public Reservation(Date checkInDate, Date checkOutDate, int person) {
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.person = person;
	}

	public Reservation(int roomNo, double price) {
		this.roomNo = roomNo;
		this.price = price;
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

	@Override
	public String toString() {
		return "Reservation{" +
				"room: " + roomNo +
				", price: " + price +
				'}';
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setPerson(int person) {
		this.person = person;
	}
}
