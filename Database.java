package uk.ac.ed.inf;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    Connection conn;
    Statement statement;


    public Database(String port, String machineName) {
        String jdbcString = "jdbc:derby://" + machineName + ":" + port + "/derbyDB";
        try {
            conn = DriverManager.getConnection(jdbcString);

            // Create a statement object that we can use for running various
            // SQL statement commands against the database.
            statement = conn.createStatement();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void tableManipulation(String TABLENAME) {
        DatabaseMetaData databaseMetadata = null;
        try {
            databaseMetadata = conn.getMetaData();
            // Note: must capitalise STUDENTS in the call to getTables
            ResultSet resultSet =
                    null;
            resultSet = databaseMetadata.getTables(null, null, TABLENAME.toUpperCase(), null);
            // If the resultSet is not empty then the table exists, so we can drop it
            if (resultSet.next()) {
                statement.execute("drop table " + TABLENAME);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (tableName.equalsIgnoreCase("deliveries")) {
            try {
                statement.execute(
                        "create table deliveries(" +
                                "orderNo char(8)," +
                                "deliveredTo varchar(19)," +
                                "costInPence int)");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void insertCls(String tableName, ArrayList<deliveries> allDeliveries, ArrayList<flightPath> allPaths) {

        if (tableName.equalsIgnoreCase("deliveries")) {
            try {
                PreparedStatement psDelivery = conn.prepareStatement(
                        "insert into flightPath values (?, ?, ?)");
                for (deliveries d : allDeliveries) {
                    psDelivery.setString(1, d.orderNo);
                    psDelivery.setString(2, d.deliveredTo);
                    psDelivery.setInt(3, d.costInPence);
                    psDelivery.execute();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (tableName.equalsIgnoreCase("flightpath")) {
            try {
                PreparedStatement psFlight = conn.prepareStatement(
                        "insert into flightPath values (?, ?, ?)");
                for (flightPath d : allPaths) {
                    psFlight.setString(1, d.orderNo);
                    psFlight.setDouble(2, d.fromLatitude);
                    psFlight.setDouble(3, d.fromLongitude);
                    psFlight.setInt(3, d.angle);
                    psFlight.setDouble(2, d.toLatitude);
                    psFlight.setDouble(3, d.toLongitude);
                    psFlight.execute();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public ArrayList<Orders> ordersPerDay(String date) {
        final String orderQuery = "select * from orders where deliveryDate=(?)";
        final String orderNumber = "orderNo";
        final String deliveryTo = "deliverTo";
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
            throwables.printStackTrace();
        }
        return orderList;
    }

    public void deliveryDetails(ArrayList<Orders> orderList, Menus menu) {
        final String orderQuery = "select * from orderDetails where orderNo=(?)";
        final String item = "item";

        for (Orders orders : orderList) {
            ArrayList<String> items = new ArrayList<>();
            try {
                PreparedStatement psDeliveryQuery = conn.prepareStatement(orderQuery);
                psDeliveryQuery.setString(1, orders.getOrderNo());
                ResultSet rs = psDeliveryQuery.executeQuery();
                while (rs.next()) {
                    String orderNo = rs.getString(item);
                    items.add(item);
                }

                String[] items_str = new String[items.size()];

                for (int i = 0; i < items.size(); i++) {
                    items_str[i] = items.get(i);
                    System.out.println(items_str[i]);
                }
                int delCost = menu.getDeliveryCost(items_str);
                orders.setDeliveryCost(delCost);
                orders.setRestaurantLocations(menu.getRestaurantLocation(items_str));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
