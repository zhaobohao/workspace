package com.gitee.apiconf.api;

import com.gitee.apiconf.api.param.AppLimitUpdateForm;
import com.gitee.apiconf.api.param.AppLimitSearchParam;
import com.gitee.apiconf.api.result.LimitAppConfigVo;
import com.gitee.apiconf.entity.GlobalEnum;
import com.gitee.apiconf.entity.LimitAppConfig;
import com.gitee.apiconf.entity.PermApiInfo;
import com.gitee.apiconf.mapper.LimitAppConfigMapper;
import com.gitee.apiconf.mapper.PermApiInfoMapper;
import com.gitee.apiconf.service.GlobalConfigService;
import com.gitee.apiconf.service.SyncService;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.exception.ApiException;
import com.gitee.easyopen.limit.LimitStatus;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.PageEasyui;
import com.gitee.fastmybatis.core.util.MyBeanUtil;
import com.google.common.collect.Sets;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ApiService
public class LimitApi {

    @Autowired
    LimitAppConfigMapper limitAppConfigMapper;
    @Autowired
    PermApiInfoMapper permApiInfoMapper;

    @Autowired
    SyncService syncService;
    @Autowired
    GlobalConfigService globalConfigService;

    @Api(name = "app.limit.pagelist")
    PageEasyui<LimitAppConfigVo> appLimitPagelist(AppLimitSearchParam param) {
        Query query = Query.build(param);
        // 右关联
        query.join("right join perm_api_info t2 on t.api_id = t2.id");

        byte defLimitStatus = LimitStatus.CLOSE.getStatus();
        int defLimitCount = NumberUtils.toInt(globalConfigService.getValue(GlobalEnum.DEFAULT_LIMIT_COUNT), 50);
        String defLimitType = globalConfigService.getValue(GlobalEnum.DEFAULT_LIMIT_TYPE);
        int defTokenBucketCount = NumberUtils.toInt(globalConfigService.getValue(GlobalEnum.DEFAULT_TOKEN_BUCKET_COUNT), 50);
        List<Map<String, Object>> listMap = limitAppConfigMapper.listMap(Arrays.asList(
                "t.id"
                , "t2.id as apiId"
                , "t2.app as app"
                , "t2.name"
                , "t2.version"
                , "t2.description"
                , "case when t.limit_type is null then '" + defLimitType + "' else t.limit_type end as limitType"
                , "case when t.limit_count is null then " + defLimitCount + " else t.limit_count end as limitCount"
                , "t.limit_msg as limitMsg"
                , "t.limit_code as limitCode"
                , "case when t.token_bucket_count is null then " + defTokenBucketCount + " else t.token_bucket_count end as tokenBucketCount"
                , "case when t.status is null then " + defLimitStatus + " else t.status end as status"
        ), query);
        long total = limitAppConfigMapper.getCount(query);
        List<LimitAppConfigVo> retList = MyBeanUtil.mapListToObjList(listMap, LimitAppConfigVo.class);

        PageEasyui<LimitAppConfigVo> pageInfo = new PageEasyui<>();
        pageInfo.setList(retList);
        pageInfo.setTotal(total);

        return pageInfo;
    }

    @Api(name = "app.limit.update")
    void appLimitUpdate(AppLimitUpdateForm form) {
        String code = form.getLimitCode();
        if (NumberUtils.isNumber(code)) {
            int codeDouble = NumberUtils.toInt(code);
            if (codeDouble <= 100) {
                throw new ApiException("code值必须大于100（1~100为系统保留code）", "500");
            }
        }

        List<Long> apiIds = form.getApiIds();
        for (Long apiId : apiIds) {
            Query query = new Query();
            query.eq("api_id", apiId);
            LimitAppConfig rec = limitAppConfigMapper.getByQuery(query);
            if(rec == null) {
                PermApiInfo apiInfo = permApiInfoMapper.getById(apiId);
                rec = new LimitAppConfig();
                CopyUtil.copyProperties(form, rec);
                rec.setName(apiInfo.getName());
                rec.setVersion(apiInfo.getVersion());
                rec.setApp(apiInfo.getApp());
                rec.setApiId(apiId);
                limitAppConfigMapper.save(rec);
            } else {
                CopyUtil.copyPropertiesIgnoreNull(form, rec);
                limitAppConfigMapper.update(rec);
            }
        }

        syncService.syncLimitConfig(Sets.newHashSet(form.getApp()));
    }
}
