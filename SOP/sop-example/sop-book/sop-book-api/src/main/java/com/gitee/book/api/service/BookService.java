package com.gitee.book.api.service;

import com.gitee.book.api.domain.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tanghc
 */
@RequestMapping("/book")
public interface BookService {
    @RequestMapping("/getBook")
    Book getBook(@RequestParam("id") int id);
}
