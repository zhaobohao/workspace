package com.gitee.sop.storyweb.controller;

import com.gitee.sop.servercommon.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * 传统web开发实例
 *
 * @author tanghc
 */
@RestController
@RequestMapping("food")
@Api(tags = "食物接口")
public class TraditionalWebappController {


    // http://localhost:8081/rest/story-service/food/getFoodById?id=1  网关入口
    // http://localhost:2222/food/getFoodById/?id=12  本地入口
    @ApiOperation(value="获取食物", notes = "根据id获取食物")
    @RequestMapping(value = "getFoodById", method = RequestMethod.GET)
    public Food getFoodById(Integer id) {
        Food food = new Food();
        food.setId(id);
        food.setName("香蕉");
        food.setPrice(new BigDecimal(20.00));
        return food;
    }

    // http://localhost:8081/rest/story-service/food/getFoodByObj?id=2
    @RequestMapping(value = "getFoodByObj", method = RequestMethod.GET)
    public Food getFoodByObj(Food food) {
        return food;
    }

    // http://localhost:8081/rest/story-service/food/saveFood
    @RequestMapping(value = "saveFood", method = RequestMethod.POST)
    public Food saveFood(@RequestBody Food food) {
        food.setId(3);
        return food;
    }

    @RequestMapping(value = "foodUpload", method = RequestMethod.POST)
    public Food upload(Food food, HttpServletRequest request) {
        // 获取上传的文件
        Collection<MultipartFile> uploadFiles = UploadUtil.getUploadFiles(request);
        StringBuilder sb = new StringBuilder();
        for (MultipartFile multipartFile : uploadFiles) {
            sb.append(multipartFile.getOriginalFilename());
        }
        food.setId(4);
        food.setName("文件名称+" + sb.toString());
        return food;
    }

    @RequestMapping(value = "foodUpload3", method = RequestMethod.POST)
    public Food upload3(Food food, MultipartFile file) {
        food.setId(5);
        food.setName("文件名称+" + file.getOriginalFilename());
        return food;
    }

    @RequestMapping(value = "foodUpload2", method = RequestMethod.POST)
    public Food upload2(Food food, MultipartFile file) {
        food.setId(4);
        food.setName("文件名称2+" + file.getOriginalFilename());
        return food;
    }

    // http://localhost:2222/food/get/3  本地
    // http://localhost:8081/rest/story-service/food/get/3  网关访问
    @RequestMapping("/get/{id}")
    public Food getById(@PathVariable("id") Integer id) {
        Food food = new Food();
        food.setId(id);
        food.setName("香蕉");
        food.setPrice(BigDecimal.valueOf(100));
        return food;
    }

    @Data
    public static class Food {
        private Integer id;
        private String name;
        private BigDecimal price;
    }

}
