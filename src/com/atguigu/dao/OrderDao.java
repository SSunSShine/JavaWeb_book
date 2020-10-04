package com.atguigu.dao;

import com.atguigu.pojo.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

public interface OrderDao {
    public int saveOrder(Order order);

    public List<Order> queryQrders();

    public int changeOrderStatus(String orderId,Integer status);

    public List<Order> queryOrdersByUserId(Integer userId);
}
