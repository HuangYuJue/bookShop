package org.book.myssm.filter;

import org.book.myssm.trans.TransactionManager;

import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

//@WebFilter("*.do")
public class OpenSessionInViewFilter implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("访问服务器通过过滤器...");
        try {
            //开启事务
            TransactionManager.beginTrans();
            System.out.println("开启事务...");
            //放行
            filterChain.doFilter(servletRequest,servletResponse);
            //提交事务
            TransactionManager.commitTrans();
            System.out.println("提交事务...");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                //回滚事务
                TransactionManager.rollbackTrans();
                System.out.println("回滚事务...");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
