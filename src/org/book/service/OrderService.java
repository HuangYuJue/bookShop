package org.book.service;

import org.book.entity.Order;
import org.book.entity.User;

import java.util.List;

public interface OrderService {
    //添加订单
    void addOrder(Order order);
    //获取所有订单
    List<Order> getOrderList(User user);
    //删除订单
    void delOrder(Order order);
    //获取全部订单信息
    List<Order> getAllOrder();
    //发货
    void sendOrder(Integer orderId);
}
