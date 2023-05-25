package org.book.dao;

import org.book.entity.User;

public interface UserDao {
    //按用户名及密码查询
    User getUser(String uname,String pwd);

    //添加用户
    void addUser(User user);

    //依照用户名获取用户
    User getName(String uname);

    //修改用户名及邮箱
    void editUser(Integer id,String uname,String email);

    //修改密码
    void editPwd(Integer id,String pwd);

    //按id查找用户
    User selectById(Integer id);
}
