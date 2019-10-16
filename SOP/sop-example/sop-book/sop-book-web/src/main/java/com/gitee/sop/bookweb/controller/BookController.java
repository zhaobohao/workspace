package com.gitee.sop.bookweb.controller;

import com.gitee.book.api.domain.Book;
import com.gitee.sop.bookweb.consumer.StoryServiceConsumer;
import com.gitee.sop.bookweb.param.BookParam;
import com.gitee.sop.story.api.domain.Story;
import com.gitee.book.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * book服务
 *
 * @author tanghc
 */
@RestController
public class BookController implements BookService {

    @Autowired
    StoryServiceConsumer storyServiceConsumer;

    @Override
    public Book getBook(int id) {
        Book book = new Book();
        book.setId(id);
        book.setName("汪汪队");
        return book;
    }

    @RequestMapping("listBookAndStory")
    public Object listBookAndStory(int id) {
        Book book = new Book();
        book.setId(id);
        book.setName("汪汪队");

        // 调用story服务
        Story story = storyServiceConsumer.getStory(id);

        return Arrays.asList(book, story);
    }

    @RequestMapping("getBook2")
    public Object getBookError(int id) {
        if (id == 0) {
            throw new RuntimeException("id不能为空");
        }
        Book book = new Book();
        book.setId(id);
        book.setName("汪汪队");
        return Arrays.asList(book);
    }



    @RequestMapping("getBook3")
    public Object getBook3(@RequestBody BookParam param) {
        if (param.getId() == 0) {
            throw new RuntimeException("id不能为空");
        }
        Book book = new Book();
        book.setId(param.getId());
        book.setName("小马宝莉");

        ApiResult apiResult = new ApiResult();
        apiResult.setData(book);
        return apiResult;
    }

}
