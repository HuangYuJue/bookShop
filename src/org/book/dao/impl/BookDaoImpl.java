package org.book.dao.impl;

import org.book.myssm.basedao.DaoConn;
import org.book.dao.BookDao;
import org.book.entity.Book;

import java.util.List;

public class BookDaoImpl extends DaoConn<Book> implements BookDao {
    @Override
    public List<Book> getBookList(Integer status,Double minPrice, Double maxPrice, String keyWord, Integer pageNo) {
        String sql = "select * from " +
                "(select * from book where bookStatus=?) t1 " +
                "where price between ? and ? and bookName like ? or author like ? limit ?,10";
        return super.getAll(sql,status,minPrice,maxPrice,"%"+keyWord+"%","%"+keyWord+"%",(pageNo-1)*10);
    }

    @Override
    public Integer getCount(Integer status,Double minPrice, Double maxPrice, String keyWord) {
        String sql = "select count(*) from" +
                "(select * from book where bookStatus=?) t1 " +
                "where price between ? and ? and bookName like ? or author like ?";
        Long aLong = (Long) super.getComplexQuery(sql, status,minPrice, maxPrice, "%" + keyWord + "%", "%" + keyWord + "%")[0];
        return aLong.intValue();
    }

    @Override
    public Book getBook(Integer id) {
        return super.getOne("select * from book where id = ?",id);
    }

    @Override
    public void addBook(Book book) {
        super.executeUpdate("insert into book(bookImg,bookName,price,author,saleCount,bookCount,bookStatus) value(?,?,?,?,?,?,0)",book.getBookImg(),book.getBookName(),book.getPrice(),book.getAuthor(),book.getSaleCount(),book.getBookCount());
    }

    @Override
    public void editBook(Book book) {
        String sql = "update book set bookImg = ?,bookName = ?,price = ?,author = ?,saleCount = ?,bookCount = ? where id = ?";
        super.executeUpdate(sql,book.getBookImg(),book.getBookName(),book.getPrice(),book.getAuthor(),book.getSaleCount(),book.getBookCount(),book.getId());
    }

    @Override
    public void del(Integer status,Integer bookId) {
        super.executeUpdate("update book set bookStatus = ? where id = ?",status,bookId);
    }
}
