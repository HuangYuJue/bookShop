package org.book.myssm.myspringmvc;

import org.book.myssm.ioc.BeanFactory;
import org.book.myssm.util.StringUtil;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet("*.do")
public class DispatcherServlet extends ViewServlet {
    //定义beanFactory
    private BeanFactory beanFactory;
    @Override
    public void init() {
        super.init();
        //获取上下文，即可获取beanFactory(ContextLoaderListener中已保存了beanFactory在application中)
        ServletContext servletContext = getServletContext();
        Object beanFactoryObj = servletContext.getAttribute("beanFactory");
        if (beanFactoryObj != null){
            beanFactory = (BeanFactory)beanFactoryObj;
        }else {
            throw new RuntimeException("IOC容器获取失败...");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        //获取/....do
        String servletPath = req.getServletPath();
        //截取/
        servletPath = servletPath.substring(1);
        int index = servletPath.lastIndexOf(".do");
        //截取.do
        servletPath = servletPath.substring(0,index);
        //赋值id
        Object bean = beanFactory.getBean(servletPath);
        //获取参数，执行方法
        String operate = req.getParameter("operate");
        if (StringUtil.isEmpty(operate)){
            operate = "index";
        }
        //获取controller类对象中所有的方法
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        for (Method method : declaredMethods){
            //获取方法名称
            String methodName = method.getName();
            //判断方法名称与operate参数是否一致
            if (operate.equals(methodName)){
                //1.接收数据
                /**
                 * 注意要配置method.getParameters()才有用，理由：
                 * 默认情况下，JDK8(1.8)的javac编译后的.class⽂件是不保留⽅法的参数名，参数名是以arg0，arg1，arg3…表⽰。
                 * 如果要保留参数名，需要给javac添加参数-parameters开启。开启步骤：第⼀步：通过设置File-->Settings-->Build,Execution,Deployment
                 * -->Compiler-->Java Compiler中设置Additional command line parameters -> 设置为 -parameters 。
                 * 填好之后再将项目build一下，Build -> Rebuild Project。
                 */
                Parameter[] parameters = method.getParameters();
                //定义一个对象组用于存储数据
                Object[] parameterValues = new Object[parameters.length];
                //循环每一个参数
                for (int i = 0;i < parameters.length;i++){
                    Parameter parameter = parameters[i];
                    System.out.println("参数："+parameter);
                    //获取参数名
                    String parameterName = parameter.getName();
                    System.out.println("参数名："+parameterName);
                    if ("req".equals(parameterName)){
                        parameterValues[i] = req;
                    }else if ("resp".equals(parameterName)){
                        parameterValues[i] = resp;
                    }else if ("session".equals(parameterName)){
                        parameterValues[i] = req.getSession();
                    }else {
                        //获取参数的值，默认为String类型
                        String parameterValue = req.getParameter(parameterName);
                        System.out.println("parameterValue："+parameterValue);
                        //获取参数类型名
                        String type = parameter.getType().getName();
                        //转换参数类型
                        Object parameterObj = parameterValue;
                        //参数为null时不能转换
                        if (parameterObj != null){
                            //为Integer类型时将其转换为Integer类型
                            if ("java.lang.Integer".equals(type)){
                                parameterObj = Integer.parseInt(parameterValue);
                            }else if ("float".equals(type)){
                                parameterObj = Float.parseFloat(parameterValue);
                            }else if ("java.lang.Double".equals(type)){
                                parameterObj = Double.valueOf(parameterValue);
                            }/*else if ("java.io.File".equals(type)){
                                System.out.println("文件类型是："+type);
                                File file = new File(parameterValue);
                                String fileName = file.getName();//获取文件名
                                parameterObj = fileName;
                            }*/
                        }
                        parameterValues[i] = parameterObj;
                    }
                }
                //2.利用反射技术调用方法
                try {
                    Object invoke = method.invoke(bean, parameterValues);
                    //将结果从Object强转为String
                    String renturninvoke = (String) invoke;
                    System.out.println("returnInvoke:"+renturninvoke);
                    //3.视图处理
                    if (renturninvoke.startsWith("redirect:")){
                        //截取掉“redirect:”
                        renturninvoke = renturninvoke.substring("redirect:".length());
                        //进行重定向
                        resp.sendRedirect(renturninvoke);
                    }else if (renturninvoke.startsWith("json:")){
                        String jsonStr = renturninvoke.substring("json:".length());
                        PrintWriter writer = resp.getWriter();
                        writer.print(jsonStr);
                        writer.flush();
                    }else {
                        super.processTemplate(renturninvoke,req,resp);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
