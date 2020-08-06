package helper;

public enum RoomTypes {
    ALL, SINGLE("S"), TWIN("T");

    private String queryValue;

    RoomTypes() { }

    RoomTypes(String queryValue) {
        this.queryValue = queryValue;
    }

    public static RoomTypes getRoomType(String s) {
        if (s.equals("S")) {
            return RoomTypes.SINGLE;
        } else {
            return RoomTypes.TWIN;
        }
    }

    public static RoomTypes getRoomTypeFromPersons(int person) {
        if (person == 0) {
            return RoomTypes.ALL;
        } else if (person == 1) {
            return RoomTypes.SINGLE;
        } else {
            return RoomTypes.TWIN;
        }
    }

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

    @Override
    public String toString() {
        return this.queryValue;
    }
}
