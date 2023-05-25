package org.book.myssm.trans;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    //开启事务
    public static void beginTrans() throws SQLException {
        //获取到connection对象
        Connection conn = ConnUtil.getConn();
        conn.setAutoCommit(false);//设置为手动提交
    }
    //提交事务
    public static void commitTrans() throws SQLException {
        //获取到connection对象
        Connection conn = ConnUtil.getConn();
        //提交事务
        conn.commit();
        //提交完成后可以把connection关闭
        ConnUtil.closeConn();
    }
    //回滚事务
    public static void rollbackTrans() throws SQLException {
        //获取到connection对象
        Connection conn = ConnUtil.getConn();
        //回滚事务
        conn.rollback();
        //回滚之后关闭connection
        ConnUtil.closeConn();
    }
}
