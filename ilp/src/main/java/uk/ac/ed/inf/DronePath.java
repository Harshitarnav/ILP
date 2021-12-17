package uk.ac.ed.inf;

/**
 * Class to get the required information about the drone movement.
 */
public class DronePath {
    LongLat fromLocation;
    LongLat toLocation;
    int angle;

    public DronePath(LongLat fromLocation, LongLat toLocation, int angle) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.angle = angle;
    }

    public LongLat getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(LongLat fromLocation) {
        this.fromLocation = fromLocation;
    }

    public LongLat getToLocation() {
        return toLocation;
    }

    public void setToLocation(LongLat toLocation) {
        this.toLocation = toLocation;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "DronePath{" +
                "fromLocation=" + fromLocation +
                ", toLocation=" + toLocation +
                ", angle=" + angle +
                '}';
    }
}
