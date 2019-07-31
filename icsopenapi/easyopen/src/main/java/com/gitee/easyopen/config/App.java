package com.gitee.easyopen.config;

/**
 * @author tanghc
 */
public class App {

    /**
     * app名称
     */
    private String app;

    /**
     * 文档页面url
     */
    private String docUrl;

    /**
     * 文档页面密码, 数据库字段：doc_password
     */
    private String docPassword;

    /**
     * 文档状态，1:开启，0：关闭, 数据库字段：doc_status
     */
    private Byte docStatus;


    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getDocPassword() {
        return docPassword;
    }

    public void setDocPassword(String docPassword) {
        this.docPassword = docPassword;
    }

    public Byte getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(Byte docStatus) {
        this.docStatus = docStatus;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }
}
