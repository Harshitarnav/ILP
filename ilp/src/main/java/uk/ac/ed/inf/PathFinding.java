package uk.ac.ed.inf;

import java.util.ArrayList;

/**
 * Class to move the drone
 */
public class PathFinding {
    public static final LongLat AppletonTower = new LongLat(-3.186874, 55.944494);
    private final NoFlyZone noFlyZonePoly;
    private final Landmark landMarkCoord;
    private final What3Words locationCoord;
    private  LongLat currpoint;
    int totalMoves_calc = 0;


    public PathFinding(NoFlyZone noFlyZonePoly, Landmark landMarkCoord, What3Words locationCoord) {
        this.noFlyZonePoly = noFlyZonePoly;
        this.landMarkCoord = landMarkCoord;
        this.locationCoord = locationCoord;
        this.currpoint = AppletonTower;

    }

    //get the path of the drone from Appleton to restaurants and delivery spot

    /**
     * Function to get all the locations that the drone has to go to per order
     * @param orders instance of the Orders class
     * @return a list of all the location coordinates that the drone has to go through to deliver the order
     */
    public ArrayList<LongLat> dronePathByOrder(Orders orders) {
        ArrayList<LongLat> dronePath = new ArrayList<>();
        LongLat currentPosition = currpoint;
        dronePath.add(currentPosition);
        for (String restaurant : orders.getRestaurantLocations()) {
            dronePath.add(locationCoord.getCoordinates(restaurant));
        }
        dronePath.add(locationCoord.getCoordinates(orders.deliveryTo));
        return dronePath;
    }

    /**
     * Function to check if the drone is passing through the No Fly Zone. If it is then a landmark is used to avoid the
     * no fly zone.
     * @param dronePath a list of the locations calculated in the last function
     * @return a list of locations with the landmarks
     */
    public ArrayList<LongLat> addLandmarks(ArrayList<LongLat> dronePath) {
        ArrayList<LongLat> flightPath = new ArrayList<>();
        flightPath.add(dronePath.get(0));
        for (int i = 0; i < dronePath.size() - 1; i++) {

            if (noFlyZonePoly.checkPointInNoFlyZone(dronePath.get(i), dronePath.get(i + 1)) &&
                    dronePath.get(i).isConfined() && dronePath.get(i+1).isConfined()) {
                LongLat bestLandmark = null;
                double calculatedDistance = 999999999;
                ArrayList<LongLat> landmarks = landMarkCoord.getLandmarks();
                for (LongLat landmark : landmarks) {
                    if (!noFlyZonePoly.checkPointInNoFlyZone(dronePath.get(i), landmark) &&
                            !noFlyZonePoly.checkPointInNoFlyZone(dronePath.get(i + 1), landmark)) {
                        double calculatedDistanceLandmark = landmark.distanceTo(dronePath.get(i)) + landmark.distanceTo(dronePath.get(i + 1));
                        if (calculatedDistanceLandmark < calculatedDistance) {
                            calculatedDistance = calculatedDistanceLandmark;
                            bestLandmark = landmark;
                        }
                    }
                }

                if (bestLandmark != null) {
                    flightPath.add(bestLandmark);
                }
                flightPath.add(dronePath.get(i + 1));
            } else {
                flightPath.add(dronePath.get(i + 1));
            }

        }
        return flightPath;
    }

    /**
     * Function to move the drone in from one position to the next
     * @param flightPath a list of locations with landmarks if necessary
     * @return a list of drone movement path of every movement and the angle at which it moves
     */
    public ArrayList<DronePath> droneMovement(ArrayList<LongLat> flightPath)
    {
        ArrayList<DronePath> droneMove = new ArrayList<>();
        LongLat nextDronePosition = null;
        int angle;
        LongLat currentDronePosition = flightPath.get(0);
        for (int i = 0; i < flightPath.size() - 1; i++)
        {
            while (!currentDronePosition.closeTo(flightPath.get(i + 1)))
            {
                angle = currentDronePosition.droneAngle(flightPath.get(i + 1));
                nextDronePosition = currentDronePosition.nextPosition(angle);
                //shifting the drone path so that the drone does not enter the no fly zone
                while (noFlyZonePoly.checkPointInNoFlyZone(currentDronePosition, nextDronePosition))
                {
                    nextDronePosition = currentDronePosition.nextPosition(angle+10);
                }
                droneMove.add(new DronePath(currentDronePosition, nextDronePosition, angle));
                currentDronePosition = nextDronePosition;
            }
            if (flightPath.get(i) != AppletonTower && !landMarkCoord.landmarkPos.contains(flightPath.get(i))) {
                droneMove.add(new DronePath(currentDronePosition, nextDronePosition, -999));
            }
        }
        //calculates the total moves by the drone in a day(eventually)
        totalMoves_calc += droneMove.size();
        //sets the next starting location as the last delivery location
        currpoint=currentDronePosition;
        return droneMove;
    }

    /**
     * Function to get the drone back to Appleton Tower at the end of the day.
     * @return a list of all the drone movements including the last one, i.e, coming back to Appleton Tower
     */
    public ArrayList<DronePath> finalMoveToAppleton() {
        LongLat lastDeliveryPosition = currpoint;
        ArrayList<LongLat> finalMove = new ArrayList<>();
        finalMove.add(lastDeliveryPosition);
        finalMove.add(AppletonTower);
        finalMove = addLandmarks(finalMove);
        ArrayList<DronePath> finalMoveList = droneMovement(finalMove);
        return finalMoveList;
    }

    /**
     * Getter for obtaining the total moves by the drone in a day.
     * @return the total moves by the drone in a day
     */
    public int getTotalMoves_calc(){
        return totalMoves_calc;
    }
}

