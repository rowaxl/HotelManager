
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
	
	

}
