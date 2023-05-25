package org.book.service.impl;

import org.book.dao.CartDao;
import org.book.service.CartService;
import org.book.service.BookService;
import org.book.entity.Book;
import org.book.entity.Cart;
import org.book.entity.CartMap;
import org.book.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServiceImpl implements CartService {
    private CartDao cartDao;
    private BookService bookService;
    @Override
    public List<Cart> getCartList(User user) {
        List<Cart> cartList = cartDao.getCartList(user);
        //遍历购物车list
        for (Cart cart : cartList){
            //获取cart中的book
            Book book = cart.getBook();
            //通过book获取id，再通过id获取到book内容
            book = bookService.getBook(book.getId());
            //将 book 的数据传进Cart中（即可通过Cart获取到Book中的各参数值）
            cart.setBook(book);
            //此处需要调用getTprice()，目的是执行getTprice()内部的代码，让book的price乘以buyCount，从而计算出tprice这个属性的值。
            // 之前不用是因为thymeleaf服务器的视图渲染技术，还是归属于服务器端，在调用执行获取小计的时候会执行get方法，
            // 而前后端分离之后，后端未获取到小计时显示为null，因为没调用getTprice方法，所以前端获取自动不添加进Object实体中。
            cart.getTprice();
        }
        return cartList;
    }

    //获取实体类CartMap
    @Override
    public CartMap getCartMap(User user) {
        //获取到数据库中购物车列表
        List<Cart> cartList = getCartList(user);
        //自定义购物车集合
        Map<Integer,Cart> map = new HashMap<>();
        //遍历购物车列表
        for (Cart cart : cartList){
            //将购物车循环加入集合中，以book的id为key，cart实体为value
            map.put(cart.getBook().getId(),cart);
        }
        //无参构造方法初始化CartMap实体类
        CartMap cartMap = new CartMap();
        //设置参数map
        cartMap.setCartMap(map);
        return cartMap;
    }

    @Override
    public void addCart(Cart cart) {
        cartDao.addCart(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        cartDao.updateCart(cart);
    }

    @Override
    public void addOrUpdate(Cart cart, CartMap cartMap) {
        //查看购物车键值对map是否为空
        if (cartMap != null){
            //获取购物车集合中的键值对集合
            Map<Integer, Cart> map = cartMap.getCartMap();
            //购物车不为空，再查看购物车栏键值对是否为空
            if (map == null){
                //购物车栏为空，实例化一个购物车栏用于存储数据集
                map = new HashMap<>();
            }
            //购物车栏不为空，查看购物车集合键值对中的键是否包含所输入的书本id(因为键值对的key是book的id)
            if (map.containsKey(cart.getBook().getId())){
                //键值对集合的键包含书本的id，则根据书本的id为键值对的键来获取值(Cart)
                Cart cartNow = map.get(cart.getBook().getId());
                //获取到cart的buyCount
                Integer buyCount = cartNow.getBuyCount();
                //设置buyCount+1
                cartNow.setBuyCount(buyCount+1);
                updateCart(cartNow);
            }else {
                //购物车栏中不包含这个键
                addCart(cart);
            }
        }else {
            //购物车为空
            Map<Integer,Cart> map = new HashMap<>();
            addCart(cart);
        }
    }

    @Override
    public void delCart(Cart cart) {
        cartDao.delCart(cart);
    }

    @Override
    public void delAllCart(Integer userId) {
        cartDao.delAllCart(userId);
    }
}
