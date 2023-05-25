package org.book.service;

import org.book.entity.Book;

import java.util.List;

public interface BookService {
    //按最低价最高价、关键词、按页查询
    List<Book> getBookList(Integer status,Double minPrice, Double maxPrice, String keyWord, Integer pageNo);

    //获取符合条件的所有条数
    Integer getCount(Integer status,Double minPrice, Double maxPrice, String keyWord);

    //获取单条书本信息
    Book getBook(Integer id);

    //添加图书
    void addBook(Book book);

    //编辑书本信息
    void editBook(Book book);

    //删除书城中图书(将bookStatus由0改为-1，有效变成无效)
    void del(Integer status,Integer bookId);
}
