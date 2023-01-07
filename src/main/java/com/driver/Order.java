package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        this.id = id;
        this.deliveryTime = Integer.valueOf(deliveryTime.substring(3))+Integer.valueOf(deliveryTime.substring(0,2))*60;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
