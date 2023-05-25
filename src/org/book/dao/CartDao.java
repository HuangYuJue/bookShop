package org.book.dao;

import org.book.entity.Cart;
import org.book.entity.User;

import java.util.List;

public interface CartDao {
    //查询所有的购物车项
    List<Cart> getCartList(User user);

    //添加一条购物车栏
    void addCart(Cart cart);

    //修改购物车栏中书本数量
    void updateCart(Cart cart);

    //移除购物车
    void delCart(Cart cart);

    //清空购物车
    void delAllCart(Integer userId);
}
