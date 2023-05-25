package org.book.service.impl;

import org.book.dao.DetailDao;
import org.book.dao.OrderDao;
import org.book.entity.Cart;
import org.book.entity.Detail;
import org.book.entity.Order;
import org.book.entity.User;
import org.book.service.CartService;
import org.book.service.OrderService;

import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private DetailDao detailDao;
    private CartService cartService;
    @Override
    public void addOrder(Order order) {
        //1.点击支付，添加订单
        //2.购物车详情页添加信息
        //3.删除购物车内相关信息
        //①：在order表中添加order记录
        orderDao.addOrder(order);   //且执行完这一步，Order中的id有值，因为dao层设置进了
        //②：通过order类获取到的userBean是User类，再通过User类获取到CartMap是CartMap类，再在CartMap类中获取到cartMap
        Map<Integer, Cart> cartMap = order.getOrderUser().getCartMap().getCartMap();
        //遍历cartMap中的所有键值对的值，cartMap.values()
        for (Cart cart : cartMap.values()){
            //初始化Detail类
            Detail detail = new Detail();
            //将cart中的参数设置进detail对象中
            detail.setBook(cart.getBook());
            detail.setBuyCount(cart.getBuyCount());
            detail.setOrderBean(order);
            //调用detail层的dao进行添加
            detailDao.addDetail(detail);
        }
        //③：循环删除购物车中内容
        for (Cart cart:cartMap.values()){
            cartService.delCart(cart);
        }
    }

    @Override
    public List<Order> getOrderList(User user) {
        List<Order> orderList = orderDao.getOrderList(user);
        for (Order order : orderList){
            Integer bookTotal = orderDao.getBookTotal(order);
            order.setBookTotal(bookTotal);
        }
        return orderList;
    }

    @Override
    public void delOrder(Order order) {
        orderDao.delOrder(order);
    }

    @Override
    public List<Order> getAllOrder() {
        List<Order> allOrder = orderDao.getAllOrder();
        for (Order order:allOrder){
            Integer bookTotal = orderDao.getBookTotal(order);
            order.setBookTotal(bookTotal);
        }
        return allOrder;
    }

    @Override
    public void sendOrder(Integer orderId) {
        orderDao.sendOrder(orderId);
    }
}
