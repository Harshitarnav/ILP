package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * This class mainly gets the name of the machine and the port where the web server is running to help calculate the
 *cost of delivery.
 *
 * @author Arnav Sinha
 */

public class Menus {

    String name;
    String port;
    ArrayList<MenuDetails> restaurantMenuDetails;

    /**
     * Constructor to get the name of the machine and the port where the web server is running.
     *
     * @param n Get's the name of machine.
     * @param p Get's the prt on which the web server is running.
     */

    public Menus(String n, String p) {
        name = n;
        port = p;
        parsingJson();
        hashmapForMenu();//invoking method
    }

    /**
     * Dictionary is a {@link Map} collection that contains item in the menu as key and cost of that item as a menu.
     */

    private void parsingJson() {
        String url = "http://" + name + ":" + port + "/menus/menus.json";
        String jsonDetailsString = Client.doRequest(url);
        System.out.println(69);
        if (jsonDetailsString == null) {
            System.err.println("empty");
        }
        Type listType = new TypeToken<ArrayList<MenuDetails>>() {}.getType();
        restaurantMenuDetails = new Gson().fromJson(jsonDetailsString, listType);
    }

    /**
     * This method populates the HashMap menuStructure with the input in the ArrayList.
     */

    public void hashmapForMenu() {
        /**
         * loop to get inside the nested list and populate the hashmap with the relevant keys and values, i.e.,
         * item and pence.
         */
        for (MenuDetails m : restaurantMenuDetails) {
            m.hashMap();
        }
    }

    public static int deliveryCost = 50; //The delivery charge.

    /**
     * This method accepts all the orders and calculates the total delivery cost of the delivery done by the drone
     * including the additional delivery charge of 50p per delivery.
     *
     * @param menuItems A set of strings to accept all the orders one by one.
     * @return The total delivery inclusive of the 50p delivery charge per delivery.
     */

    public int getDeliveryCost(String... menuItems) {

        int initialCost = 0;
        for (String menuItem : menuItems) {
            for (MenuDetails m : restaurantMenuDetails) {
                if (m.eachItemCost(menuItem) == -1) {
                    System.err.println("Error 404 : " + m + "Not Found");
                } else {
                    initialCost = initialCost + m.eachItemCost(menuItem);
                }
            }
        }
        return (initialCost + deliveryCost);
    }

        public ArrayList<String> getRestaurantLocation (String ...menuItems){
            ArrayList<String> resLoc = new ArrayList<>();
            String resLocation;
            for (String menuItem : menuItems) {
                for (MenuDetails m : restaurantMenuDetails) {
                    resLocation = m.eachRestaurantLocation(menuItem);
                    if (resLoc.contains(resLocation)) {
                        System.err.println("Enter new restaurant");
                    }
                    if (resLocation != null) {
                        System.err.println("Enter a restaurant");
                    }
                    else {
                        resLoc.add(resLocation);
                    }
                }
            }
            return resLoc;
    }
}
