package com.prim.biz;

import com.prim.pojo.Book;
import java.util.List;

public interface BookBiz {
    List<Book> selectBookByCid(int categoryId);

    int batchBook(List<Book> books);

    List<Book> selectAll();
}
