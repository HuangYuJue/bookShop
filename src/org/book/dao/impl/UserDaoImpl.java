package org.book.dao.impl;

import org.book.dao.UserDao;
import org.book.myssm.basedao.DaoConn;
import org.book.entity.User;

public class UserDaoImpl extends DaoConn<User> implements UserDao {
    @Override
    public User getUser(String uname, String pwd) {
        return super.getOne("select * from user where uname like ? and pwd like ?",uname,pwd);
    }

    @Override
    public void addUser(User user) {
        super.executeUpdate("insert into user(uname,pwd,email,role) value(?,?,?,?)",user.getUname(),user.getPwd(),user.getEmail(),user.getRole());
    }

    @Override
    public User getName(String uname){
        return super.getOne("select * from user where uname like ?",uname);
    }

    @Override
    public void editUser(Integer id,String uname, String email) {
        super.executeUpdate("update user set uname = ?,email = ? where id = ?",uname,email,id);
    }

    @Override
    public void editPwd(Integer id, String pwd) {
        super.executeUpdate("update user set pwd = ? where id = ?",pwd,id);
    }

    @Override
    public User selectById(Integer id) {
        return super.getOne("select * from user where id = ?",id);
    }
}
