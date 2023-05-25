package org.book.entity;

import java.util.List;

public class User {
    private Integer id;
    private String uname;
    private String pwd;
    private String email;
    private Integer role;

    private List<Cart> cartList;    //1:*
    private List<Order> orderList;  //1:*

    private CartMap cartMap;

    //只需要一个参数的有参构造方法在之后中进行调用
    /**
     * 在 Order 和 Cart 类中都有参数 User userBean;即 User 中的 id 是那两个类的主键，
     * 所以要通过 User 的 id 来 new 一个 User，通过单个 User类的id来构造一个 User userBean;
     * 所以需要一个只有一个id为参数的构造方法来构造User userBean;User userBean = new User(id);
     * 在外键类中获取User userBean就要构造方法构造类。
     */
    public User(Integer id){
        this.id=id;
    }

    public User() {
    }

    public User(String uname, String pwd, String email, Integer role) {
        this.uname = uname;
        this.pwd = pwd;
        this.email = email;
        this.role = role;
    }

    public User(Integer id, String uname, String pwd, String email, Integer role, List<Cart> cartList, List<Order> orderList) {
        this.id = id;
        this.uname = uname;
        this.pwd = pwd;
        this.email = email;
        this.role = role;
        this.cartList = cartList;
        this.orderList = orderList;
    }

    public CartMap getCartMap() {
        return cartMap;
    }

    public void setCartMap(CartMap cartMap) {
        this.cartMap = cartMap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}