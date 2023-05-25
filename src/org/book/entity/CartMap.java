package org.book.entity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

//此实体类用于存储购物车的集合，并计算购物车的总价钱以及购物车的栏数，主要用于计算
public class CartMap {
    private Map<Integer,Cart> cartMap ; //保存购物车集合，其中key是book的id，因为通过book的id来查询
    private Double totalPrice;//购物车中总价钱
    private Integer totalCart;//总的购物车栏
    private Integer totalBook;//总的书本数量

    public CartMap() {
    }
    public Map<Integer, Cart> getCartMap() {
        return cartMap;
    }
    public void setCartMap(Map<Integer, Cart> cartMap) {
        this.cartMap = cartMap;
    }
    public Double getTotalPrice() {
        totalPrice = 0.0;
        //如果购物车集合不为空，且购物车集合的大小大于0
        if (cartMap != null && cartMap.size()>0){
            Set<Map.Entry<Integer, Cart>> entries = cartMap.entrySet();
            for (Map.Entry<Integer,Cart> entry : entries){
                //获取每一栏购物车
                Cart value = entry.getValue();
                //获取每一栏书本数量
                Integer buyCount = value.getBuyCount();
                //获取书本价格
                double price = value.getBook().getPrice();
                //精确
                BigDecimal bigDecimalPrice = new BigDecimal("" + price);
                BigDecimal bigDecimalBuyCount = new BigDecimal("" + buyCount);
                BigDecimal multiply = bigDecimalPrice.multiply(bigDecimalBuyCount);
                double doubleValue = multiply.doubleValue();
                totalPrice = totalPrice + doubleValue;
            }
        }
        return totalPrice;
    }
    public Integer getTotalCart() {
        totalCart = 0;
        if (cartMap != null && cartMap.size() > 0){
            //直接计算购物车集合的长度
            totalCart = cartMap.size();
        }
        return totalCart;
    }
    public Integer getTotalBook() {
        totalBook = 0;
        if (cartMap != null && cartMap.size() > 0){
            //直接获取购物车集合的所有值(即键值对中的”值“)
            Collection<Cart> values = cartMap.values();
            for (Cart cart : values){
                //获取每一个购物车栏中的购买数量
                Integer buyCount = cart.getBuyCount();
                totalBook = totalBook + buyCount;
            }
        }
        return totalBook;
    }
}
