package com.gitee.sop.adminserver.api.isv;

import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.DataType;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.util.MapperUtil;
import com.gitee.sop.adminserver.api.IdParam;
import com.gitee.sop.adminserver.api.isv.param.IsvInfoFormAdd;
import com.gitee.sop.adminserver.api.isv.param.IsvInfoFormUpdate;
import com.gitee.sop.adminserver.api.isv.param.IsvKeysFormUpdate;
import com.gitee.sop.adminserver.api.isv.param.IsvKeysGen;
import com.gitee.sop.adminserver.api.isv.param.IsvPageParam;
import com.gitee.sop.adminserver.api.isv.result.IsvDetailDTO;
import com.gitee.sop.adminserver.api.isv.result.IsvInfoVO;
import com.gitee.sop.adminserver.api.isv.result.IsvKeysGenVO;
import com.gitee.sop.adminserver.api.isv.result.IsvKeysVO;
import com.gitee.sop.adminserver.api.isv.result.RoleVO;
import com.gitee.sop.adminserver.bean.ChannelMsg;
import com.gitee.sop.adminserver.bean.NacosConfigs;
import com.gitee.sop.adminserver.common.BizException;
import com.gitee.sop.adminserver.common.ChannelOperation;
import com.gitee.sop.adminserver.common.IdGen;
import com.gitee.sop.adminserver.common.RSATool;
import com.gitee.sop.adminserver.entity.IsvInfo;
import com.gitee.sop.adminserver.entity.IsvKeys;
import com.gitee.sop.adminserver.entity.PermIsvRole;
import com.gitee.sop.adminserver.entity.PermRole;
import com.gitee.sop.adminserver.mapper.IsvInfoMapper;
import com.gitee.sop.adminserver.mapper.IsvKeysMapper;
import com.gitee.sop.adminserver.mapper.PermIsvRoleMapper;
import com.gitee.sop.adminserver.mapper.PermRoleMapper;
import com.gitee.sop.adminserver.service.ConfigPushService;
import com.gitee.sop.adminserver.service.RoutePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
@ApiService
@ApiDoc("ISV管理")
@Slf4j
public class IsvApi {

    public static final byte SIGN_TYPE_RSA = 1;
    public static final byte SIGN_TYPE_MD5 = 2;

    static Map<String, Byte> SIGN_TYPE_MAP = new HashMap<>();
    static {
        SIGN_TYPE_MAP.put("rsa", (byte) SIGN_TYPE_RSA);
        SIGN_TYPE_MAP.put("md5", (byte) SIGN_TYPE_MD5);
    }

    @Autowired
    IsvInfoMapper isvInfoMapper;

    @Autowired
    IsvKeysMapper isvKeysMapper;

    @Autowired
    PermIsvRoleMapper permIsvRoleMapper;

    @Autowired
    PermRoleMapper permRoleMapper;

    @Autowired
    RoutePermissionService routePermissionService;

    @Autowired
    private ConfigPushService configPushService;

    @Value("${sop.sign-type}")
    private String sopSignType;

    @Api(name = "isv.info.page")
    @ApiDocMethod(description = "isv列表", results = {
            @ApiDocField(name = "pageIndex", description = "第几页", dataType = DataType.INT, example = "1"),
            @ApiDocField(name = "pageSize", description = "每页几条数据", dataType = DataType.INT, example = "10"),
            @ApiDocField(name = "total", description = "每页几条数据", dataType = DataType.LONG, example = "100"),
            @ApiDocField(name = "rows", description = "数据", dataType = DataType.ARRAY, elementClass = IsvInfoVO.class)
    })
    PageInfo<IsvInfoVO> pageIsv(IsvPageParam param) {
        Query query = Query.build(param);
        query.orderby("id", Sort.DESC);
        PageInfo<IsvInfo> pageInfo = MapperUtil.query(isvInfoMapper, query);
        List<IsvInfo> list = pageInfo.getList();

        List<IsvInfoVO> retList = list.stream()
                .map(isvInfo -> {
                    return buildIsvVO(isvInfo);
                })
                .collect(Collectors.toList());

        PageInfo<IsvInfoVO> pageInfoRet = new PageInfo<>();
        pageInfoRet.setTotal(pageInfo.getTotal());
        pageInfoRet.setList(retList);

        return pageInfoRet;
    }

    @Api(name = "isv.info.get")
    @ApiDocMethod(description = "获取isv")
    IsvInfoVO getIsvVO(IdParam param) {
        IsvInfo isvInfo = isvInfoMapper.getById(param.getId());
        return buildIsvVO(isvInfo);
    }

    @Api(name = "isv.keys.get")
    @ApiDocMethod(description = "获取isv2")
    IsvKeysVO getIsvKeys(@NotBlank(message = "appKey不能为空")
                         @ApiDocField(description = "appKey")
                                 String appKey) {
        IsvKeys isvKeys = isvKeysMapper.getByColumn("app_key", appKey);
        IsvKeysVO isvDetailVO = new IsvKeysVO();
        if (isvKeys != null) {
            CopyUtil.copyProperties(isvKeys, isvDetailVO);
        }
        isvDetailVO.setAppKey(appKey);
        isvDetailVO.setSignType(getSignType());
        return isvDetailVO;
    }

    private IsvInfoVO buildIsvVO(IsvInfo isvInfo) {
        if (isvInfo == null) {
            return null;
        }
        IsvInfoVO vo = new IsvInfoVO();
        CopyUtil.copyProperties(isvInfo, vo);
        vo.setRoleList(this.buildIsvRole(isvInfo));
        return vo;
    }

    /**
     * 构建ISV拥有的角色
     *
     * @param permClient
     * @return
     */
    List<RoleVO> buildIsvRole(IsvInfo permClient) {
        List<String> roleCodeList = routePermissionService.listClientRoleCode(permClient.getId());
        if (CollectionUtils.isEmpty(roleCodeList)) {
            return Collections.emptyList();
        }
        List<PermRole> list = permRoleMapper.list(new Query().in("role_code", roleCodeList));

        return list.stream()
                .map(permRole -> {
                    RoleVO vo = new RoleVO();
                    CopyUtil.copyProperties(permRole, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Api(name = "isv.info.add")
    @ApiDocMethod(description = "添加isv")
    @Transactional(rollbackFor = Exception.class)
    public void addIsv(IsvInfoFormAdd param) throws Exception {
        String appKey = new SimpleDateFormat("yyyyMMdd").format(new Date()) + IdGen.nextId();
        IsvInfo rec = new IsvInfo();
        rec.setAppKey(appKey);
        CopyUtil.copyPropertiesIgnoreNull(param, rec);
        isvInfoMapper.saveIgnoreNull(rec);
        if (CollectionUtils.isNotEmpty(param.getRoleCode())) {
            this.saveIsvRole(rec, param.getRoleCode());
        }
        IsvKeysGenVO isvKeysGenVO = this.createIsvKeys();
        IsvKeys isvKeys = new IsvKeys();
        isvKeys.setAppKey(appKey);
        isvKeys.setSignType(getSignType());
        CopyUtil.copyPropertiesIgnoreNull(isvKeysGenVO, isvKeys);
        isvKeysMapper.saveIgnoreNull(isvKeys);

        this.sendChannelMsg(rec.getAppKey());
    }

    private byte getSignType() {
        return SIGN_TYPE_MAP.getOrDefault(sopSignType, SIGN_TYPE_RSA);
    }

    @Api(name = "isv.info.update")
    @ApiDocMethod(description = "修改isv")
    @Transactional(rollbackFor = Exception.class)
    public void updateIsv(IsvInfoFormUpdate param) {
        IsvInfo rec = isvInfoMapper.getById(param.getId());
        CopyUtil.copyPropertiesIgnoreNull(param, rec);
        isvInfoMapper.updateIgnoreNull(rec);
        this.saveIsvRole(rec, param.getRoleCode());

        this.sendChannelMsg(rec.getAppKey());
    }

    @Api(name = "isv.keys.update")
    @ApiDocMethod(description = "修改isv")
    public void updateIsvKeys(IsvKeysFormUpdate param) {
        IsvKeys isvKeys = isvKeysMapper.getByColumn("app_key", param.getAppKey());
        if (isvKeys == null) {
            isvKeys = new IsvKeys();
            CopyUtil.copyPropertiesIgnoreNull(param, isvKeys);
            isvKeys.setSignType(getSignType());
            isvKeysMapper.saveIgnoreNull(isvKeys);
        } else {
            CopyUtil.copyPropertiesIgnoreNull(param, isvKeys);
            isvKeysMapper.updateIgnoreNull(isvKeys);
        }

        this.sendChannelMsg(isvKeys.getAppKey());
    }

    private void sendChannelMsg(String appKey) {
        IsvDetailDTO isvDetail = isvInfoMapper.getIsvDetail(appKey);
        if (isvDetail == null) {
            return;
        }
        ChannelMsg channelMsg = new ChannelMsg(ChannelOperation.ISV_INFO_UPDATE, isvDetail);
        configPushService.publishConfig(NacosConfigs.DATA_ID_ISV, NacosConfigs.GROUP_CHANNEL, channelMsg);
    }

    private IsvKeysGenVO createIsvKeys() throws Exception {
        IsvKeysGenVO isvFormVO = new IsvKeysGenVO();
        String secret = IdGen.uuid();

        isvFormVO.setSecret(secret);

        RSATool rsaToolIsv = new RSATool(RSATool.KeyFormat.PKCS8, RSATool.KeyLength.LENGTH_2048);
        RSATool.KeyStore keyStoreIsv = rsaToolIsv.createKeys();
        isvFormVO.setPublicKeyIsv(keyStoreIsv.getPublicKey());
        isvFormVO.setPrivateKeyIsv(keyStoreIsv.getPrivateKey());

        isvFormVO.setPublicKeyPlatform("");
        isvFormVO.setPrivateKeyPlatform("");
        return isvFormVO;
    }

    @Api(name = "isv.keys.gen")
    @ApiDocMethod(description = "生成公私钥")
    RSATool.KeyStore createPubPriKey(IsvKeysGen param) throws Exception {
        RSATool.KeyFormat format = RSATool.KeyFormat.PKCS8;
        Byte keyFormat = param.getKeyFormat();
        if (keyFormat != null && keyFormat == 2) {
            format = RSATool.KeyFormat.PKCS1;
        }
        RSATool rsaTool = new RSATool(format, RSATool.KeyLength.LENGTH_2048);
        return rsaTool.createKeys();
    }

    @Api(name = "isv.secret.gen")
    @ApiDocMethod(description = "生成MD秘钥")
    String createSecret() throws Exception {
        return IdGen.uuid();
    }

    void saveIsvRole(IsvInfo isvInfo, List<String> roleCodeList) {
        Query query = new Query();
        long isvInfoId = isvInfo.getId();
        query.eq("isv_id", isvInfoId);
        permIsvRoleMapper.deleteByQuery(query);

        List<PermIsvRole> tobeSaveList = roleCodeList.stream()
                .map(roleCode -> {
                    PermIsvRole rec = new PermIsvRole();
                    rec.setIsvId(isvInfoId);
                    rec.setRoleCode(roleCode);
                    return rec;
                })
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(tobeSaveList)) {
            permIsvRoleMapper.saveBatch(tobeSaveList);
        }

        try {
            routePermissionService.sendIsvRolePermissionMsg(isvInfo.getAppKey(), roleCodeList);
        } catch (Exception e) {
            log.error("同步角色失败，isvInfo:{}, roleCodeList:{}", isvInfo, roleCodeList);
            throw new BizException("同步角色失败，请查看网关日志");
        }
    }
}
