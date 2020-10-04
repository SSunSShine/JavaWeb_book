package com.atguigu.service;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    public String createOrder(Cart cart,Integer userId);

    public List<Order> showAllOrders();

    public void sendOrder(String orderId);

    public List<OrderItem> showOrderDetail(String orderId);

    public List<Order> showMyOrders(Integer userId);

    public void receiveOrder(String orderId);
}
