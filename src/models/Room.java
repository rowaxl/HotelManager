package models;

import helper.InvalidInputException;

public class Room {
	
	private int roomNo;
	private int floor;
	private String roomType;
	
	public Room(int roomNo, int floor, String roomType) {
		super();
		this.roomNo = roomNo;
		this.floor = floor;
		this.roomType = roomType;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public int getFloor() {
		return floor;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomNo(int roomNo) throws InvalidInputException {
		if(roomNo >= 100 || roomNo <= 1599) {
			this.roomNo = roomNo;
		} else {
			throw new InvalidInputException("Invalid room number. Please try one more time.");
		}
	}

	public void setFloor(int floor) throws InvalidInputException {
		if(floor > 0 && floor <= 15) {
			this.floor = floor;
		} else {
			throw new InvalidInputException("Invalid floor. Please try one more time.");
		}
	}

	public void setRoomType(String roomType) throws InvalidInputException {
		if(roomType.equals("S") || roomType.equals("T")) {
			this.roomType = roomType;
		} else {
			throw new InvalidInputException("Invalid room type. Please try one more time.");
		}
	}

}
