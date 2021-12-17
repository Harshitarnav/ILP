package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *App class for autonomous drone to collect all the data from the provided user input(date, web server port and database server port)
 *  and perform the needed functionalities to get the optimum path for the drone traversing in a day.
 */
public class App 
{

    public static void main(String[] args ) {

        if (args.length != 5) {
            throw new IllegalArgumentException("Please enter the arguments in the 'DD MM YYYY WebServerPort DatabaseServerPort' format correctly!");
        }

        //storing the date in the required order
        String day= args[0];
        String month=args[1];
        String year= args[2];
        String Date = year+"-"+month+"-"+day;

        //storing the web and database port
        String webserverport = args[3];
        String databaseport =args[4];

        //parsing and creating instances of all the useful classes
        Database database = new Database(databaseport,"localhost");
        Menus menu = new Menus("localhost", webserverport);
        What3Words what3Words = new What3Words("localhost", webserverport);
        Landmark landmarks = new Landmark("localhost", webserverport);
        NoFlyZone noFlyZone = new NoFlyZone("localhost", webserverport);
        PathFinding pathFinding = new PathFinding(noFlyZone, landmarks, what3Words);

        //checking the number of orders on the date and creating a list of those orders
        ArrayList<Orders> orders = database.ordersPerDay(Date);
        System.out.println("Number of orders on " + Date + " = " + orders.size());

        //initializing each order with all the required details
        database.deliveryDetails(orders, menu);

        //Sorting the order list in the descending order of delivery cost so that the orders with the higher values are prioritized
        // to get an optimum drone path
        orders.sort(Comparator.comparingInt(Orders::getDeliveryCost));
        Collections.reverse(orders);

        //Printing the sorted order list
        for (Orders order : orders){
            System.out.println(order);
        }

        //Creating the required delivery and flightpath table to store all the location, cost and order data in a day
        database.createTable("deliveries");
        database.createTable("flightPath");

        //To get the order cost of all the orders placed.
        int monetaryValueOfOrdersPlaced = 0;
        for(Orders order : orders){
            monetaryValueOfOrdersPlaced = monetaryValueOfOrdersPlaced + order.getDeliveryCost();
        }

        ArrayList<DronePath> droneMove = new ArrayList<>();
        //Creating a GeoJSON instance to store all the drone movements to plot on the map
        GeoJson output = new GeoJson();
        int totalMoves = 0;
        int monetaryValueOfDeliveriesMade = 0;
        for (Orders order : orders) {

            //Creating instances of classes related to drone activity
            ArrayList<LongLat> dronePath = pathFinding.dronePathByOrder(order);
            ArrayList<LongLat> withLandmark = pathFinding.addLandmarks(dronePath);
            droneMove = pathFinding.droneMovement(withLandmark);

            totalMoves = pathFinding.getTotalMoves_calc();

            //Populating the tables with the required data if the drone movement is less than 1400(safe value without Appleton)
            if(totalMoves<1400) {
                //To get the order cost of all the orders delivered
                monetaryValueOfDeliveriesMade = monetaryValueOfDeliveriesMade + order.getDeliveryCost();
                database.deliveriesTable(order.orderNo, order.deliveryTo, order.deliveryCost);
                for (DronePath dronemove : droneMove) {
                    database.flightPathTable(order.orderNo, dronemove.fromLocation.getLat(), dronemove.fromLocation.getLng(),
                            dronemove.angle, dronemove.toLocation.getLat(), dronemove.toLocation.getLng());
                    output.addLocation(dronemove.fromLocation);
                }
            }
        }
        ArrayList<DronePath> paths = pathFinding.finalMoveToAppleton();
        for (DronePath path : paths) {
            database.flightPathTable("Appleton", path.fromLocation.getLat(), path.fromLocation.getLng(),
                    path.angle, path.toLocation.getLat(), path.toLocation.getLng());
            output.addLocation(path.fromLocation);
        }
        float sampledAvg = monetaryValueOfDeliveriesMade/(float)monetaryValueOfOrdersPlaced *100;
        System.out.println("Total number of moves by the drone before going back to Appleton Tower = " + totalMoves);
        System.out.println("Sampled Average Percentage Monetary Value = " + sampledAvg);
        output.writeFile(day+"-"+month+"-"+year);
    }
    }