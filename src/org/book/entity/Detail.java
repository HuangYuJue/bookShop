package org.book.entity;

public class Detail {
    private Integer id;
    private Integer buyCount;
    private Book book;  //*:1
    private Order orderBean;    //*:1

    //因为Detail中的id是book和order表的外键，主键中可以获取detailList，所以主键依照detail的id来查找单个detail而不是detailList
    //等一系列只需要一个参数的有参构造方法在之后中进行调用
    public Detail(Integer id){
        this.id = id;
    }

    public Detail() {
    }
    public Detail(Integer id, Integer buyCount, Book book, Order orderBean) {
        this.id = id;
        this.buyCount = buyCount;
        this.book = book;
        this.orderBean = orderBean;
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
    public Order getOrderBean() {
        return orderBean;
    }
    public void setOrderBean(Order orderBean) {
        this.orderBean = orderBean;
    }
}
