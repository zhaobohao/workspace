package org.springbootexample.jwt;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Swagger2MarkupTest
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/1/26
 */
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Swagger2MarkupTest {
    @Autowired
    private MockMvc mockMvc;

    private static final Logger LOG = LoggerFactory.getLogger(Swagger2MarkupTest.class);

    /**
     * 自动生成adoc文件
     * @throws Exception e
     *
     * 执行完成后生成PDF文件方法：
     *
     * 首先在`swagger/swagger.adoc`的顶部加入：
    ```
    :toclevels: 3
    :numbered:
    ```
    注意有个空行分割，目的是左边导航菜单是3级，并且自动加序号。
    为了美化显示，还要将`swagger.adoc`中全局替换一下，将
    ```
    cols=".^2,.^3,.^9,.^4,.^2"
    ```
    替换成：
    ```
    cols=".^2,.^3,.^6,.^4,.^5"
    ```
    然后在/resources目录下面执行：
    ```
    asciidoctor-pdf -r asciidoctor-pdf-cjk-kai_gen_gothic -a pdf-style=KaiGenGothicCN swagger/swagger.adoc
    ```
    会在`swagger.adoc`的同级目录生成`swagger.pdf`文件。
     */
    @Test
    public void createSpringFoxSwaggerJson() throws Exception {
//        String outputDir = System.getProperty("swaggerOutputDir"); // mvn test
        MvcResult mvcResult = this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String swaggerJson = response.getContentAsString();
//        Files.createDirectories(Paths.get(outputDir));
//        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputDir, "swagger.json"), StandardCharsets.UTF_8)){
//            writer.write(swaggerJson);
//        }
        LOG.info("--------------------swaggerJson create --------------------");
        LOG.info("--------------------swagon.json to asciiDoc finished --------------------");
    }


}
