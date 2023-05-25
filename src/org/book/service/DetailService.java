package org.book.service;

import org.book.entity.Detail;

import java.util.List;

public interface DetailService {
    //根据订单id获取到订单详情
    public List<Detail> getDetailList(Integer orderId);

    //查询所有订单详情
    public List<Detail> getAllDetail();

    //取消购买订单中某书本
    void delDetail(Integer detailId);
}
