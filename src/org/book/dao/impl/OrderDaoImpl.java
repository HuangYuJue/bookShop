package org.book.dao.impl;

import org.book.dao.OrderDao;
import org.book.myssm.basedao.DaoConn;
import org.book.entity.Order;
import org.book.entity.User;

import java.math.BigDecimal;
import java.util.List;

public class OrderDaoImpl extends DaoConn<Order> implements OrderDao {
    @Override
    public void addOrder(Order order) {
        //记得order表要加入”`“，不然会报语法错误，`order`。
        Integer id = super.executeUpdate("insert into `order` (orderDate,orderMoney,orderNo,orderUser,orderStatus) value(?,?,?,?,?)", order.getOrderDate(), order.getOrderMoney(), order.getOrderNo(), order.getOrderUser().getId(), order.getOrderStatus());
        /**
         * 因为在添加时 DaoConn 中有写，如果是 insert 则会返回 主键id，在添加操作时不知道 主键id，添加完成后，可知 id值。
         * 获取到 id 之后再将 id 的值存储进实体类对象中，则添加之后立马获取到了 id 值，而所有值都有了，而不是不存在 id。
         * id是主键，自动递增，所以不自己设置值，所以全程这个实体类对象中的id都为null，
         * 只有通过查询才能查询出id，但仅仅在添加操作中，主键id是不会有值的，一直为null。
         */
        //后将 id 设置进实体类对象中，这样id就有值了，而Order的主键id与Detail的外键相连，所以service添加要利用到Order的id
        order.setId(id);
    }

    @Override
    public List<Order> getOrderList(User user) {
        List<Order> orderList = super.getAll("select * from `order` where orderUser = ?", user.getId());
        return orderList;
    }

    @Override
    public Integer getBookTotal(Order order) {
        /**
         * ① select * from `order` t1 inner join order_detail t2 on t1.id = t2.orderBean where orderUser = 2：
         * 将order表视t1表，将order_detail表视为t2表，将查询当用户为2，在order表中的id与order_detail表中的orderBean相同时，
         * order与order_detail表中所有的信息，其中order每列在前面，order_detail每列在后。
         * ② select t1.id,t2.buyCount,t2.orderBean from `order` t1 inner join order_detail t2 on t1.id = t2.orderBean where orderUser = 2：
         * 将order表视t1表，将order_detail表视为t2表，将查询当用户为2，在order表中的id与order_detail表中的orderBean相同时，
         * order表中的id，order_detail表中的buyCount和orderBean查询出来，顺序为：id、buyCount、orderBean。
         * ③ select sum(t3.buyCount) as bookTotal,t3.orderBean from
         * (select t1.id,t2.buyCount,t2.orderBean from `order` t1 inner join order_detail t2
         * on t1.id = t2.orderBean where orderUser = 2) t3 where t3.orderBean = 13
         * 将第②步查询出来的表视为t3表，将要查询的orderBean从t3表中获取，并查询t3表中t3的orderBean为13时的buyCount的所有值并相加，
         * 即可的userBean为2在orderBean为13时购买的图书数量，并将其命名为bookTotal。
         */
        String sql = "select sum(t3.buyCount) as bookTotal,t3.orderBean from " +
                "(select t1.id,t2.buyCount,t2.orderBean from `order` t1 inner join order_detail t2" +
                " on t1.id=t2.orderBean where orderUser = ?) t3 where t3.orderBean = ?";
        return ((BigDecimal)super.getComplexQuery(sql,order.getOrderUser().getId(),order.getId())[0]).intValue();
    }

    @Override
    public Order getOrder(Integer orderId) {
        return super.getOne("select * from `order` where id = ?",orderId);
    }

    @Override
    public void delOrder(Order order) {
        super.executeUpdate("delete from `order` where id = ?",order.getId());
    }

    @Override
    public List<Order> getAllOrder() {
        return super.getAll("select * from `order`");
    }

    @Override
    public void sendOrder(Integer orderId) {
        super.executeUpdate("update `order` set orderStatus = 1 where id = ?",orderId);
    }
}
