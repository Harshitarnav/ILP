package uk.ac.ed.inf;

import java.sql.*;
import java.util.ArrayList;

/**
 * Database class used to store information into the Apache Derby database.
 */
public class Database {
    Connection conn;
    Statement statement;

    final String orderNumber = "orderNo";
    final String deliveryTo = "deliverTo";

    public Database(String port, String name) {
        String jdbcString = "jdbc:derby://" + name + ":" + port + "/derbyDB";
        try {
            //to get a java.sql.Connection to the database that we specify with jdbcString.
            conn = DriverManager.getConnection(jdbcString);

            // Creating a statement object that we can use for running various
            // SQL statement commands against the database.
            statement = conn.createStatement();

        } //Since most databases can throw a java.sql.SQLException, catch block is needed
        catch (SQLException throwables) {
            System.err.println("Error : Unable to connect to the Database.");
            throwables.printStackTrace();
        }
    }

    /**
     * Function to drop table if the table is already present so that the create table command does not fail
     *
     * @param TABLENAME a String parameter to drop the table if a table of this name is present
     */
    public void tableManipulation(String TABLENAME) {
        DatabaseMetaData databaseMetadata = null;
        try {
            //to check if derbyDB database has the TABLENAME passed.
            databaseMetadata = conn.getMetaData();
            ResultSet resultSet = databaseMetadata.getTables(null, null, TABLENAME.toUpperCase(), null);
            // If the resultSet is not empty then the table exists, so we can drop it
            if (resultSet.next()) {
                statement.execute("drop table " + TABLENAME);
            }
        }
        catch (SQLException throwables) {
            System.err.println("There is no such table present in the database!");
            throwables.printStackTrace();
        }
    }

    /**
     * Creating an empty table. Now we can create a table of that name knowing that its not already present
     *
     * @param tableName name of the empty table created
     */
    public void createTable(String tableName) {
        tableManipulation(tableName);
        if (tableName.equalsIgnoreCase("flightpath")) {
            try {
                statement.execute(
                        "create table flightpath(" +
                                "orderNo char(8)," +
                                "fromLongitude double," +
                                "fromLatitude double," +
                                "angle integer," +
                                "toLongitude double," +
                                "toLatitude double)");
            }
            catch (SQLException throwables) {
                System.err.println("Error : Cannot create the flightPath Table");
                throwables.printStackTrace();
            }
        } else if (tableName.equalsIgnoreCase("deliveries")) {
            try {
                statement.execute(
                        "create table deliveries(" +
                                "orderNo char(8)," +
                                "deliveredTo varchar(19)," +
                                "costInPence int)");
            }
            catch (SQLException throwables) {
                System.err.println("Error : Cannot create the deliveries Table");
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Function to execute a parameterised query to fill in all the details in the deliveries table
     *
     * @param orderNo     order number of a particular order that day
     * @param deliveredTo drop off location for the entire order
     * @param costInPence the total cost of that order
     */
    public void deliveriesTable(String orderNo, String deliveredTo, int costInPence) {
        try {
            //allows to execute a parameterised query
            PreparedStatement psDelivery = conn.prepareStatement(
                    "insert into deliveries values (?, ?, ?)");
            psDelivery.setString(1, orderNo);
            psDelivery.setString(2, deliveredTo);
            psDelivery.setInt(3, costInPence);
            psDelivery.execute();
        }
        catch (SQLException throwables) {
            System.err.println("Error : Cannot insert values into the deliveries Table");
            throwables.printStackTrace();
        }
    }

    /**
     * Function to execute a parameterised query to fill in all the required details in the flightPath table one
     * detail set at a time
     * @param orderNo order number of a particular order that day
     * @param fromLatitude the latitude coordinate of the initial position of the drone
     * @param fromLongitude the longitude coordinate of the initial position of the drone
     * @param angle the angle between the initial position and the final position of the drone
     * @param toLongitude the longitude coordinate of the final position of the drone
     * @param toLatitude the latitude coordinate of the final position of the drone
     */
    public void flightPathTable(String orderNo, double fromLatitude, double fromLongitude, int angle, double toLongitude, double toLatitude) {

        try {
            PreparedStatement psFlight = conn.prepareStatement(
                    "insert into flightPath values (?, ?, ?, ?, ?, ?)");
            psFlight.setString(1, orderNo);
            psFlight.setDouble(2, fromLatitude);
            psFlight.setDouble(3, fromLongitude);
            psFlight.setInt(4, angle);
            psFlight.setDouble(5, toLatitude);
            psFlight.setDouble(6, toLongitude);
            psFlight.execute();
        } catch (SQLException throwables) {
            System.err.println("Error : Cannot insert values into the flightPath Table");
            throwables.printStackTrace();
        }
    }

    /**
     * Function to take in date as a parameter to get the list of orders of that day
     * @param date date of the day that the user fed in
     * @return a list of array values of all the orders
     */
    public ArrayList<Orders> ordersPerDay(String date) {
        final String orderQuery = "select * from orders where deliveryDate=(?)";

        // Search for the cost of that order number and add them to a list
        ArrayList<Orders> orderList = new ArrayList<>();

        try {
            PreparedStatement psDeliveryQuery = conn.prepareStatement(orderQuery);
            psDeliveryQuery.setDate(1, Date.valueOf(date));
            ResultSet rs = psDeliveryQuery.executeQuery();
            while (rs.next()) {
                String orderNo = rs.getString(orderNumber);
                String deliveryLocation = rs.getString(deliveryTo);
                orderList.add(new Orders(orderNo, 0, deliveryLocation));
            }
        } catch (SQLException throwables) {
            System.err.println("Error : Cannot obtain orders for the day.");
            throwables.printStackTrace();
        }
        return orderList;
    }

    /**
     * Function to input delivery details to each order block
     * @param orderList a list of details important for each order
     * @param menu instance of menu class containing the menu details
     */
    public void deliveryDetails(ArrayList<Orders> orderList, Menus menu) {
        final String orderQuery = "select * from orderDetails where orderNo=(?)";

        for (Orders orders : orderList) {
            ArrayList<String> items = new ArrayList<>();
            try {
                PreparedStatement psDeliveryQuery = conn.prepareStatement(orderQuery);
                psDeliveryQuery.setString(1, orders.getOrderNo());
                ResultSet rs = psDeliveryQuery.executeQuery();
                while (rs.next()) {
                    String item= rs.getString("item");
                    items.add(item);
                }
                //Converting Arraylist to String array.
                String[] items_str = new String[items.size()];

                for (int i = 0; i < items.size(); i++) {
                    items_str[i] = items.get(i);
                }
                int delCost = menu.getDeliveryCost(items_str);
                orders.setDeliveryCost(delCost);
                orders.setRestaurantLocations(menu.getRestaurantLocation(items_str));
            } catch (SQLException throwables) {
                System.err.println("Error : Cannot obtain the required data.");
                throwables.printStackTrace();
            }
        }
    }
}

