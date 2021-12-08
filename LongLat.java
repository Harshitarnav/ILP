package uk.ac.ed.inf;

import com.mapbox.geojson.Point;

/**
 * This class is to represent a point on the given map.
 *
 * @author Arnav Sinha
 */
public class LongLat {

    static final LongLat AppletonTower = new LongLat(−3.186874, 55.944494);
    static final LongLat ForestHill = new LongLat(−3.192473, 55.946233);
    static final LongLat KFC = new LongLat(−3.184319, 55.946233);
    static final LongLat Top_of_the_Meadows = new LongLat(−3.192473, 55.942617);
    static final LongLat Buccleuch_St_bus_stop = new LongLat(−3.192473, 55.942617);
    double latitude;
    double longitude;

    /**
     * Constructor for the LongLat class
     *
     * @param lon Takes the input for the longitude of the point.
     * @param lat Takes the input for the latitude of the point.
     */
    public LongLat(double lon, double lat) {
        this.longitude = lon;
        this.latitude = lat;
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
        distance = (Math.sqrt((Math.pow(d.latitude - latitude,2) + Math.pow(d.longitude - longitude,2))));
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
            LongLat same = new LongLat(longitude, latitude);
            return same;
        } else {
            double angleInRadians = Math.toRadians(angle);
            LongLat newPosition = new LongLat(longitude + (0.00015*Math.cos(angleInRadians)),
                                        latitude + (0.00015*Math.sin(angleInRadians)));
            return newPosition;
        }
    }

    public int droneAngle(LongLat d) {
        double lat_diff = d.latitude - latitude;
        double long_diff = d.longitude - longitude;
        int drone_Angle = (int)Math.round(Math.toDegrees(
                Math.atan2(lat_diff, long_diff)));



        return drone_Angle;
    }

    public Point loctoPoint() {
        return Point.fromLngLat(this.longitude, this.latitude);
    }


    /**
     * Helper function for isConfined to reduce the clutter in the main block. Checks the confinement area.
     *
     * @return True if the conditions satisfy and false otherwise.
     */

    public boolean droneStillSane() {
        return (latitude >= 55.942617 && latitude <= 55.946233
                && longitude >= -3.192473 && longitude <= -3.184319);
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
