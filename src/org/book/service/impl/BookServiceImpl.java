package org.book.service.impl;

import org.book.dao.BookDao;
import org.book.service.BookService;
import org.book.entity.Book;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;
    @Override
    public List<Book> getBookList(Integer status,Double minPrice, Double maxPrice, String keyWord, Integer pageNo) {
        return bookDao.getBookList(status,minPrice, maxPrice, keyWord, pageNo);
    }

    @Override
    public Integer getCount(Integer status,Double minPrice, Double maxPrice, String keyWord) {
        Integer count = bookDao.getCount(status,minPrice, maxPrice, keyWord);
        return count;
    }

    @Override
    public Book getBook(Integer id) {
        return bookDao.getBook(id);
    }

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void editBook(Book book) {
        bookDao.editBook(book);
    }

    @Override
    public void del(Integer status,Integer bookId) {
        bookDao.del(status,bookId);
    }
}