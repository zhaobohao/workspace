package com.gitee.easyopen.doc;

import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.util.VelocityUtil;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 生成markdown文档文件
 *
 * @author tanghc
 */
public class MarkdownDocCreator implements DocFileCreator {
    private static final Logger LOG = LoggerFactory.getLogger(MarkdownDocCreator.class);


    private static final String MARKDWON_SUFFIX = ".md";
    private static final String SIDEBAR_FILENAME = "_sidebar.md";
    private static final String API_FOLDER = "api/";

    private static final String MODULE_TPL = "\r\n\r\n* %s\r\n\r\n";
    private static final String FILE_TPL = "  * [%s](" + API_FOLDER + "%s.md)\r\n";

    private static final String MARKDOWN_TEMPLATE_CLASSPATH = "/easyopen_template/markdownDoc.md";


    /**
     * 存放文档的地方
     */
    private String docsDir;

    /**
     * api文件夹
     */
    private String apiDir;

    /**
     * 文档模板
     */
    private String template = MARKDOWN_TEMPLATE_CLASSPATH;

    /**
     * @param dir 文档根目录
     */
    public MarkdownDocCreator(String dir) {
        if (!dir.endsWith("/")) {
            dir = dir + "/";
        }
        this.docsDir = dir + "docs/";
        this.apiDir = this.docsDir + API_FOLDER;
    }

    public MarkdownDocCreator(String dir, String template) {
        this(dir);
        this.template = template;
    }

    /**
     * 生成markdown格式的文档
     */
    @Override
    public void createMarkdownDoc(Collection<ApiModule> apiModules) throws IOException {
        LOG.info("生成markdown文档文件，保存路径：{}", this.apiDir);
        this.createSidebar(apiModules);
        for (ApiModule apiModule : apiModules) {
            List<ApiDocItem> moduleItems = apiModule.getModuleItems();
            for (ApiDocItem item : moduleItems) {
                this.createDocFile(item);
            }
        }
    }

    /**
     * 生成侧边菜单
     *
     * @param apiModules
     */
    protected void createSidebar(Collection<ApiModule> apiModules) throws IOException {
        StringBuilder sidebarContent = new StringBuilder();
        for (ApiModule apiModule : apiModules) {
            // * Getting started 一级
            sidebarContent.append(String.format(MODULE_TPL, apiModule.getName()));
            List<ApiDocItem> moduleItems = apiModule.getModuleItems();
            for (ApiDocItem item : moduleItems) {
                // * [Quick start](quickstart.md) 二级
                sidebarContent.append(String.format(FILE_TPL, item.getDescription(), item.getNameVersion()));
            }
        }
        String siderbarFilePath = this.docsDir + SIDEBAR_FILENAME;
        FileUtils.write(new File(siderbarFilePath), sidebarContent.toString(), Consts.UTF8);
    }

    protected void createDocFile(ApiDocItem item) throws IOException {
        VelocityContext context = new VelocityContext();
        context.put("docItem", item);
        ClassPathResource resource = new ClassPathResource(this.template);
        String fileContent = VelocityUtil.generateToString(context, resource.getInputStream());
        String filepath = this.apiDir + item.getNameVersion() + MARKDWON_SUFFIX;
        FileUtils.write(new File(filepath), fileContent, Consts.UTF8);
    }

    public void setDocsDir(String docsDir) {
        this.docsDir = docsDir;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setApiDir(String apiDir) {
        this.apiDir = apiDir;
    }
}
