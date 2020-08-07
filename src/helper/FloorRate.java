package helper;

public enum FloorRate {
    LOW(1.0), HIGH(1.5);

    private double rate;

    FloorRate(double rate) {
        this.rate = rate;
    }

    public static double getFloorRate(int floor) throws InvalidInputException {
        if (floor < 1) {
            throw new InvalidInputException("Floor cannot be zero or negative");
        }

        if (floor <= 5) {
            return FloorRate.LOW.getRate() + ((double) floor / 10);
        } else {
            return FloorRate.HIGH.getRate() + ((double) floor / 10);
        }
    }

    public double getRate() {
        return this.rate;
    }
}
