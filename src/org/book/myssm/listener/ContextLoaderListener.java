package org.book.myssm.listener;

import org.book.myssm.ioc.BeanFactory;
import org.book.myssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*监听上下文启动，在上下文启动的时候去创建ioc容器，然后将其保存到application(servletContext)作用域
    之后中央控制器再从application作用域中去获取ioc容器
 */
//@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //获取ServletContext对象
        ServletContext servletContext = servletContextEvent.getServletContext();
        //利用有参构造方法
        //获取web.xml中上下文的初始化参数（applicationContext.xml）
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        //创建IOC容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(contextConfigLocation);
        //将beanFactory保存到application(servletContext)中
        servletContext.setAttribute("beanFactory",beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
