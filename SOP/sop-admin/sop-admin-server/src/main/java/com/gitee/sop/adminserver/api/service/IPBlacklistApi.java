package com.gitee.sop.adminserver.api.service;

import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.support.PageEasyui;
import com.gitee.fastmybatis.core.util.MapperUtil;
import com.gitee.sop.adminserver.api.service.param.ConfigIpBlackForm;
import com.gitee.sop.adminserver.api.service.param.ConfigIpBlacklistPageParam;
import com.gitee.sop.adminserver.api.service.result.ConfigIpBlacklistVO;
import com.gitee.sop.adminserver.bean.ChannelMsg;
import com.gitee.sop.adminserver.bean.NacosConfigs;
import com.gitee.sop.adminserver.common.BizException;
import com.gitee.sop.adminserver.common.ChannelOperation;
import com.gitee.sop.adminserver.entity.ConfigIpBlacklist;
import com.gitee.sop.adminserver.mapper.ConfigIpBlacklistMapper;
import com.gitee.sop.adminserver.service.ConfigPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tanghc
 */
@ApiService
@ApiDoc("IP黑名单管理")
@Slf4j
public class IPBlacklistApi {

    @Autowired
    ConfigIpBlacklistMapper configIpBlacklistMapper;

    @Autowired
    private ConfigPushService configPushService;

    @ApiDocMethod(description = "获取IP黑名单，分页")
    @Api(name = "ip.blacklist.page")
    PageEasyui<ConfigIpBlacklistVO> page(ConfigIpBlacklistPageParam form) {
        Query query = Query.build(form);
        query.orderby("id", Sort.DESC);
        return MapperUtil.queryForEasyuiDatagrid(configIpBlacklistMapper, query, ConfigIpBlacklistVO.class);
    }

    @ApiDocMethod(description = "IP黑名单--新增")
    @Api(name = "ip.blacklist.add")
    void add(ConfigIpBlackForm form) {
        ConfigIpBlacklist rec = configIpBlacklistMapper.getByColumn("ip", form.getIp());
        if (rec != null) {
            throw new BizException("IP已添加");
        }
        rec = new ConfigIpBlacklist();
        CopyUtil.copyPropertiesIgnoreNull(form, rec);
        configIpBlacklistMapper.saveIgnoreNull(rec);
        try {
            this.sendIpBlacklistMsg(rec, ChannelOperation.BLACKLIST_ADD);
        } catch (Exception e) {
            log.error("推送IP黑名单失败, rec:{}",rec, e);
            throw new BizException("推送IP黑名单失败");
        }
    }

    @ApiDocMethod(description = "IP黑名单--修改")
    @Api(name = "ip.blacklist.update")
    void update(ConfigIpBlackForm form) {
        ConfigIpBlacklist rec = configIpBlacklistMapper.getById(form.getId());
        CopyUtil.copyPropertiesIgnoreNull(form, rec);
        configIpBlacklistMapper.updateIgnoreNull(rec);
    }

    @ApiDocMethod(description = "IP黑名单--删除")
    @Api(name = "ip.blacklist.del")
    void del(long id) {
        ConfigIpBlacklist rec = configIpBlacklistMapper.getById(id);
        if (rec == null) {
            return;
        }
        configIpBlacklistMapper.deleteById(id);
        try {
            this.sendIpBlacklistMsg(rec, ChannelOperation.BLACKLIST_DELETE);
        } catch (Exception e) {
            log.error("推送IP黑名单失败, rec:{}",rec, e);
            throw new BizException("推送IP黑名单失败");
        }
    }

    public void sendIpBlacklistMsg(ConfigIpBlacklist configIpBlacklist, ChannelOperation channelOperation) {
        ChannelMsg channelMsg = new ChannelMsg(channelOperation, configIpBlacklist);
        configPushService.publishConfig(NacosConfigs.DATA_ID_IP_BLACKLIST, NacosConfigs.GROUP_CHANNEL, channelMsg);
    }

}
