package org.book.myssm.trans;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static Connection createConn(){
        try {
            //创建Properties对象，properties类就是map集合的实现类
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("E:\\idea项目\\book\\src\\jdbc.properties");
            prop.load(fis);
            //获取连接池对象
            DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
            //获取数据库连接Connection
            Connection connection = dataSource.getConnection();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConn(){
        //从threadLocal中获取到Connection
        Connection conn = threadLocal.get();
        //如果threadLocal中获取不到
        if (conn == null){
            //则要另外获取
            conn = createConn();
            //将connection放在threadLocal中
            threadLocal.set(conn);
        }
        return conn;
    }

    public static void closeConn() throws SQLException {
        //从threadLocal中获取到Connection
        Connection conn = threadLocal.get();
        //如果threadLocal中获取不到
        if (conn == null){
            //则不需要做任何处理
            return;
        }
        //如果能获取到，并且没关闭，则需要关闭connection，并赋值connection为null
        if (!conn.isClosed()){
            conn.close();
            threadLocal.set(null);
        }
    }
}
