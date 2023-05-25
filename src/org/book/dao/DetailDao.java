package org.book.dao;

import org.book.entity.Detail;
import org.book.entity.User;

import java.util.List;

public interface DetailDao {
    //添加订单详情
    void addDetail(Detail detail);

    //通过订单id获取订单详情
    List<Detail> getDetailList(Integer orderId);

    //获取所有的订单
    List<Detail> getAllDetail();

    //取消购买订单中某书本
    void delDetail(Integer detailId);
}
