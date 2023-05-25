package org.book.entity;

import java.math.BigDecimal;

public class Cart {
    private Integer id;
    private Integer buyCount;
    private Book book;  //*:1
    private User userBean;  //*:1

    private Double tprice;//price*buyCount
    public Double getTprice() {
        BigDecimal bigDecimalprice = new BigDecimal(""+book.getPrice());
        BigDecimal bigDecimalbuyCount = new BigDecimal(""+buyCount);
        BigDecimal bigDecimaltprice = bigDecimalbuyCount.multiply(bigDecimalprice);
        tprice = bigDecimaltprice.doubleValue();
        return tprice;
    }
    //因为Cart中的id是book和user表的外键，主键中可以获取cartList，所以主键依照cart的id来查找单个cart而不是cartList
    //等一系列只需要一个参数的有参构造方法在之后中进行调用
    public Cart(Integer id){
        this.id = id;
    }

    public Cart(Integer id,Integer buyCount){
        this.id = id;
        this.buyCount = buyCount;
    }

    //用于在后期给需要传值并构造实体类时使用，id可不设置，tprice只可读，所以只需三个参数
    public Cart(Integer buyCount, Book book, User userBean) {
        this.buyCount = buyCount;
        this.book = book;
        this.userBean = userBean;
    }

    public Cart() {
    }
    public Cart(Integer id, Integer buyCount, Book book, User userBean) {
        this.id = id;
        this.buyCount = buyCount;
        this.book = book;
        this.userBean = userBean;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getBuyCount() {
        return buyCount;
    }
    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public User getUserBean() {
        return userBean;
    }
    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }
}
