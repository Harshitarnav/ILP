package uk.ac.ed.inf;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to get the no fly zone coordinates so that the drone can be manipulated if the drone path intersects with the no fly zone area
 */
public class NoFlyZone {
    ArrayList<Polygon> polygonsOfNoFlyZone;
    ArrayList<ArrayList<LongLat>> allNoFlyCoord;
    String port;
    String name;

    public NoFlyZone(String name, String port) {
        polygonsOfNoFlyZone = new ArrayList<>();
        allNoFlyCoord = new ArrayList<>();
        this.port = port;
        this.name = name;
        getNoFlyZone();
        noFlyZonesCoord();
    }

    /**
     * Function to parse all the no fly zones and get all the polygons of no fly zone
     */
    private void getNoFlyZone() {
        String url = "http://" + name + ":" + port + "/buildings/" + "no-fly-zones.geojson";
        String jsonDetailsString = Client.doRequest(url);
        FeatureCollection featureCollection = FeatureCollection.fromJson(jsonDetailsString);
        ArrayList<Feature> featureArrayList = (ArrayList<Feature>) featureCollection.features();
        for(Feature f : featureArrayList) {
            Geometry g = f.geometry();
            Polygon p = (Polygon) g;
            polygonsOfNoFlyZone.add(p);
        }
    }

    /**
     * Function to get all the coordinates from the polygons of no fly zones ontained in the last function.
     */
    public void noFlyZonesCoord() {
        for (Polygon p : polygonsOfNoFlyZone) {
            for (List<Point> manyCoord : p.coordinates()) {
                ArrayList<LongLat> noFlyCoord = new ArrayList<>();
                for (Point coord : manyCoord) {
                    noFlyCoord.add(new LongLat(coord.longitude(), coord.latitude()));
                }
                allNoFlyCoord.add(noFlyCoord);
            }
        }
    }

    /*
    This entire code block has been taken from GeeksForGeeks which is used to see if a given point lies inside the no fly zone or not.
    Link : https://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/
     */

    // Given three collinear points p, q, r,
    // the function checks if point q lies
    // on line segment 'pr'
    private static boolean onSegment(LongLat p, LongLat q, LongLat r) {
        if (q.getLng() <= Math.max(p.getLng(), r.getLng()) &&
                q.getLng() >= Math.min(p.getLng(), r.getLng()) &&
                q.getLat() <= Math.max(p.getLat(), r.getLat()) &&
                q.getLat() >= Math.min(p.getLat(), r.getLat())) {
            return true;
        }
        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    private static int orientation(LongLat p, LongLat q, LongLat r) {
        double val = (q.getLat() - p.getLat()) * (r.getLng() - q.getLng())
                - (q.getLng() - p.getLng()) * (r.getLat() - q.getLat());

        if (val == 0) {
            return 0; // collinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // The function that returns true if
    // line segment 'p1q1' and 'p2q2' intersect.
    static boolean doIntersect(LongLat p1, LongLat q1,
                               LongLat p2, LongLat q2) {
        // Find the four orientations needed for
        // general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
        {
            return true;
        }

        // Special Cases
        // p1, q1 and p2 are collinear and
        // p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1))
        {
            return true;
        }

        // p1, q1 and p2 are collinear and
        // q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1))
        {
            return true;
        }

        // p2, q2 and p1 are collinear and
        // p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2))
        {
            return true;
        }

        // p2, q2 and q1 are collinear and
        // q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2))
        {
            return true;
        }

        // Doesn't fall in any of the above cases
        return false;
    }

    //returns true if the drone intersects a point in no fly zone and false otherwise
    public boolean checkPointInNoFlyZone(LongLat p, LongLat q) {
        for (ArrayList<LongLat> polyCoordList : allNoFlyCoord) {
            for (int i=0 ; i<polyCoordList.size()-1 ; i++ ) {
                if (doIntersect(p, q, polyCoordList.get(i), polyCoordList.get(i+1))){
                    return true;
                }
            }
            if (doIntersect(p, q, polyCoordList.get(0), polyCoordList.get(polyCoordList.size()-1))){
                return true;
            }
        }
        return false;
    }
}
