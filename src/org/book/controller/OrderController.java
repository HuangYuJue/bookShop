package org.book.controller;

import org.book.service.OrderService;
import org.book.entity.Order;
import org.book.entity.User;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderController {
    private OrderService orderService;
    //添加订单
    public String addOrder(HttpSession session) throws ParseException {
        //初始化封装 Order
        Order order = new Order();
        //定义时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        System.out.println("dateStr："+dateStr);
        date = sdf.parse(dateStr);
        System.out.println("date："+date);
        //String[] strings = dateStr.split("-");//将”-“进行分割，可以将里面的值取出来
        //定义全球唯一编码，后面加上具体时间
        Date date1 = new Date();
        int year = date1.getYear();
        int month = date1.getMonth();
        int day = date1.getDate();
        int hours = date1.getHours();
        int minutes = date1.getMinutes();
        int seconds = date1.getSeconds();
        String string = UUID.randomUUID().toString()+"_"+year+month+day+hours+minutes+seconds;
        //获取订单用户
        User user = (User) session.getAttribute("user");
        //获取订单价钱
        Double totalPrice = user.getCartMap().getTotalPrice();
        if (totalPrice == 0){
            return "cart/cart";
        }
        //传入order中
        order.setOrderDate(date);
        order.setOrderNo(string);
        order.setOrderMoney(totalPrice);
        order.setOrderUser(user);
        order.setOrderStatus(0);//默认设置为0
        //调用方法
        orderService.addOrder(order);
        return "redirect:order.do?operate=getOrderList";
    }

    //根据用户名获取订单
    public String getOrderList(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Order> orderList = orderService.getOrderList(user);
        user.setOrderList(orderList);
        session.setAttribute("user",user);
        return "order/order";
    }

    //删除订单
    public String delOrder(Integer orderId){
        orderService.delOrder(new Order(orderId));
        return "redirect:order.do?operate=getOrderList";
    }

    //获取所有订单
    public String getAllOrder(HttpSession session){
        List<Order> allOrder = orderService.getAllOrder();
        session.setAttribute("allOrder",allOrder);
        return "manager/order_manager";
    }

    //发货
    public String sendOrder(Integer orderId){
        orderService.sendOrder(orderId);
        return "redirect:order.do?operate=getAllOrder";
    }
}
