package org.book.controller;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.book.entity.User;
import org.book.service.BookService;
import org.book.entity.Book;
import org.book.myssm.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

public class BookController {
    private BookService bookService;
    //按关键字、页数等查询所有图书
    public String index(Double minPrice, Double maxPrice, String keyWord, Integer pageNo, HttpSession session){
        //调用方法
        getBook(minPrice, maxPrice, keyWord, pageNo, session);
        return "index";
    }
    //图书管理
    public String bookManager(Double minPrice, Double maxPrice, String keyWord, Integer pageNo, HttpSession session){
        //获取session中的user
        User user = (User) session.getAttribute("user");
        //如果user存在且role=1
        if (user != null){
            Integer role = user.getRole();
            //如果是管理员
            if (role == 1){
                //调用方法
                getBook(minPrice, maxPrice, keyWord, pageNo, session);
                getNoBook(minPrice, maxPrice, keyWord, pageNo, session);
                return "manager/book_manager";
            }
        }
        return "index";
    }
    //获取所有有效图书
    public void getBook(Double minPrice, Double maxPrice, String keyWord, Integer pageNo, HttpSession session){
        if (minPrice == null){
            minPrice = 0.00;
        }
        //double类型，长度为10，小数点为2，则整数为8
        if (maxPrice == null){
            maxPrice = 99999999.99;
        }
        if (StringUtil.isEmpty(keyWord)){
            keyWord = "";
        }
        if (pageNo == null){
            pageNo = 1;
        }
        List<Book> bookList = bookService.getBookList(0,minPrice, maxPrice, keyWord, pageNo);
        Integer count = bookService.getCount(0,minPrice, maxPrice, keyWord);
        Integer pages = (count+10-1)/10;
        session.setAttribute("keyWord",keyWord);
        session.setAttribute("pageNo",pageNo);
        session.setAttribute("count",count);
        session.setAttribute("pages",pages);
        session.setAttribute("bookList",bookList);
    }
    //获取所有无效图书
    public void getNoBook(Double minPrice, Double maxPrice, String keyWord, Integer pageNo, HttpSession session){
        if (minPrice == null){
            minPrice = 0.00;
        }
        //double类型，长度为10，小数点为2，则整数为8
        if (maxPrice == null){
            maxPrice = 99999999.99;
        }
        if (StringUtil.isEmpty(keyWord)){
            keyWord = "";
        }
        if (pageNo == null){
            pageNo = 1;
        }
        List<Book> no_bookList = bookService.getBookList(-1,minPrice, maxPrice, keyWord, pageNo);
        Integer no_count = bookService.getCount(-1,minPrice, maxPrice, keyWord);
        Integer no_pages = (no_count+10-1)/10;
        session.setAttribute("no_pageNo",pageNo);
        session.setAttribute("no_count",no_count);
        session.setAttribute("no_pages",no_pages);
        session.setAttribute("no_bookList",no_bookList);
    }

    //根据id获取单条书本信息
    public String getBookById(Integer bookId,HttpSession session){
        Book book = bookService.getBook(bookId);
        session.setAttribute("book",book);
        return "manager/book_edit";
    }
    //编辑图书
    public String edit(Integer id,String bookImg,String bookName,Double price,String author,Integer saleCount,Integer bookCount){
        bookService.editBook(new Book(id,bookImg,bookName,price,author,saleCount,bookCount));
        return "redirect:book.do?operate=bookManager";
    }

    //添加图书
    public String add(String bookImg, String bookName, Double price, String author, Integer saleCount, Integer bookCount){
        Book book = new Book(bookImg, bookName, price, author, saleCount, bookCount);
        bookService.addBook(book);
        return "redirect:book.do?operate=bookManager";
    }

    //删除图书，status变成-1/图书生效，status变成0
    public String del(Integer status,Integer bookId){
        bookService.del(status,bookId);
        return "redirect:book.do?operate=bookManager";
    }
}
