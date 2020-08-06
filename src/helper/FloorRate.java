package helper;

public enum FloorRate {
    LOW(1.0), HIGH(1.5);

    private double rate;
    FloorRate(double rate) {
        this.rate = rate;
    }

    public static FloorRate getFloorRate(int floor) {
        if (floor > 5) {
            return FloorRate.HIGH;
        } else {
            return FloorRate.LOW;
        }
    }

    public double getRate() {
        return this.rate;
    }
}
