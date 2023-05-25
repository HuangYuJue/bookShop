package org.book.myssm.basedao;

import org.book.myssm.trans.ConnUtil;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoConn<T> {
    private static Connection conn = null;
    private PreparedStatement psmt;
    private ResultSet rs;
    private Class entityClass;
    public DaoConn(){
        //getClass()获取Class类，即子类的Class，getClass().getGenericSuperclass();即获取子类的父类的Class，即本类。
        Type superclass = getClass().getGenericSuperclass();
        //ParameterizedType参数化类型
        Type[] actualTypeArguments = ((ParameterizedType) superclass).getActualTypeArguments();
        //获取到的<T>中的真实的类型(获取泛型里面的实际参数类型)
        Type actualTypeArgument = actualTypeArguments[0];
        try {
            //获取<T>的全类名并根据类路径获取class文件
            entityClass = Class.forName(actualTypeArgument.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //给预处理命令对象设置参数
    public void setParam(PreparedStatement psmt,Object...params) throws SQLException {
        if (params != null && params.length > 0){
            for (int i = 0;i < params.length;i++){
                psmt.setObject(i+1,params[i]);
            }
        }
    }
    //通过反射技术给obj对象的property属性赋propertyValue值
    private void setValue(Object obj,String property,Object propertyValue){
        //obj.getClass()获取运行时对象的类
        Class aClass = obj.getClass();
        try {
            //获取obj中property对应属性
            Field field = aClass.getDeclaredField(property);
            if (field != null){
                String typeName = field.getType().getName();
                if (isMyType(typeName)){
                    //获取类
                    Class forName = Class.forName(typeName);
                    //获取类中构造方法
                    Constructor constructor = forName.getDeclaredConstructor(Integer.class);
                    //根据构造方法创建实例
                    propertyValue = constructor.newInstance(propertyValue);
                }
                field.setAccessible(true);
                field.set(obj,propertyValue);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    public boolean isNotMyType(String type){
        return "java.lang.Integer".equals(type)
                || "java.lang.String".equals(type)
                || "java.lang.Double".equals(type)
                || "java.util.Date".equals(type);
    }
    public boolean isMyType(String type){
        return !isNotMyType(type);
    }
    //单条查询
    public T getOne(String sql,Object...params){
        try {
            //获取conn
            conn = ConnUtil.createConn();
            //预处理给定的sql语句
            psmt = conn.prepareStatement(sql);
            //设置参数
            setParam(psmt,params);
            //执行查询语句
            rs = psmt.executeQuery();
            //获取结果集元数据
            ResultSetMetaData metaData = rs.getMetaData();
            System.out.println("结果集元数据："+metaData);
            /*
            结果集元数据：
    com.mysql.cj.jdbc.result.ResultSetMetaData@1d73bd57 - Field level information:
	com.mysql.cj.result.Field@20bc2d55[dbName=book,tableName=user,originalTableName=user,columnName=id,originalColumnName=id,mysqlType=3(FIELD_TYPE_INT),sqlType=4,flags= AUTO_INCREMENT PRIMARY_KEY, charsetIndex=63, charsetName=ISO-8859-1]
	com.mysql.cj.result.Field@7bed5c60[dbName=book,tableName=user,originalTableName=user,columnName=uname,originalColumnName=uname,mysqlType=253(FIELD_TYPE_VARCHAR),sqlType=12,flags= UNIQUE_KEY, charsetIndex=33, charsetName=utf8]
	com.mysql.cj.result.Field@3b932582[dbName=book,tableName=user,originalTableName=user,columnName=pwd,originalColumnName=pwd,mysqlType=253(FIELD_TYPE_VARCHAR),sqlType=12,flags=, charsetIndex=33, charsetName=utf8]
	com.mysql.cj.result.Field@3b8aaea2[dbName=book,tableName=user,originalTableName=user,columnName=email,originalColumnName=email,mysqlType=253(FIELD_TYPE_VARCHAR),sqlType=12,flags=, charsetIndex=33, charsetName=utf8]
	com.mysql.cj.result.Field@7a1dfa03[dbName=book,tableName=user,originalTableName=user,columnName=role,originalColumnName=role,mysqlType=3(FIELD_TYPE_INT),sqlType=4,flags=, charsetIndex=63, charsetName=ISO-8859-1]
             */
            //获取元数据的列数
            int columnCount = metaData.getColumnCount();
            //解析rs
            if (rs.next()){
                T entity = (T)entityClass.newInstance();
                for (int i=0;i<columnCount;i++){
                    //获取指定列的名称(属性)
                    String columnName = metaData.getColumnName(i + 1);
                    //获取指定列名称分别对应的值(属性值)
                    Object columnValue = rs.getObject(i + 1);
                    System.out.println("DaoConn属性值："+columnValue);
                    //利用反射技术给属性赋值
                    setValue(entity,columnName,columnValue);
                }
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    //多条查询
    public List<T> getAll(String sql,Object...params){
        //初始化集合
        List<T> list = new ArrayList<>();
        try {
            //获取conn
            conn=ConnUtil.createConn();
            //预处理sql语句
            psmt=conn.prepareStatement(sql);
            //设置参数
            setParam(psmt,params);
            //执行查询
            rs= psmt.executeQuery();
            //获取结果集元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取结果集列数
            int columnCount = metaData.getColumnCount();
            //解析rs
            while (rs.next()){
                T entity = (T)entityClass.newInstance();
                for (int i=0;i<columnCount;i++){
                    //获取元数据的属性名
                    String columnName = metaData.getColumnName(i + 1);
                    //获取元数据对应的值
                    Object columnValue = rs.getObject(i + 1);
                    //设置参数
                    setValue(entity,columnName,columnValue);
                }
                //将每个实体都加入集合
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
    //增删改
    public Integer executeUpdate(String sql,Object...params){
        boolean insertFlag = false;
        //将sql语句的前后空格删去后将其转为大写并查看开始字符串是insert
        insertFlag = sql.trim().toUpperCase().startsWith("INSERT");
        //获取conn
        conn = ConnUtil.createConn();
        try {
            //判断insertFlag是否为true，即是否是插入语句
            if (insertFlag) {
                //预处理，获取刚刚插入的自增id，并可利用psmt.getGeneratedKeys()返回id
                psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            }else {
                //预处理
                psmt=conn.prepareStatement(sql);
            }
            //设置参数
            setParam(psmt,params);
            //执行更新
            int update = psmt.executeUpdate();
            if (insertFlag){
                //获取id
                rs = psmt.getGeneratedKeys();
                if (rs.next()){
                    //rs.getLong(1)：返回结果集第一列的值，并将改Long类型转换为int类型
                    return ((Long)rs.getLong(1)).intValue();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //复杂查询
    public Object[] getComplexQuery(String sql,Object...params){
        try {
            //获取conn
            conn = ConnUtil.createConn();
            //预处理
            psmt = conn.prepareStatement(sql);
            //设置参数
            setParam(psmt,params);
            //执行
            rs = psmt.executeQuery();
            //获取结果集元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取元数据列数
            int columnCount = metaData.getColumnCount();
            //初始化
            Object[] columnValueArr = new Object[columnCount];
            //解析rs
            if (rs.next()){
                for (int i=0;i<columnCount;i++){
                    //获取结果集的元数据值
                    Object columnValue = rs.getObject(i + 1);
                    //将元数据存入实体数组
                    columnValueArr[i] = columnValue;
                }
                return columnValueArr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
