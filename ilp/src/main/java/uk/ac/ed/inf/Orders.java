package uk.ac.ed.inf;

import java.util.ArrayList;

/**
 * Class to store all the important details of an order.
 */
public class Orders {
    String orderNo;
    int deliveryCost;
    String deliveryTo;
    ArrayList<String> resLoc;
    ArrayList<LongLat> landmarkPos;

    public Orders(String orderNo, int deliveryCost, String deliveryTo) {
        this.orderNo = orderNo;
        this.deliveryCost = deliveryCost;
        this.deliveryTo = deliveryTo;
        resLoc = new ArrayList<>();
        landmarkPos = new ArrayList<>();
    }


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public ArrayList<String> getRestaurantLocations() {
        return resLoc;
    }

    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    public void setRestaurantLocations(ArrayList<String> restaurantLocations) {
        this.resLoc = restaurantLocations;
    }


    @Override
    public String toString() {
        return "Orders{" +
                "orderNo ='" + orderNo + '\'' +
                ", deliveryCost = " + deliveryCost +
                ", deliveryTo ='" + deliveryTo + '\'' +
                ",restaurants = " + resLoc +
                '}';
    }
}
