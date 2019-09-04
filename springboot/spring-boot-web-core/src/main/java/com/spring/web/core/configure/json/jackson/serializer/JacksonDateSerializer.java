

package com.spring.web.core.configure.json.jackson.serializer;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * <p>
 *     Jackson Date反序列化器
 * </p>
 * @author geekidea
 * @date 2018-11-08
 */
public class JacksonDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (date != null){
            String dateString = DateUtil.format(date,"yyyy-MM-dd HH:mm:ss");
            jsonGenerator.writeString(dateString);
        }
    }
}
