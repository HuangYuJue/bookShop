package org.book.dao.impl;

import org.book.dao.DetailDao;
import org.book.myssm.basedao.DaoConn;
import org.book.entity.Detail;

import java.util.List;

public class DetailDaoImpl extends DaoConn<Detail> implements DetailDao {
    @Override
    public void addDetail(Detail detail) {
        super.executeUpdate("insert into order_detail(book,buyCount,orderBean) value(?,?,?)",detail.getBook().getId(),detail.getBuyCount(),detail.getOrderBean().getId());
    }

    public List<Detail> getDetailList(Integer orderId){
        return super.getAll("select * from order_detail where orderBean = ?",orderId);
    }

    @Override
    public List<Detail> getAllDetail() {
        return super.getAll("select * from order_detail");
    }

    @Override
    public void delDetail(Integer detailId) {
        super.executeUpdate("delete from order_detail where id = ?",detailId);
    }
}
