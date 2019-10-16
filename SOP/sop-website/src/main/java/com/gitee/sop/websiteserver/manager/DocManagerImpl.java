package com.gitee.sop.websiteserver.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.gitee.sop.websiteserver.bean.DocInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author tanghc
 */
@Service
@Slf4j
public class DocManagerImpl implements DocManager {

    // key:title
    private Map<String, DocInfo> docDefinitionMap = new HashMap<>();

    /**
     * KEY:serviceId, value: md5
     */
    private Map<String, String> serviceIdMd5Map = new HashMap<>();

    private DocParser swaggerDocParser = new SwaggerDocParser();

    private DocParser easyopenDocParser = new EasyopenDocParser();

    @Override
    public void addDocInfo(String serviceId, String docInfoJson, Consumer<DocInfo> callback) {
        String newMd5 = DigestUtils.md5DigestAsHex(docInfoJson.getBytes(StandardCharsets.UTF_8));
        String oldMd5 = serviceIdMd5Map.get(serviceId);
        if (Objects.equals(newMd5, oldMd5)) {
            return;
        }
        serviceIdMd5Map.put(serviceId, newMd5);
        JSONObject docRoot = JSON.parseObject(docInfoJson, Feature.OrderedField, Feature.DisableCircularReferenceDetect);
        DocParser docParser = this.buildDocParser(docRoot);
        DocInfo docInfo = docParser.parseJson(docRoot);
        docInfo.setServiceId(serviceId);
        docDefinitionMap.put(docInfo.getTitle(), docInfo);
        callback.accept(docInfo);
    }

    protected DocParser buildDocParser(JSONObject rootDoc) {
        Object easyopen = rootDoc.get("easyopen");
        if (easyopen != null) {
            return easyopenDocParser;
        } else {
            return swaggerDocParser;
        }
    }

    @Override
    public DocInfo getByTitle(String title) {
        return docDefinitionMap.get(title);
    }

    @Override
    public Collection<DocInfo> listAll() {
        return docDefinitionMap.values();
    }

    @Override
    public void remove(String serviceId) {
        serviceIdMd5Map.remove(serviceId);
        docDefinitionMap.entrySet().removeIf(entry -> serviceId.equalsIgnoreCase(entry.getValue().getServiceId()));
    }
}
