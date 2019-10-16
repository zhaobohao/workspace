package com.gitee.sop.bookweb.controller;

import com.gitee.book.api.domain.Book;
import com.gitee.sop.bookweb.consumer.StoryServiceConsumer;
import com.gitee.sop.bookweb.param.BookParam;
import com.gitee.sop.bookweb.vo.BookVO;
import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.story.api.domain.Story;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 这里演示如何接受业务参数。
 * @author tanghc
 */
@Api(tags = "图书接口")
@RestController
public class AlipayBookController {

    @Autowired
    StoryServiceConsumer storyServiceConsumer;

    @ApiOperation(value="查询书本信息", notes = "可以根据ISBN查询书本信息")
    @ApiMapping(value = "book.search")
    public BookVO searchBook(BookParam param) {
        BookVO bookVO = new BookVO();
        bookVO.setId(1);
        bookVO.setName("白雪公主，ISBN:" + param.getIsbn());
        bookVO.setIsbn("ABCSSSSDDD");
        return bookVO;
    }

    @ApiMapping(value = "alipay.book.get")
    public Book getBook() {
        Book story = new Book();
        story.setId(1);
        story.setName("白雪公主(alipay.book.get)");
        return story;
    }

    // 通过Feign调用story服务
    @ApiMapping(value = "alipay.book.story.get")
    public Object getBook2() {
        Story story = new Story();
        story.setId(1);
        story.setName("白雪公主(alipay.book.story.get)");
        Story story2 = storyServiceConsumer.getStory(1);
        return Arrays.asList(story, story2);
    }

}
