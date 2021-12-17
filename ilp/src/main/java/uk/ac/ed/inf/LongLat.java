package uk.ac.ed.inf;

/**
 * This class is to represent a point on the given map.
 *
 * @author Arnav Sinha
 */
public class LongLat {

    double lat;
    double lng;

    /**
     * Constructor for the LongLat class
     *
     * @param lon Takes the input for the longitude of the point.
     * @param lat Takes the input for the latitude of the point.
     */
    public LongLat(double lon, double lat) {
        this.lng = lon;
        this.lat = lat;
    }

    /**
     * This is a method to check if the point is within the drone confinement area.
     *
     * @return Returns true if the drone is inside the confinement zone and false otherwise.
     */

    public boolean isConfined() {
        if (droneStillSane()) {
            return true;
        }
        return false;
    }

    /**
     * This method finds the distance between the initial and the next position of the drone.
     *
     * @param d Object is passed as a parameter which is a different instance of the class.
     * @return The Pythagorean distance between the two points.
     */

    public double distanceTo(LongLat d) {
        double distance;
        distance = (Math.sqrt((Math.pow(d.lat - lat,2) + Math.pow(d.lng - lng,2))));
        return distance;
    }

    /**
     * This method checks if the two points are close to each other or not.
     *
     * @param d Object is passed as a parameter which is a different instance of the class.
     * @return True if the Pythagorean distance between them is less than the distance tolerence of 0.00015 degree and
     * false otherwise.
     */

    public boolean closeTo(LongLat d) {
        if (distanceTo(d) < 0.00015) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method represents the new position of the drone.
     *
     * @param angle The direction in which the drone has moved.
     * @return Object with the new position of the drone.
     */

    public LongLat nextPosition(int angle) {
        if (angle == -999) {
            LongLat same = new LongLat(lng, lat);
            return same;
        } else {
            double angleInRadians = Math.toRadians(angle);
            LongLat newPosition = new LongLat(lng + (0.00015*Math.cos(angleInRadians)),
                                        lat + (0.00015*Math.sin(angleInRadians)));
            return newPosition;
        }
    }

    /**
     * Function to calculate the angle between two positions of the drone.
     * @param d A LongLat parameter representing the final position of the drone in every movement
     * @return integer(well rounded off) value of drone angle
     */
    public int droneAngle(LongLat d) {
        double yDistance = d.getLat() - this.lat;
        double xDistance = d.getLng() - this.lng;
        double angleRadians = Math.atan(yDistance / xDistance);
        double angleDegrees = Math.toDegrees(angleRadians);
        double angleFromEast = 0.0;
        // Calculate the angle anti-clockwise, with East = 0 degrees
        if (xDistance > 0 && yDistance > 0) {
            angleFromEast = angleDegrees;
        } else if (xDistance < 0 && yDistance > 0) {
            angleFromEast = 180 - Math.abs(angleDegrees);
        } else if (xDistance < 0 && yDistance < 0) {
            angleFromEast = 180 + angleDegrees;
        } else if (xDistance > 0 && yDistance < 0) {
            angleFromEast = 360 - (Math.abs(angleDegrees));
        }
        // Round the angle up or down to the corresponding multiple of 10
        int angleRoundedDown = (int) (angleFromEast - angleFromEast % 10);
        int angleRoundedUp = (int) ((10 - angleFromEast % 10) + angleFromEast);
        if ((angleRoundedUp - angleFromEast) < (angleFromEast - angleRoundedDown)) {
            return angleRoundedUp;
        } else {
            return angleRoundedDown;
        }
    }

    /**
     * Helper function for isConfined to reduce the clutter in the main block. Checks the confinement area.
     *
     * @return True if the conditions satisfy and false otherwise.
     */

    public boolean droneStillSane() {
        return (lat >= 55.942617 && lat <= 55.946233
                && lng >= -3.192473 && lng <= -3.184319);
    }

    /**
     * Getter to get the longitude
     */
    public double getLng() {
        return lng;
    }

    /**
     * Getter to get the latitude
     */
    public double getLat() {
        return lat;
    }

    @Override
    public String toString() {
        return "LongLat{" +
                "latitude=" + lat +
                ", longitude=" + lng +
                '}';
    }


}
