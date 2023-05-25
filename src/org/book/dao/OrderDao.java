package org.book.dao;

import org.book.entity.Order;
import org.book.entity.User;

import java.util.List;

public interface OrderDao {
    //添加订单
    void addOrder(Order order);

    //根据user获取所有订单列表
    List<Order> getOrderList(User user);

    //获取每个订单的书本数量
    Integer getBookTotal(Order order);

    //根据订单id获取订单信息
    Order getOrder(Integer orderId);

    //删除订单
    void delOrder(Order order);

    //获取全部订单信息
    List<Order> getAllOrder();

    //发货
    void sendOrder(Integer orderId);
}
