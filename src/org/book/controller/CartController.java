package org.book.controller;
import com.google.gson.Gson;
import org.book.entity.CartMap;
import org.book.service.CartService;
import org.book.entity.Book;
import org.book.entity.Cart;
import org.book.entity.User;

import javax.servlet.http.HttpSession;

public class CartController {
    private CartService cartService;
    public String cart(HttpSession session){
        //通过session获取当前用户
        User user = (User) session.getAttribute("user");
        CartMap cartMap = cartService.getCartMap(user);
        user.setCartMap(cartMap);
        session.setAttribute("user",user);
        return "cart/cart";
    }
    //加入购物车
    public String addCart(Integer bookId,HttpSession session){
        User user = (User) session.getAttribute("user");
        //需要获取一个Cart
        //1.利用id参构造方法获取到 Book
        Book book = new Book(bookId);
        //2.Cart实体类中有四个可设置值的参数，而id可不赋值，所以只需定义一个有其他三个参数的构造方法
        Cart cart = new Cart(1,book,user);
        cartService.addOrUpdate(cart,user.getCartMap());
        return "redirect:book.do?operate=index";
    }
    //购物车页面count的加减
    public String updateCart(Integer cartId,Integer buyCount){
        Cart cart = new Cart(cartId,buyCount);
        cartService.updateCart(cart);
        return "redirect:cart.do?operate=cart";
    }
    //删除购物车
    public String delCart(Integer cartId){
        Cart cart = new Cart(cartId);
        cartService.delCart(cart);
        return "redirect:cart.do?operate=cart";
    }
    //清空购物车
    public String delAllCart(Integer userId){
        cartService.delAllCart(userId);
        return "redirect:cart.do?operate=cart";
    }
    /*public String cartInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        CartMap cartMap = cartService.getCartMap(user);
        //调用Cart中的三个属性的get方法，目的是在此处计算这三个属性的值，否则这三个属性为null，
        //导致的结果就是下一步的gson转化时，为null的属性会被忽略
        cartMap.getTotalBook();
        cartMap.getTotalPrice();
        cartMap.getTotalCart();
        Gson gson = new Gson();
        String toJson = gson.toJson(cartMap);
        return "json:"+toJson;
    }*/
}
