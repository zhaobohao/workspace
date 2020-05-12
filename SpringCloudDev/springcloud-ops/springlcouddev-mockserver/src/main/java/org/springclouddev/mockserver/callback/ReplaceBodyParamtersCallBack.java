package org.springclouddev.mockserver.callback;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.mockserver.mock.action.ExpectationResponseCallback;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.Parameter;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.mockserver.entity.MockHttp;
import org.springclouddev.mockserver.wrapper.MockWrapper;
import org.springframework.http.HttpHeaders;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhaobo
 * @date
 */
@Data
public class ReplaceBodyParamtersCallBack implements ExpectationResponseCallback {
    private MockHttp mockHttp;

    public ReplaceBodyParamtersCallBack() {
    }

    public ReplaceBodyParamtersCallBack(MockHttp mockHttp) {
        this.mockHttp = mockHttp;
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse handle(HttpRequest request) throws Exception {
        String responseBody = mockHttp.getResponseBody();
        //1. 取request里的parameters进行替换
        List<Parameter> plist = request.getQueryStringParameterList();
        for (int i = 0, size = plist.size(); i < size; i++) {
            Parameter parameter = plist.get(i);
            if (mockHttp.getResponseBody().indexOf("${" + parameter.getName() + "}") > 0) {
                responseBody = responseBody.replaceAll("\\$\\{" + parameter.getName() + "\\}", CollUtil.join(parameter.getValues(), ","));
            }
        }
        //2. 取jsonBody进行替换
        if (request.getFirstHeader(HttpHeaders.CONTENT_TYPE).indexOf("json") > 0) {
            String jsonBody = request.getBodyAsString();
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            if (jsonBody.startsWith("{")) {
                jsonObject = JSONUtil.parseObj(jsonBody);
            } else if (jsonBody.startsWith("[")) {
                jsonArray = JSONUtil.parseArray(jsonBody);
            }
            if (StrUtil.isNotBlank(jsonBody)) {
                List<String> keys = getKeys(mockHttp.getResponseBody());
                for (int i = 0, size = keys.size(); i < size; i++) {
                    String key = keys.get(i);
                    if (null != jsonObject) {
                        responseBody = responseBody.replaceAll("\\$\\{" + key + "\\}", String.valueOf(jsonObject.getByPath(key)));
                    } else if (null != jsonArray) {
                        responseBody = responseBody.replaceAll("\\$\\{" + key + "\\}", String.valueOf(jsonArray.getByPath(key)));
                    }
                }
            }
        }
        MockHttp clone = new MockHttp();
        BeanUtil.copyProperties(mockHttp, clone);
        clone.setResponseBody(responseBody);
        return MockWrapper.mockHttpResponse(clone).get();

    }

    /**
     * 提取所有的key.
     *
     * @param source
     * @return
     */
    private List<String> getKeys(String source) {
        Pattern pattern = Pattern.compile("(\\$\\{)(.*?)(\\})");
        Matcher matcher = pattern.matcher(source);
        List<String> keys = new LinkedList<String>();
        while (matcher.find()) {
            keys.add(matcher.group(2));
        }
        return keys;
    }
}
