package com.gitee.sop.websiteserver.manager;

import com.gitee.sop.websiteserver.bean.DocInfo;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author tanghc
 */
public interface DocManager {

    void addDocInfo(String serviceId, String docJson, Consumer<DocInfo> callback);

    DocInfo getByTitle(String title);

    Collection<DocInfo> listAll();

    void remove(String serviceId);
}
