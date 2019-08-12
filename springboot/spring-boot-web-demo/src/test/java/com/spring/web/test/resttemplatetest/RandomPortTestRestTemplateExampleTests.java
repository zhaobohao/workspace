package com.spring.web.test.resttemplatetest;

import com.spring.web.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testng.annotations.Test;

@Slf4j
public class RandomPortTestRestTemplateExampleTests extends BaseTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void exampleTest() {
        String body = this.restTemplate.getForObject("/hello", String.class);
        Assertions.assertThat(body).isEqualTo("zhaobo");
    }

}
