package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * This class mainly gets the name of the machine and the port where the web server is running to help calculate the
 *cost of delivery and get the restaurant location.
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
        parsingJson();//invoking method
        hashmapForMenu();//invoking method
    }

    /**
     * Function to parse the http url
     */
    private void parsingJson() {
        String url = "http://" + name + ":" + port + "/menus/menus.json";
        String jsonDetailsString = Client.doRequest(url);
        if (jsonDetailsString == null) {
            System.err.println("empty");
        }
        Type listType = new TypeToken<ArrayList<MenuDetails>>() {}.getType();
        restaurantMenuDetails = new Gson().fromJson(jsonDetailsString, listType);
    }


    /**
     * Function to call the hash map builder.
     */
    public void hashmapForMenu() {
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
                if (m.eachItemCost(menuItem) ==null) {
                    continue;
                } else {
                    initialCost = initialCost + m.eachItemCost(menuItem);
                }
            }

        }
        return (initialCost + deliveryCost);
    }

    /**
     * This method accepts all the orders and returns a list with the locations of all the restaurants.
     * @param menuItems A set of strings to accept all the orders one by one.
     * @return list of the restaurant locations
     */
    public ArrayList<String> getRestaurantLocation (String ...menuItems){
        ArrayList<String> resLoc = new ArrayList<>();
        String resLocation;
        for (String menuItem : menuItems) {
            for (MenuDetails m : restaurantMenuDetails) {
                resLocation = m.eachRestaurantLocation(menuItem);
                if (!resLocation.equalsIgnoreCase("NotARestaurant")) {
                    resLoc.add(resLocation);
                }
            }
        }
        Set<String> set = new HashSet<>(resLoc);
        resLoc.clear();
        resLoc.addAll(set);

        return resLoc;
    }
}
