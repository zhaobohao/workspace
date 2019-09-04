

package com.spring.web.core.configure.json.jackson.deserializer;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author zhaobohao
 * @date 2018-11-08
 */
public class JacksonDoubleDeserializer extends JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String string = jsonParser.getText();
        return StrUtil.isBlank(string)?null: Double.parseDouble(string);
    }
}
