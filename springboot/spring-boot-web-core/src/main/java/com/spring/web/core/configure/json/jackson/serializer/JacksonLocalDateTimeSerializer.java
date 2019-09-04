

package com.spring.web.core.configure.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.spring.web.core.constant.DatePattern;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *     Jackson LocalDateTime 自定义序列化器
 * </p>
 * @author geekidea
 * @date 2018/11/8
 */
public class JacksonLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (localDateTime != null){
            String string = localDateTime.format(DateTimeFormatter.ofPattern(DatePattern.yyyy_MM_dd_HH_mm_ss));
            jsonGenerator.writeString(string);
        }
    }

}
