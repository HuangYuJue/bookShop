package org.book.service;

import org.book.entity.Cart;
import org.book.entity.CartMap;
import org.book.entity.User;

import java.util.List;

public interface CartService {
    //获取所有的购物车数据
    List<Cart> getCartList(User user);

    //获取数据库中没有的实体类CartMap的数据
    CartMap getCartMap(User user);

    //增加购物车栏
    void addCart(Cart cart);

    //修改购物车栏的书本数量
    void updateCart(Cart cart);

    //判断是增加购物车栏还是修改购物车栏，并执行
    void addOrUpdate(Cart cart,CartMap cartMap);

    //删除购物车
    void delCart(Cart cart);

    //清空购物车
    void delAllCart(Integer userId);
}
