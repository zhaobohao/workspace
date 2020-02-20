package com.gitee.sop.websiteserver.manager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.websiteserver.bean.DocInfo;
import com.gitee.sop.websiteserver.bean.DocItem;
import com.gitee.sop.websiteserver.bean.DocModule;
import com.gitee.sop.websiteserver.bean.DocParameter;
import com.gitee.sop.websiteserver.bean.DocParserContext;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 解析swagger的json内容
 *
 * @author tanghc
 */
public class SwaggerDocParser implements DocParser {
    @Override
    public DocInfo parseJson(JSONObject docRoot) {
        String title = docRoot.getJSONObject("info").getString("title");
        List<DocItem> docItems = new ArrayList<>();

        JSONObject paths = docRoot.getJSONObject("paths");
        if (paths == null) {
            paths = new JSONObject();
        }
        Set<String> pathNameSet = paths.keySet();
        for (String apiPath : pathNameSet) {
            JSONObject pathInfo = paths.getJSONObject(apiPath);
            // key: get,post,head...
            Collection<String> httpMethodList = getHttpMethods(pathInfo);
            Optional<String> first = httpMethodList.stream().findFirst();
            if (first.isPresent()) {
                String method = first.get();
                JSONObject docInfo = pathInfo.getJSONObject(method);
                DocItem docItem = buildDocItem(docInfo, docRoot);
                if (docItem == null) {
                    continue;
                }
                if (docItem.isUploadRequest()) {
                    docItem.setHttpMethodList(Sets.newHashSet("post"));
                } else {
                    docItem.setHttpMethodList(httpMethodList);
                }
                docItems.add(docItem);
            }
        }

        docItems.sort(Comparator.comparing(DocItem::getNameVersion));

        List<DocModule> docModuleList = docItems.stream()
                .collect(Collectors.groupingBy(DocItem::getModule))
                .entrySet()
                .stream()
                .map(entry -> {
                    List<DocItem> docItemList = entry.getValue();
                    DocModule docModule = new DocModule();
                    docModule.setModule(entry.getKey());
                    docModule.setDocItems(docItemList);
                    docModule.setOrder(getMuduleOrder(docItemList));
                    return docModule;
                }).sorted(Comparator.comparing(DocModule::getOrder))
                .collect(Collectors.toList());

        DocInfo docInfo = new DocInfo();
        docInfo.setTitle(title);
        docInfo.setDocModuleList(docModuleList);
        return docInfo;
    }

    private int getMuduleOrder(List<DocItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Integer.MAX_VALUE;
        }
        List<DocItem> docItemList = new ArrayList<>(items);
        docItemList.sort(Comparator.comparing(DocItem::getModuleOrder));
        return docItemList.get(0).getModuleOrder();
    }

    protected Collection<String> getHttpMethods(JSONObject pathInfo) {
        // key: get,post,head...
        List<String> retList;
        Set<String> httpMethodList = pathInfo.keySet();
        if (httpMethodList.size() <= 2) {
            retList = new ArrayList<>(httpMethodList);
        } else {
            Set<String> ignoreHttpMethods = DocParserContext.ignoreHttpMethods;
            retList = httpMethodList.stream()
                    .filter(method -> !ignoreHttpMethods.contains(method.toLowerCase()))
                    .collect(Collectors.toList());
        }
        Collections.sort(retList);
        return retList;
    }

    protected DocItem buildDocItem(JSONObject docInfo, JSONObject docRoot) {
        String apiName = docInfo.getString("sop_name");
        // 非开放接口
        if (StringUtils.isBlank(apiName)) {
            return null;
        }
        DocItem docItem = new DocItem();
        docItem.setName(apiName);
        docItem.setVersion(docInfo.getString("sop_version"));
        docItem.setSummary(docInfo.getString("summary"));
        docItem.setDescription(docInfo.getString("description"));
        docItem.setMultiple(docInfo.getString("multiple") != null);
        docItem.setProduces(docInfo.getJSONArray("produces").toJavaList(String.class));
        docItem.setModuleOrder(NumberUtils.toInt(docInfo.getString("module_order"), 0));
        String moduleName = this.buildModuleName(docInfo, docRoot);
        docItem.setModule(moduleName);
        List<DocParameter> docParameterList = this.buildRequestParameterList(docInfo, docRoot);
        docItem.setRequestParameters(docParameterList);

        List<DocParameter> responseParameterList = this.buildResponseParameterList(docInfo, docRoot);
        docItem.setResponseParameters(responseParameterList);

        return docItem;
    }

    protected String buildModuleName(JSONObject docInfo, JSONObject docRoot) {
        String title = docRoot.getJSONObject("info").getString("title");
        JSONArray tags = docInfo.getJSONArray("tags");
        if (tags != null && tags.size() > 0) {
            return tags.getString(0);
        }
        return title;
    }

    protected List<DocParameter> buildRequestParameterList(JSONObject docInfo, JSONObject docRoot) {
        Optional<JSONArray> parametersOptional = Optional.ofNullable(docInfo.getJSONArray("parameters"));
        JSONArray parameters = parametersOptional.orElse(new JSONArray());
        List<DocParameter> docParameterList = new ArrayList<>();
        for (int i = 0; i < parameters.size(); i++) {
            JSONObject fieldJson = parameters.getJSONObject(i);
            DocParameter docParameter = fieldJson.toJavaObject(DocParameter.class);
            docParameterList.add(docParameter);
        }

        Map<String, List<DocParameter>> collect = docParameterList.stream()
                .filter(docParameter -> docParameter.getName().contains("."))
                .map(docParameter -> {
                    String name = docParameter.getName();
                    int index = name.indexOf('.');
                    String module = name.substring(0, index);
                    String newName = name.substring(index + 1);
                    DocParameter ret = new DocParameter();
                    BeanUtils.copyProperties(docParameter, ret);
                    ret.setName(newName);
                    ret.setModule(module);
                    return ret;
                })
                .collect(Collectors.groupingBy(DocParameter::getModule));

        collect.forEach((key, value) -> {
            DocParameter moduleDoc = new DocParameter();
            moduleDoc.setName(key);
            moduleDoc.setType("object");
            moduleDoc.setRefs(value);
            docParameterList.add(moduleDoc);
        });

        return docParameterList.stream()
                .filter(docParameter -> !docParameter.getName().contains("."))
                .collect(Collectors.toList());
    }

    protected List<DocParameter> buildResponseParameterList(JSONObject docInfo, JSONObject docRoot) {
        RefInfo refInfo = getResponseRefInfo(docInfo);
        List<DocParameter> respParameterList = Collections.emptyList();
        if (refInfo != null) {
            String responseRef = refInfo.ref;
            respParameterList = this.buildDocParameters(responseRef, docRoot, true);
            // 如果返回数组
            if (refInfo.isArray) {
                DocParameter docParameter = new DocParameter();
                docParameter.setName("items");
                docParameter.setType("array");
                docParameter.setRefs(respParameterList);
                respParameterList = Collections.singletonList(docParameter);
            }
        }
        return respParameterList;
    }

    protected List<DocParameter> buildDocParameters(String ref, JSONObject docRoot, boolean doSubRef) {
        JSONObject responseObject = docRoot.getJSONObject("definitions").getJSONObject(ref);
        JSONObject properties = responseObject.getJSONObject("properties");
        Set<String> fieldNames = properties.keySet();
        List<DocParameter> docParameterList = new ArrayList<>();
        for (String fieldName : fieldNames) {
            /*
               {
                  "description": "分类故事",
                  "$ref": "#/definitions/StoryVO"
                }
             */
            JSONObject fieldInfo = properties.getJSONObject(fieldName);
            DocParameter docParameter = fieldInfo.toJavaObject(DocParameter.class);
            docParameter.setName(fieldName);
            docParameterList.add(docParameter);
            RefInfo refInfo = this.getRefInfo(fieldInfo);
            if (refInfo != null && doSubRef) {
                // 如果是树状菜单的话，这里可能触发死循环
                String subRef = refInfo.ref;
                boolean nextDoRef = !Objects.equals(ref, subRef);
                List<DocParameter> refs = buildDocParameters(subRef, docRoot, nextDoRef);
                docParameter.setRefs(refs);
            }
        }
        return docParameterList;
    }


    /**
     * 简单对象返回：
     * "responses": {
     *                     "200": {
     *                         "description": "OK",
     *                         "schema": {
     *                             "$ref": "#/definitions/FileUploadVO"
     *                         }
     *                     },
     *                     "401": {
     *                         "description": "Unauthorized"
     *                     },
     *                     "403": {
     *                         "description": "Forbidden"
     *                     },
     *                     "404": {
     *                         "description": "Not Found"
     *                     }
     *                 }
     * 纯数组返回：
     * "responses": {
     *                     "200": {
     *                         "description": "OK",
     *                         "schema": {
     *                             "type": "array",
     *                             "items": {
     *                                 "$ref": "#/definitions/StoryVO"
     *                             }
     *                         }
     *                     },
     *                     "401": {
     *                         "description": "Unauthorized"
     *                     },
     *                     "403": {
     *                         "description": "Forbidden"
     *                     },
     *                     "404": {
     *                         "description": "Not Found"
     *                     }
     *                 }
     * @param docInfo
     * @return
     */
    protected RefInfo getResponseRefInfo(JSONObject docInfo) {
        return Optional.ofNullable(docInfo.getJSONObject("responses"))
                .flatMap(jsonObject -> Optional.ofNullable(jsonObject.getJSONObject("200")))
                .flatMap(jsonObject -> Optional.ofNullable(jsonObject.getJSONObject("schema")))
                .map(this::getRefInfo)
                .orElse(null);
    }

    private RefInfo getRefInfo(JSONObject jsonObject) {
        String ref;
        boolean isArray = "array".equals(jsonObject.getString("type"));
        if (isArray) {
            ref = jsonObject.getJSONObject("items").getString("$ref");
        } else {
            // #/definitions/Category
            ref = jsonObject.getString("$ref");
        }
        if (ref == null) {
            return null;
        }
        int index = ref.lastIndexOf("/");
        if (index > -1) {
            ref = ref.substring(index + 1);
        }
        RefInfo refInfo = new RefInfo();
        refInfo.isArray = isArray;
        refInfo.ref = ref;
        return refInfo;
    }

    private static class RefInfo {
        private boolean isArray;
        private String ref;
    }

}
