package com.gitee.easyopen.doc;

import java.io.IOException;
import java.util.Collection;

/**
 * 负责文档文件生成
 * @author tanghc
 */
public interface DocFileCreator {

    /**
     * 生成markdown格式的文档
     * @param apiModules 文档信息
     * @throws IOException
     */
    void createMarkdownDoc(Collection<ApiModule> apiModules) throws IOException;
}
