package org.book.controller;

import org.book.entity.User;
import org.book.service.DetailService;
import org.book.entity.Detail;

import javax.servlet.http.HttpSession;
import java.util.List;

public class DetailController {
    private DetailService detailService;
    //根据订单id查询订单详情
    public String getDetailList(Integer orderId, HttpSession session){
        List<Detail> detailList = detailService.getDetailList(orderId);
        session.setAttribute("detailList",detailList);
        User user = (User) session.getAttribute("user");
        if (user.getRole() == 0){
            return "order/detail";
        }else if (user.getRole() == 1){
            return "manager/detail_manager";
        }
        return null;
    }
    //查询所有订单信息
    public String getAllDetail(HttpSession session){
        List<Detail> allDetail = detailService.getAllDetail();
        session.setAttribute("allDetail",allDetail);
        return "manager/";
    }
    //取消购买订单中某书本
    public String delDetail(Integer detailId,HttpSession session){
        detailService.delDetail(detailId);
        Detail detail = (Detail) session.getAttribute("detailList");
        return "redirect:detail.do?operate=getDetailList&orderId="+detail.getOrderBean().getId();
    }
}