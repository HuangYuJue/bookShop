package org.book.dao.impl;

import org.book.myssm.basedao.DaoConn;
import org.book.dao.CartDao;
import org.book.entity.Cart;
import org.book.entity.User;

import java.util.List;

public class CartDaoImpl extends DaoConn<Cart> implements CartDao {
    @Override
    public List<Cart> getCartList(User user) {
        return super.getAll("select * from cart where userBean = ?",user.getId());
    }

    @Override
    public void addCart(Cart cart) {
        super.executeUpdate("insert into cart(book,buyCount,userBean) value(?,?,?)",cart.getBook().getId(),cart.getBuyCount(),cart.getUserBean().getId());
    }

    @Override
    public void updateCart(Cart cart) {
        super.executeUpdate("update cart set buyCount = ? where id = ?",cart.getBuyCount(),cart.getId());
    }

    @Override
    public void delCart(Cart cart) {
        super.executeUpdate("delete from cart where id = ?", cart.getId());
    }

    @Override
    public void delAllCart(Integer userId) {
        super.executeUpdate("delete from cart where userBean = ?",userId);
    }
}
