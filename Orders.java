package uk.ac.ed.inf;

import java.util.ArrayList;

public class Orders {
    String orderNo;
    int deliveryCost;
    String deliveryTo;
    ArrayList<String> restaurantLocations;

    public Orders(String orderNo, int deliveryCost, String deliveryTo) {
        this.orderNo = orderNo;
        this.deliveryCost = deliveryCost;
        this.deliveryTo = deliveryTo;
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

    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    public void setRestaurantLocations(ArrayList<String> restaurantLocations) {
        this.restaurantLocations = restaurantLocations;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderNo='" + orderNo + '\'' +
                ", deliveryDate=" + deliveryCost +
                ", deliveryTo='" + deliveryTo + '\'' +
                '}';
    }
}
