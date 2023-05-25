package org.book.entity;

import java.util.List;

public class Book {
    private Integer id;
    private String bookImg;
    private String bookName;
    private Double price;
    private String author;
    private Integer saleCount;
    private Integer bookCount;
    private Integer bookStatus = 0;//状态默认值为0表示有效的数据，-1表示无效的数据

    private List<Cart> cartList;    //1:*
    private List<Detail> detailList;    //1:*

    //只需要一个参数的有参构造方法在之后中进行调用
    /**
     * 在 Cart 和 Detail 类中都有参数 Book book;即 Book 中的 id 是那两个类的主键，
     * 所以要通过 Book 的 id 来 new 一个 Book，通过单个 Book类的id来构造一个 Book book;
     * 所以需要一个只有一个id为参数的构造方法来构造Book book;Book book = new Book(id);
     * 在外键类中获取Book book就要构造方法构造类。
     */
    public Book(Integer id){
        this.id = id;
    }

    public Book() {
    }

    public Book(Integer id, String bookImg, String bookName, Double price, String author, Integer saleCount, Integer bookCount) {
        this.id = id;
        this.bookImg = bookImg;
        this.bookName = bookName;
        this.price = price;
        this.author = author;
        this.saleCount = saleCount;
        this.bookCount = bookCount;
    }

    public Book(String bookImg, String bookName, Double price, String author, Integer saleCount, Integer bookCount) {
        this.bookImg = bookImg;
        this.bookName = bookName;
        this.price = price;
        this.author = author;
        this.saleCount = saleCount;
        this.bookCount = bookCount;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }
}
