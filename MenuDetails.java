package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to convert Java Strings into Java Objects.
 *
 * @author Arnav Sinha
 */

public class MenuDetails {
    public String location;
    String name;
    ArrayList<Menu> menu;

    /**
     * Creating an Array list of the Menu attribute.
     */

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

    private Map<String, Integer> menuStructure = new HashMap<>();

    public void hashMap(){
        for (Menu i : menu) {
            menuStructure.put(i.item, i.pence);
        }
    }

    public int eachItemCost(String menuItem) {
        if (menuStructure.containsKey(menuItem)) {
            return menuStructure.get(menuItem);
        }
        return -1;
    }

    public String eachRestaurantLocation(String menuItem) {
        if (menuStructure.containsKey(menuItem)) {
            return location;
        }
        return null;
    }
}
