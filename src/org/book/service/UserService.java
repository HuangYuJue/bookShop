package org.book.service;

import org.book.entity.User;

public interface UserService {
    //登录/个人中心
    User login(String uname, String pwd);

    //注册
    void register(User user);

    //查看用户名是否已存在
    User getName(String uname);

    //修改用户
    void editUser(Integer id,String uname,String email);

    //修改密码
    void editPwd(Integer id,String pwd);

    //按id查找用户
    User selectById(Integer id);
}