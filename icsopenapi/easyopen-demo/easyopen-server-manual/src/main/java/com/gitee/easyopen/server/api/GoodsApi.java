package com.gitee.easyopen.server.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.auth0.jwt.interfaces.Claim;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.ApiResult;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.auth.OpenUser;
import com.gitee.easyopen.doc.DataType;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.server.api.param.GoodsParam;
import com.gitee.easyopen.server.api.param.GoodsParam2;
import com.gitee.easyopen.server.api.result.Goods;
import com.gitee.easyopen.server.api.result.GoodsPageVo;
import com.gitee.easyopen.server.model.PageInfo;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;

/**
 * 业务类
 * 
 * @author tanghc
 *
 */
@ApiService
@ApiDoc(value = "商品模块", order = 1)
public class GoodsApi {

    @Autowired
    UserService userService; // 依赖注入

    @Api(name = "goods.get")
    @ApiDocMethod(description="获取商品")
    Goods getGoods(GoodsParam param) {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name("苹果iPhoneX");
        goods.setPrice(new BigDecimal(8000));
        return goods;
    }

    @Api(name = "goods.list", version = "2.0"
            , wrapResult = false // 对结果不进行包装，直接将List<Goods>转成json数组形式返回
            )
    @ApiDocMethod(description="获取商品列表"
        ,results={@ApiDocField(description="商品列表",name="list", elementClass=Goods.class)}
    )
    public List<Goods> listGoods(GoodsParam param) {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name("iPhoneX");
        goods.setPrice(new BigDecimal(8000));

        Goods goods2 = new Goods();
        goods2.setId(2L);
        goods2.setGoods_name("三星");
        goods2.setPrice(new BigDecimal(7000));
        return Arrays.asList(goods, goods2);
    }
    
    @Api(name = "wrapResult.false"
            , wrapResult = false // 对结果不进行包装，直接将ApiResult转成json形式返回
            )
    public ApiResult fun(GoodsParam param) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(200);
        apiResult.setMsg("xxx");
        return apiResult;
    }
    
     @Api(name = "goods.pageinfo", version = "1.0")
    @ApiDocMethod(description="获取商品列表"
            ,results={@ApiDocField(name="pageIndex",description="第几页",dataType=DataType.INT,example="1"),
                    @ApiDocField(name="pageSize",description="每页几条数据",dataType=DataType.INT,example="10"),
                    @ApiDocField(name="total",description="每页几条数据",dataType=DataType.LONG,example="100"),
                    @ApiDocField(name="rows",description="数据",dataType=DataType.ARRAY,elementClass=Goods.class),}
            )
    public PageInfo<Goods> pageinfo(GoodsParam param) {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name("iPhoneX");
        goods.setPrice(new BigDecimal(8000));

        Goods goods2 = new Goods();
        goods2.setId(2L);
        goods2.setGoods_name("三星");
        goods2.setPrice(new BigDecimal(7000));
        
        List<Goods> list = Arrays.asList(goods, goods2);
        
        PageInfo<Goods> pageInfo = new PageInfo<>();
        pageInfo.setPageIndex(1);
        pageInfo.setPageSize(10);
        pageInfo.setTotal(100);
        pageInfo.setRows(list);
        return pageInfo;
    }
    
    @Api(name = "goods.pageinfo", version = "2.0")
    @ApiDocMethod(description="获取商品列表",resultClass=GoodsPageVo.class)
    public PageInfo<Goods> pageinfo2(GoodsParam param) {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name("iPhoneX");
        goods.setPrice(new BigDecimal(8000));

        Goods goods2 = new Goods();
        goods2.setId(2L);
        goods2.setGoods_name("三星");
        goods2.setPrice(new BigDecimal(7000));
        
        List<Goods> list = Arrays.asList(goods, goods2);
        
        PageInfo<Goods> pageInfo = new PageInfo<>();
        pageInfo.setPageIndex(1);
        pageInfo.setPageSize(10);
        pageInfo.setTotal(100);
        pageInfo.setRows(list);
        return pageInfo;
    }

    // 该接口不需要验证
    @Api(name = "goods.add", ignoreValidate = true)
    @ApiDocMethod(description="添加商品")
    public void addGoods(GoodsParam param) {
        Goods goods = new Goods();
        goods.setId(2L);
        goods.setGoods_name(param.getGoods_name());
        goods.setPrice(new BigDecimal(8000));
    }
    
    // accessToken认证,拿到用户
    @Api(name = "user.goods.get")
    @ApiDocMethod(description="获取用户商品(accessToken)")
    public Goods getusergoods(GoodsParam param) {
        // 拿到accessToken用户
        OpenUser user = ApiContext.getAccessTokenUser();
        System.out.println(user.getUsername());
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name(user.getUsername() + " iPhoneX");
        goods.setPrice(new BigDecimal(8000));
        return goods;
    }
    
    // jwt认证,拿到数据
    @Api(name = "userjwt.goods.get")
    @ApiDocMethod(description="获取用户商品(jwt)",
            remark = "先访问<a href='http://localhost:8080/jwtLogin' target='_blank'>这里</a>获取jwt")
    public Goods getuserjwtgoods(GoodsParam param) {
        // 获取jwt数据
        Map<String, Claim> jwtData = ApiContext.getJwtData();
        
        Set<Entry<String, Claim>> set = jwtData.entrySet();
        for (Entry<String, Claim> entry : set) {
            System.out.println("key:" + entry.getKey() + ", value:" + entry.getValue().as(String.class));
        }
        
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name("iPhoneX " + jwtData.get("username").asString());
        goods.setPrice(new BigDecimal(8000));
        return goods;
    }
    
    AtomicInteger i = new AtomicInteger(1);
    // 测试分布式锁功能
    @Api(name = "userlock.test")
    public Goods getuserjwtgoods2(GoodsParam param) throws UnsupportedEncodingException {
        System.out.println("======================="+i.getAndIncrement());
        
        Goods goods = new Goods();
        goods.setId(i.longValue());
        goods.setGoods_name(param.getGoods_name() + " " + URLDecoder.decode(ApiContext.getApiParam().fatchData(), "UTF-8"));
        goods.setPrice(new BigDecimal(8000));
        return goods;
    }

    // 下载文件，可用Postman请求
    /*
        postman设置：
        POST URL:http://localhost:8080/api
        body raw application/json
        {
            "name": "download.test",
            "version": "",
            "data": "%7B%22goods_name%22%3A%22iphoneX%22%7D"
        }

        点击sand and download
      */
    @Api(name = "download.test", ignoreValidate = true, noReturn = true)
    public void download(GoodsParam param) throws IOException {
        HttpServletResponse response = ApiContext.getResponse();
        String fileName = "文件.txt";
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));

        // 下载application.properties文件
        ClassPathResource resource = new ClassPathResource("application.properties");
        InputStream inputStream = resource.getInputStream();
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
    }

    // 下载文件，浏览器输入：http://127.0.0.1:8080/api?name=download.test2&version=
    @Api(name = "download.test2", ignoreValidate = true, noReturn = true)
    public void download2() throws IOException {
        HttpServletResponse response = ApiContext.getResponse();
        String fileName = "文件.txt";
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));

        // 下载application.properties文件
        ClassPathResource resource = new ClassPathResource("application.properties");
        InputStream inputStream = resource.getInputStream();
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
    }

}
