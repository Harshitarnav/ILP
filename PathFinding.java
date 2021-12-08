package uk.ac.ed.inf;

import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;

import java.util.ArrayList;
import java.util.List;

public class PathFinding {
    private final NoFlyZone noFlyZonePoly;
    private final Landmark landMarkCoord;
    private final What3Words locationCoord;
    private LongLat currentLoc;
    private int totalMoves;


    public PathFinding(NoFlyZone noFlyZonePoly, Landmark landMarkCoord, What3Words locationCoord, LongLat currentLoc) {
        this.noFlyZonePoly = noFlyZonePoly;
        this.landMarkCoord = landMarkCoord;
        this.locationCoord = locationCoord;
        this.currentLoc = currentLoc.AppletonTower;
        totalMoves = 1500;
    }

    public void droneMovement(ArrayList<LongLat> dronePathCoord, ArrayList<flightPath> flightPath) {
        LongLat currentLocation = dronePathCoord.get(0);
        for(int i=1; i<dronePathCoord.size(); i++) {
            LongLat nextLocation = dronePathCoord.get(i);
            while(currentLocation.closeTo(nextLocation)==false){
                int angleForFlying = currentLocation.droneAngle(nextLocation);
                LongLat nextDroneLocation = currentLocation.nextPosition(angleForFlying);

                //need to check that under confinement zone and not in no fly zone
                if()
            }
        }
    }




}
