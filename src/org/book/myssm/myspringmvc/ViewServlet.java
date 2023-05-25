package org.book.myssm.myspringmvc;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewServlet extends HttpServlet {
    //定义TemplateEngine模板引擎
    private TemplateEngine templateEngine;

    @Override
    public void init() {
        //获取ServletContext对象
        ServletContext servletContext = this.getServletContext();
        //创建Thymeleaf解析器对象
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        //给解析器对象设置参数,HTML是默认模式
        templateResolver.setTemplateMode(TemplateMode.HTML);
        String viewPrefix = servletContext.getInitParameter("view-prefix");
        templateResolver.setPrefix(viewPrefix);
        String viewSuffix = servletContext.getInitParameter("view-suffix");
        templateResolver.setSuffix(viewSuffix);
        //设置缓存过期时间
        templateResolver.setCacheTTLMs(60000L);
        //设置是否缓存
        templateResolver.setCacheable(true);
        //设置服务器编码方式
        templateResolver.setCharacterEncoding("utf-8");
        //创建模板引擎对象
        templateEngine = new TemplateEngine();
        //给模板引擎对象设置模板解析器
        templateEngine.setTemplateResolver(templateResolver);
    }
    protected void processTemplate(String templateName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应体内容类型和字符集
        response.setContentType("text/html;charset=utf-8");
        //创建WebContext对象
        WebContext webContext = new WebContext(request,response,getServletContext());
        //处理模板数据
        templateEngine.process(templateName,webContext,response.getWriter());
    }
}
