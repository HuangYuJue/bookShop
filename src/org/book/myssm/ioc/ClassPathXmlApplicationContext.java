package org.book.myssm.ioc;

import org.book.myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory{
    //定义一键值对集合容器
    private Map<String,Object> map = new HashMap<>();
/*    //定义资源文件
    private String resource = "applicationContext.xml";*/

    public ClassPathXmlApplicationContext(String resource){
        try {
            if (StringUtil.isEmpty(resource)){
                throw new RuntimeException("IOC容器的配置文件没有指定...");
            }
            //获取资源文件的输入流（把要解析的 XML 文档转化为输入流，以便 DOM 解析器解析它）
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
            //调用 DocumentBuilderFactory.newInstance() 方法得到创建 DOM 解析器的工厂。
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //调用工厂对象的 newDocumentBuilder方法得到 DOM 解析器对象。
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //调用 DOM 解析器对象的 parse() 方法解析 XML 文档，得到代表整个文档的 Document 对象，进行可以利用DOM特性对整个XML文档进行操作了。
            Document document = documentBuilder.parse(inputStream);
            //通过标签名查询标签DOM对象
            NodeList nodeList = document.getElementsByTagName("bean");
            for (int i = 0;i < nodeList.getLength();i++){
                //获取nodeList中的第i个元素
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    //根据属性名称获取节点的属性
                    String id = element.getAttribute("id");
                    String aClass = element.getAttribute("class");
                    Object instance = Class.forName(aClass).newInstance();
                    //存入map中
                    map.put(id,instance);
                }
            }
            for (int i = 0;i < nodeList.getLength();i++){
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    //获取id
                    String id = element.getAttribute("id");
                    //获取元素的子节点
                    NodeList childNodes = element.getChildNodes();
                    for (int j = 0;j < childNodes.getLength();j++){
                        Node childNode = childNodes.item(j);
                        //判断是否是元素节点并且名称为property
                        if (childNode.getNodeType() == Node.ELEMENT_NODE && "property".equals(childNode.getNodeName())){
                            Element childElement = (Element) childNode;
                            String name = childElement.getAttribute("name");
                            String ref = childElement.getAttribute("ref");
                            //通过ref(下一个ref = 上一个id，此层的ref对应上一层的id)获取Object
                            Object refObj = map.get(ref);
                            //通过id获取到Object(此层的id对应下一层的ref)
                            Object beanObj = map.get(id);
                            Class beanObjClass = beanObj.getClass();
                            //根据name的内容找到类中对应的属性(字段)
                            Field declaredField = beanObjClass.getDeclaredField(name);
                            declaredField.setAccessible(true);
                            //给Class对象的属性设置值
                            declaredField.set(beanObj,refObj);
                            System.out.println("declaredField是："+declaredField);
                            //declaredField是：private org.book.ssm.mybatis.dao.UserDao org.book.ssm.spring.service.impl.UserServiceImpl.userDao
                            //declaredField是：private org.book.ssm.spring.service.UserService org.book.ssm.springmvc.controller.UserController.userService
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String id) {
        return map.get(id);
    }
}
