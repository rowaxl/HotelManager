package helper;


public enum RoomRate {
	
	SINGLE(120),TWIN(170);
	
	private double price;
	
	RoomRate(double price){
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}

}
