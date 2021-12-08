package uk.ac.ed.inf;

import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;

import java.util.ArrayList;
import java.util.List;

public class PointInNoFlyZone {
    ArrayList<ArrayList<LongLat>> allNoFlyCoord;
    private final NoFlyZone noFlyZonePoly;

    public PointInNoFlyZone(NoFlyZone noFlyZonePoly) {
        allNoFlyCoord = new ArrayList<>();
        this.noFlyZonePoly = noFlyZonePoly;
    }

    public void noFlyZonesCoord() {
        for (Polygon p : noFlyZonePoly.polygonsOfNoFlyZone) {
            for (List<Point> manyCoord : p.coordinates()) {
                ArrayList<LongLat> noFlyCoord = new ArrayList<>();
                for (Point coord : manyCoord) {
                    noFlyCoord.add(new LongLat(coord.longitude(), coord.latitude()));
                }
                allNoFlyCoord.add(noFlyCoord);
            }
        }
    }

    // Given three collinear points p, q, r,
    // the function checks if point q lies
    // on line segment 'pr'
    private static boolean onSegment(LongLat p, LongLat q, LongLat r) {
        if (q.getLongitude() <= Math.max(p.getLongitude(), r.getLongitude()) &&
                q.getLongitude() >= Math.min(p.getLongitude(), r.getLongitude()) &&
                q.getLatitude() <= Math.max(p.getLatitude(), r.getLatitude()) &&
                q.getLatitude() >= Math.min(p.getLatitude(), r.getLatitude())) {
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
        double val = (q.getLatitude() - p.getLatitude()) * (r.getLongitude() - q.getLongitude())
                - (q.getLongitude() - p.getLongitude()) * (r.getLatitude() - q.getLatitude());

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

    public boolean checkPointInNoFlyZone(LongLat p, LongLat q) {
        for (ArrayList<LongLat> polyCoordList : allNoFlyCoord) {
            forEach(i : )
        }
    }
}
