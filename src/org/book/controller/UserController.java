package org.book.controller;
import com.google.gson.Gson;
import org.book.entity.CartMap;
import org.book.service.CartService;
import org.book.service.UserService;
import org.book.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UserController {
    private UserService userService;
    private CartService cartService;
    //登录
    public String login(String uname, String pwd, String remember, HttpSession session, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        //登陆之前先会查cookie
        getCookie(req,resp);
        User user = userService.login(uname, pwd);
        if (user != null){
            //获取cartMap
            CartMap cartMap = cartService.getCartMap(user);
            user.setCartMap(cartMap);
            if ("remember".equals(remember)){
                //cookie不能直接存储中文，如需存储，则需要进行转码：URL编码
                uname = URLEncoder.encode(uname, "utf-8");
                pwd = URLEncoder.encode(pwd, "utf-8");
                //创建一个cookie对象
                Cookie un_cookie = new Cookie("uname",uname);
                Cookie p_cookie = new Cookie("pwd",pwd);
                //设置存活时间：30min
                un_cookie.setMaxAge(60*1);
                p_cookie.setMaxAge(60*1);
                //将cookie保存在浏览器端
                resp.addCookie(un_cookie);
                resp.addCookie(p_cookie);
            }
            //登录成功之后的user对象存储在session中
            session.setAttribute("user",user);
            if (user.getRole() == 0){
                return "redirect:book.do?operate=index";
            }else if (user.getRole() == 1){
                return "manager/manager";
            }
        }
        return "user/login";
    }
    //查找指定的cookie对象
    public void getCookie(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        Cookie uname = findCookie("uname", cookies);
        Cookie pwd = findCookie("pwd", cookies);
        System.out.println("uname:"+uname+",pwd:"+pwd);
        if (uname != null || pwd != null){
            String unameValue = uname.getValue();
            String pwdValue = pwd.getValue();
            request.getSession().setAttribute("uname",unameValue);
            request.getSession().setAttribute("pwd",pwdValue);
        }
    }
    //声明一个方法，用来查找指定名称的cookie对象
    //如果返回null，表示没有这个名称对应的Cookie对象，否则就是找到了指定名称的cookie对象
    public Cookie findCookie(String name,Cookie[] cookies){
        if (name == null || cookies == null || cookies.length == 0){
            return null;
        }
        for (Cookie cookie : cookies){
            //getName方法返回Cookie的key
            //getValue方法返回Cookie的value
            if (name.equals(cookie.getName())){
                return cookie;
            }
        }
        return null;
    }

    //注册
    public String register(String verifyCode,String uname,String pwd,String email,String code,HttpSession session,HttpServletResponse response) throws IOException {
        Object kaptchaCodeObj = session.getAttribute("KAPTCHA_SESSION_KEY");
        if (kaptchaCodeObj == null || !verifyCode.equals(kaptchaCodeObj)){
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script language='javascript'>alert('验证码不正确！');</script>");
            return "user/register";
        }
        else {
            if (verifyCode.equals(kaptchaCodeObj)){
                userService.register(new User(uname,pwd,email,0));
                return "user/login";
            }
        }
        return "user/login";
    }
    public String ckUname(String uname){
        User user = userService.getName(uname);
        if (user != null){
            //用户名已存在，不可以注册
            return "json:{'uname':'1'}";
            //return "ajax:1";
        }else {
            //用户名不存在，可以注册
            return "json:{'uname':'0'}";
            //return "ajax:0";
        }
    }

    //修改用户
    public String editUser(Integer id,String newuname,String pwd,HttpSession session){
        userService.editUser(id,newuname,pwd);
        User user = userService.login(newuname, pwd);
        session.setAttribute("user",user);
        return "user/person";
    }

    //修改密码
    public String editPwd(Integer id,String newpwd,HttpSession session){
        userService.editPwd(id, newpwd);
        User user = userService.selectById(id);
        session.setAttribute("user",user);
        return "user/person";
    }

    //注销
    public String exit(HttpSession session){
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:book.do?operate=index";
    }
}
