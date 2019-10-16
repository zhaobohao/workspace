package com.gitee.sop.websiteserver.manager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.websiteserver.bean.DocInfo;
import com.gitee.sop.websiteserver.bean.DocItem;
import com.gitee.sop.websiteserver.bean.DocModule;
import com.gitee.sop.websiteserver.bean.DocParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
public class EasyopenDocParser implements DocParser {
    @Override
    public DocInfo parseJson(JSONObject docRoot) {
        String title = docRoot.getString("title");
        List<DocItem> docItems = new ArrayList<>();
        JSONArray apiModules = docRoot.getJSONArray("apiModules");
        for (int i = 0; i < apiModules.size(); i++) {
            JSONObject module = apiModules.getJSONObject(i);
            JSONArray moduleItems = module.getJSONArray("moduleItems");
            for (int k = 0; k < moduleItems.size(); k++) {
                JSONObject docInfo = moduleItems.getJSONObject(k);
                DocItem docItem = buildDocItem(docInfo);
                docItem.setModule(module.getString("name"));
                docItems.add(docItem);
            }
        }

        List<DocModule> docModuleList = docItems.stream()
                .collect(Collectors.groupingBy(DocItem::getModule))
                .entrySet()
                .stream()
                .map(entry -> {
                    DocModule docModule = new DocModule();
                    docModule.setModule(entry.getKey());
                    docModule.setDocItems(entry.getValue());
                    return docModule;
                })
                .collect(Collectors.toList());

        DocInfo docInfo = new DocInfo();
        docInfo.setTitle(title);
        docInfo.setDocModuleList(docModuleList);
        return docInfo;
    }

    protected DocItem buildDocItem(JSONObject docInfo) {
        DocItem docItem = new DocItem();
        docItem.setName(docInfo.getString("name"));
        docItem.setVersion(docInfo.getString("version"));
        docItem.setSummary(docInfo.getString("description"));
        docItem.setDescription(docInfo.getString("description"));
        List<DocParameter> docParameterList = this.buildParameterList(docInfo, "paramDefinitions");
        docItem.setRequestParameters(docParameterList);

        List<DocParameter> responseParameterList = this.buildParameterList(docInfo, "resultDefinitions");
        docItem.setResponseParameters(responseParameterList);

        return docItem;
    }

    protected List<DocParameter> buildParameterList(JSONObject docInfo, String key) {
        JSONArray params = docInfo.getJSONArray(key);
        if (params == null) {
            return Collections.emptyList();
        }
        List<DocParameter> docParameterList = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            JSONObject jsonObject = params.getJSONObject(i);
            DocParameter docParameter = jsonObject.toJavaObject(DocParameter.class);
            docParameter.setType(jsonObject.getString("dataType"));
            docParameterList.add(docParameter);
        }
        return docParameterList;
    }

}
