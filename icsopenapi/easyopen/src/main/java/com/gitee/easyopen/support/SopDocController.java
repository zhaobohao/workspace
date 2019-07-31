package com.gitee.easyopen.support;

import com.gitee.easyopen.doc.ApiDocHolder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tanghc
 */
public abstract class SopDocController {

    public abstract String getDocTitle();

    @RequestMapping("/v2/api-docs")
    public Map<String, Object> getDocInfo() {
        Map<String, Object> context = this.getContext();
        context.put("easyopen", "1.16.3");
        context.put("apiModules", ApiDocHolder.getApiDocBuilder().getApiModules());
        context.put("title", getDocTitle());
        return context;
    }

    public Map<String, Object> getContext() {
        return new HashMap<>(8);
    }

}
