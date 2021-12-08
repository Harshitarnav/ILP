package uk.ac.ed.inf;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        deliveries myDelobj = new deliveries("Arnav", "is", 04312);
        flightPath myflyobj = new flightPath("12174", 12.0, 4.0, 9312, 455, 740);
        String webserverport = args[3];
        String databaseport =args[4];
        String localhost = "localhost";
        Database database = new Database(databaseport, localhost);
        Menus menu = new Menus(localhost, webserverport);
        String day= args[0];
        String month=args[1];
        String year= args[2];
        String Date = year+"-"+month+"-"+day;
        ArrayList<Orders> orders = database.ordersPerDay(Date);
        for (Orders order : orders){
            System.out.println(order);
        }
        database.deliveryDetails(orders, menu);

        System.out.println(orders.size()+ " no of orders today");
        System.out.println(day+"-"+month);





        System.out.println("Hello World!");
    }
}
