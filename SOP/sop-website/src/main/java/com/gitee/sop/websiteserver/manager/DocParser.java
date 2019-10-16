package com.gitee.sop.websiteserver.manager;

import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.websiteserver.bean.DocInfo;

/**
 * @author tanghc
 */
public interface DocParser {
    DocInfo parseJson(JSONObject docRoot);
}
