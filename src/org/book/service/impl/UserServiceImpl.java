package org.book.service.impl;

import org.book.dao.UserDao;
import org.book.service.UserService;
import org.book.entity.User;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    @Override
    public User login(String uname, String pwd) {
        return userDao.getUser(uname, pwd);
    }

    @Override
    public void register(User user) {
        userDao.addUser(user);
    }

    @Override
    public User getName(String uname){
        return userDao.getName(uname);
    }

    @Override
    public void editUser(Integer id,String uname, String email) {
        userDao.editUser(id,uname,email);
    }

    @Override
    public void editPwd(Integer id, String pwd) {
        userDao.editPwd(id,pwd);
    }

    @Override
    public User selectById(Integer id) {
        return userDao.selectById(id);
    }
}
