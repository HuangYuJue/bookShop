package org.book.service.impl;

import org.book.dao.DetailDao;
import org.book.dao.OrderDao;
import org.book.service.BookService;
import org.book.service.DetailService;
import org.book.entity.Book;
import org.book.entity.Detail;
import org.book.entity.Order;

import java.util.List;

public class DetailServiceImpl implements DetailService {
    private DetailDao detailDao;
    private BookService bookService;
    private OrderDao orderDao;
    @Override
    public List<Detail> getDetailList(Integer orderId) {
        List<Detail> detailList = detailDao.getDetailList(orderId);
        //循环遍历list集合
        for (Detail detail : detailList){
            //获取到detail类中的book的id，并将值赋进去
            Book book = detail.getBook();
            book = bookService.getBook(book.getId());
            detail.setBook(book);
        }
        return detailList;
    }

    @Override
    public List<Detail> getAllDetail() {
        List<Detail> allDetail = detailDao.getAllDetail();
        for (Detail detail : allDetail){
            //获取到detail类中的book的id，并将值赋进去
            Book book = detail.getBook();
            book = bookService.getBook(book.getId());
            detail.setBook(book);
            //获取到OrderBean类中的order的id，并将值赋进去
            Order orderBean = detail.getOrderBean();
            orderBean = orderDao.getOrder(orderBean.getId());
            detail.setOrderBean(orderBean);
        }
        return allDetail;
    }

    @Override
    public void delDetail(Integer detailId) {
        detailDao.delDetail(detailId);
    }
}
