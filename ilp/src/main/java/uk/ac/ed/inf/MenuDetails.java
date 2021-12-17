package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to convert Java Strings into Java Objects. It stores the various restaurant menus and details.
 *
 * @author Arnav Sinha
 */

public class MenuDetails {
    public String location;
    String name;
    ArrayList<Menu> menu;

    public static class Menu {
        String item;
        int pence;

        public Menu(String item, int pence) {
            this.item = item;
            this.pence = pence;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public int getPence() {
            return pence;
        }

        public void setPence(int pence) {
            this.pence = pence;
        }

        @Override
        public String toString() {
            return "Menu{" +
                    "item='" + item + '\'' +
                    ", pence=" + pence +
                    '}';
        }
    }

    public MenuDetails(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "MenuDetails{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    //HashMap for storing the item and the corresponding cost of a menu
    private Map<String, Integer> menuStructure;

    /*
     * This method populates the HashMap menuStructure with the input in the ArrayList. item is the key and cost in pence is the value
     */
    public void hashMap(){
        menuStructure = new HashMap<>();
        for (Menu i : menu) {
            menuStructure.put(i.item, i.pence);
        }
    }

    /*
     *Function to get the cost of each item per order
     */
    public Integer eachItemCost(String menuItem) {
        if (menuStructure.containsKey(menuItem)) {
            return menuStructure.get(menuItem);
        }
        return null;
    }

    /*
     * Function to get the restaurant location for each order
     */
    public String eachRestaurantLocation(String menuItem) {
        if (menuStructure.containsKey(menuItem)) {
            return location;
        }
        return "NotARestaurant";
    }
}
