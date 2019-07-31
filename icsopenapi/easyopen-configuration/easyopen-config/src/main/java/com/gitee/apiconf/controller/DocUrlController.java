package com.gitee.apiconf.controller;

import com.gitee.apiconf.common.ChannelContext;
import com.gitee.apiconf.entity.AppInfo;
import com.gitee.apiconf.mapper.AppInfoMapper;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.util.MyBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author tanghc
 */
@RestController
public class DocUrlController {

    @Autowired
    AppInfoMapper appInfoMapper;

    @RequestMapping("listDocUrl")
    public Object listDocUrl() {
        Set<String> appNames = ChannelContext.listAppNames();

        if (CollectionUtils.isEmpty(appNames)) {
            return Collections.emptyList();
        }

        Query query = new Query().eq("doc_status", 1).orderby("app", Sort.ASC);
        List<AppInfo> list = appInfoMapper.list(query);
        List<AppInfoVo> retList = new ArrayList<>(list.size());

        for (AppInfo appInfo : list) {
            if (appNames.contains(appInfo.getApp())) {
                AppInfoVo vo = new AppInfoVo();
                MyBeanUtil.copyPropertiesIgnoreNull(appInfo, vo);
                vo.setNeedPassword(StringUtils.isNotBlank(appInfo.getDocPassword()));
                retList.add(vo);
            }
        }

        return retList;
    }

    private static class AppInfoVo {
        /**
         * app名称, 数据库字段：app
         */
        private String app;
        /**
         * 文档页面url, 数据库字段：doc_url
         */
        private String docUrl;
        private boolean needPassword;

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getDocUrl() {
            return docUrl;
        }

        public void setDocUrl(String docUrl) {
            this.docUrl = docUrl;
        }

        public boolean isNeedPassword() {
            return needPassword;
        }

        public void setNeedPassword(boolean needPassword) {
            this.needPassword = needPassword;
        }
    }

}
