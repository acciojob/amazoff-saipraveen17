package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository = new OrderRepository();

    public void saveOrder(Order order) {

        orderRepository.addOrderToDB(order);
    }

    public void saveDeliveryPartner(String partner) {

        orderRepository.addPartnerToDB(partner);
    }

    public void assignOrder(String orderId, String partnerId) {

        orderRepository.assignOrderToPartner(orderId,partnerId);
    }

    public Order getOrderDetails(String orderId) {

        return orderRepository.getOrderFromDB(orderId);
    }

    public DeliveryPartner getPartnerDetails(String partnerId) {

        return orderRepository.getPartnerFromDB(partnerId);
    }

    public int numberOfOrdersAssignedToPartner(String partnerId) {

        return orderRepository.numberOfOrders(partnerId);
    }

    public List<String> listOfOrdersAssignedToPartner(String partnerId) {

        return orderRepository.listOfOrders(partnerId);
    }

    public List<String> listOfAllOrders() {

        return orderRepository.listOfAllOrdersInDB();
    }

    public int countUnassignedOrders() {

       return orderRepository.countUnassignedOrdersInDB();
    }

    public int ordersNotDelivered(String time, String partnerId) {

        return orderRepository.ordersNotDeliveredAfterGivenTime(time, partnerId);
    }

    public String latestDeliveryTime(String partnerId) {

        return orderRepository.lastDeliveryTime(partnerId);
    }

    public void deletePartner(String partnerId) {

        orderRepository.deletePartnerFromDB(partnerId);
    }

    public void deleteOrder(String orderId) {

        orderRepository.deleteOrderFromDB(orderId);
    }
}
