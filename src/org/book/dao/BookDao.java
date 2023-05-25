package org.book.dao;

import org.book.entity.Book;

import java.util.List;

public interface BookDao {
    //获取后台所有的书本，可根据价格范围以及书名及作者查询，且分页pageNo表示当前页，且当前书本状态为0
    List<Book> getBookList(Integer status, Double minPrice, Double maxPrice, String keyWord, Integer pageNo);

    //获取符合条件的所有书籍条数
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
