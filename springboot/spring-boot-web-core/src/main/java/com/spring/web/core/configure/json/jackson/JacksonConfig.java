

package com.spring.web.core.configure.json.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.spring.web.core.configure.json.jackson.deserializer.JacksonDateDeserializer;
import com.spring.web.core.configure.json.jackson.deserializer.JacksonDoubleDeserializer;
import com.spring.web.core.configure.json.jackson.serializer.JacksonDateSerializer;

import com.spring.web.core.configure.json.jackson.serializer.JacksonIntegerDeserializer;
import com.spring.web.core.constant.DatePattern;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 *
 * @author zhaobohao
 * @author 2018-11-08
 */
@Configuration
public class JacksonConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        SimpleModule simpleModule = new SimpleModule();
        // Long类型序列化成字符串，避免Long精度丢失
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        // Date
        simpleModule.addSerializer(Date.class,new JacksonDateSerializer());
        simpleModule.addDeserializer(Date.class,new JacksonDateDeserializer());

        simpleModule.addDeserializer(Integer.class,new JacksonIntegerDeserializer());
        simpleModule.addDeserializer(Double.class,new JacksonDoubleDeserializer());

        // jdk8日期序列化和反序列化设置
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd_HH_mm_ss)));
        javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd_HH_mm_ss)));
        
        javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd)));
        javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd)));
        
        javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.HH_mm_ss)));
        javaTimeModule.addDeserializer(LocalTime.class,new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.HH_mm_ss)));

        objectMapper.registerModule(simpleModule).registerModule(javaTimeModule).registerModule(new ParameterNamesModule());

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

        //放到第一个
        converters.add(0,jackson2HttpMessageConverter);
    }
}