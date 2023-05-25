package org.book.entity;

import java.util.Date;
import java.util.List;

public class Order {
    private Integer id;
    private Date orderDate;
    private Double orderMoney;
    private String orderNo; //订单编号
    private User orderUser; //*:1
    private Integer orderStatus;//状态：已发货/等待发货

    private List<Detail> detailList;    //1:*

    //用于获取订单中某个订单的总的书本数量
    private Integer bookTotal;

//因为Order是User的外键，所以user中可以获取OrderList，可根据order的id获取单条记录
    //等一系列只需要一个参数的有参构造方法在之后中进行调用
    /**
     * 在 Detail 类中都有参数 Order orderBean;即 Order 中的 id 是 Detail 类的主键，
     * 所以要通过 Order 的 id 来 new 一个 Order，通过单个 Order类的id来构造一个 Order orderBean;
     * 所以需要一个只有一个id为参数的构造方法来构造Order orderBean;Order orderBean = new Order(id);
     * 在外键类中获取Order orderBean就要构造方法构造类。
     */
    public Order(Integer id){
        this.id = id;
    }

    public Order() {
    }
    public Order(Integer id, Date orderDate, Double orderMoney, String orderNo, User orderUser, Integer orderStatus, List<Detail> detailList) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderMoney = orderMoney;
        this.orderNo = orderNo;
        this.orderUser = orderUser;
        this.orderStatus = orderStatus;
        this.detailList = detailList;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public Double getOrderMoney() {
        return orderMoney;
    }
    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public User getOrderUser() {
        return orderUser;
    }
    public void setOrderUser(User orderUser) {
        this.orderUser = orderUser;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }
    public String getStatus(){
        Integer orderStatus = getOrderStatus();
        String status = "";
        if (this.orderStatus == 1){
            status = "已发货";
        }else {
            status = "等待发货";
        }
        return status;
    }
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    public List<Detail> getDetailList() {
        return detailList;
    }
    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }
    public Integer getBookTotal() {
        return bookTotal;
    }
    public void setBookTotal(Integer bookTotal) {
        this.bookTotal = bookTotal;
    }
}
