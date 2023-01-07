package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    Map<String, Order> orderDB = new HashMap<>();
    Map<String, DeliveryPartner> partnerDB = new HashMap<>();
    Map<String, List<String>> partnerOrdersDB = new HashMap<>();
    Map<String, String> orderPartnerPairDB = new HashMap<>();

    public void addOrderToDB(Order order) {

        String orderId = order.getId();
        if(orderDB.containsKey(orderId)) return;
        orderDB.put(orderId, order);
    }

    public void addPartnerToDB(String partnerId) {

        if(partnerDB.containsKey(partnerId)) return;
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        partnerDB.put(partnerId, partner);
    }

    public void assignOrderToPartner(String orderId, String partnerId) {

        if(orderPartnerPairDB.containsKey(orderId)) {
            return;
        }
        if(!partnerOrdersDB.containsKey(partnerId)) {
            partnerOrdersDB.put(partnerId, new ArrayList<>());
        }
        List<String> ordersList = partnerOrdersDB.get(partnerId);
        ordersList.add(orderId);
        partnerOrdersDB.put(partnerId, ordersList);
        orderPartnerPairDB.put(orderId,partnerId);
        partnerDB.get(partnerId).setNumberOfOrders(ordersList.size());
    }

    public Order getOrderFromDB(String orderId) {

        return orderDB.get(orderId);
    }

    public DeliveryPartner getPartnerFromDB(String partnerId) {

        return partnerDB.get(partnerId);
    }

    public int numberOfOrders(String partnerId) {

        return partnerOrdersDB.get(partnerId).size();
    }

    public List<String> listOfOrders(String partnerId) {

        return partnerOrdersDB.get(partnerId);
    }

    public List<String> listOfAllOrdersInDB() {

        List<String> orderList = new ArrayList<>(orderDB.keySet());
        return orderList;
    }

    public int countUnassignedOrdersInDB() {

        int count = 0;
        for(String orderId : orderDB.keySet()) {
            if(!orderPartnerPairDB.containsKey(orderId)) {
                count++;
            }
        }
        return count;
    }

    public int ordersNotDeliveredAfterGivenTime(String time, String partnerId) {

        int count = 0;
        int timeInMinutes = Integer.valueOf(time.substring(3))+Integer.valueOf(time.substring(0,2))*60;
        for(String orderId : partnerOrdersDB.get(partnerId)) {
            if(orderDB.get(orderId).getDeliveryTime()>timeInMinutes) {
                count++;
            }
        }
        return count;
    }

    public String lastDeliveryTime(String partnerId) {

        List<String> ordersList = partnerOrdersDB.get(partnerId);
        Collections.sort(ordersList, (a,b)->orderDB.get(a).getDeliveryTime()-orderDB.get(b).getDeliveryTime());
        String orderId = ordersList.get(ordersList.size()-1);
        int time = orderDB.get(orderId).getDeliveryTime();
        StringBuilder lastTime = new StringBuilder();
        int hours = time/60;
        int minutes = time%60;
        if(hours<10) {
            lastTime.append(0);
        }
        lastTime.append(hours);
        lastTime.append(':');
        if(minutes<10) {
            lastTime.append(0);
        }
        lastTime.append(minutes);
        return lastTime.toString();
    }

    public void deletePartnerFromDB(String partnerId) {

        if(partnerOrdersDB.containsKey(partnerId)) {
            for(String orderId : partnerOrdersDB.get(partnerId)) {
                orderPartnerPairDB.remove(orderId);
            }
            partnerOrdersDB.remove(partnerId);
        }
        partnerDB.remove(partnerId);
    }

    public void deleteOrderFromDB(String orderId) {

        if(orderPartnerPairDB.containsKey(orderId)) {
            String partnerId = orderPartnerPairDB.get(orderId);
            List<String> ordersList = partnerOrdersDB.get(partnerId);
            ordersList.remove(orderId);
            partnerOrdersDB.put(partnerId, ordersList);
            orderPartnerPairDB.remove(orderId);
            partnerDB.get(partnerId).setNumberOfOrders(ordersList.size());
        }
        orderDB.remove(orderId);
    }
}
