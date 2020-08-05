package helper;

public enum RoomTypes {
	
	SINGLE,
    TWIN;
    public static String getRoomTypeName(String type) {
        switch (type) {
            case "S":
                return SINGLE.name();
            case "T":
                return TWIN.name();
            default:
                return "Invalid Room Type";
        }
    }

}
