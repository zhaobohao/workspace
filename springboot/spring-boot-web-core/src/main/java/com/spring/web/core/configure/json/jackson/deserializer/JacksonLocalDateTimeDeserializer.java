

package com.spring.web.core.configure.json.jackson.deserializer;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.spring.web.core.constant.DatePattern;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *     Jackson LocaDateTime反序列化器
 * </p>
 * @author geekidea
 * @date 2018-11-08
 */
public class JacksonLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String string = jp.getText();
        if (StrUtil.isBlank(string)){
            return null;
        }
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd_HH_mm_ss));
    }

}
