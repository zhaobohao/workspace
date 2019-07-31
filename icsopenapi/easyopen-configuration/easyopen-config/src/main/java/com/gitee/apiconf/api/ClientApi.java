package com.gitee.apiconf.api;

import com.gitee.apiconf.api.param.AppClientForm;
import com.gitee.apiconf.api.param.AppParam;
import com.gitee.apiconf.api.param.ClientInfoPageParam;
import com.gitee.apiconf.api.result.AppKeySecretVo;
import com.gitee.apiconf.api.result.ClientVo;
import com.gitee.apiconf.api.result.PubPriVo;
import com.gitee.apiconf.api.result.RoleVo;
import com.gitee.apiconf.common.IdGen;
import com.gitee.apiconf.entity.PermClient;
import com.gitee.apiconf.entity.PermClientRole;
import com.gitee.apiconf.entity.PermRole;
import com.gitee.apiconf.mapper.PermClientMapper;
import com.gitee.apiconf.mapper.PermClientRoleMapper;
import com.gitee.apiconf.mapper.PermRoleMapper;
import com.gitee.apiconf.service.PermService;
import com.gitee.apiconf.service.SyncService;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.easyopen.util.KeyStore;
import com.gitee.easyopen.util.RSAUtil;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.PageEasyui;
import com.gitee.fastmybatis.core.util.MapperUtil;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApiService
@ApiDoc("接入方管理")
public class ClientApi {

	@Autowired
	PermClientMapper permClientMapper;
	@Autowired
	PermClientRoleMapper permClientRoleMapper;
	@Autowired
	PermRoleMapper permRoleMapper;
	@Autowired
	PermService permService;

	@Autowired
	SyncService syncService;
	
	@SuppressWarnings({ "rawtypes"})
	@Api(name = "app.client.list")
	@ApiDocMethod(description = "接入方列表")
	PageEasyui pageClient(ClientInfoPageParam param) {
		Query query = Query.build(param);
		PageInfo<PermClient> pageInfo = MapperUtil.query(permClientMapper, query);
		List<PermClient> list = pageInfo.getList();

		List<ClientVo>	retList = new ArrayList<>(list.size());

		for (PermClient permClient : list) {
			ClientVo vo = new ClientVo();
			CopyUtil.copyProperties(permClient, vo);
			vo.setRoleList(this.buildClientRole(permClient));
			retList.add(vo);
		}

		PageEasyui<ClientVo> pageInfoRet = new PageEasyui<>();
		pageInfoRet.setTotal(pageInfo.getTotal());
		pageInfoRet.setList(retList);

		return pageInfoRet;
	}

	List<RoleVo> buildClientRole(PermClient permClient) {
		List<String> roleCodeList = permService.listClientRoleCode(permClient.getId());
		if(CollectionUtils.isEmpty(roleCodeList)) {
			return Collections.emptyList();
		}
		List<PermRole> list = permRoleMapper.list(new Query().in("role_code", roleCodeList));

		List<RoleVo> retList = new ArrayList<>(list.size());

		for (PermRole permRole : list) {
			RoleVo vo = new RoleVo();
			CopyUtil.copyProperties(permRole, vo);
			retList.add(vo);
		}

		return retList;
	}

	@Api(name = "app.baseinfo.load")
	PermClient loadBaseInfo(AppParam param) {
		return permClientMapper.getByColumn("app", param.getApp());
	}
	
	@Api(name = "client.pubprikey.create")
	PubPriVo createPubPriKey() throws Exception {
		KeyStore keyStore = RSAUtil.createKeys();
		PubPriVo vo = new PubPriVo();
		vo.setPubKey(keyStore.getPublicKey());
		vo.setPriKey(keyStore.getPrivateKey());
		return vo;
	}

	@Api(name = "client.appkeysecret.create")
	AppKeySecretVo createAppKeySecret() {
		String appKey = IdGen.nextId();
		String secret = IdGen.uuid();
		AppKeySecretVo vo = new AppKeySecretVo();
		vo.setAppKey(appKey);
		vo.setSecret(secret);
		return vo;
	}

	@Api(name = "app.client.add")
	void addClient(AppClientForm param) {
		PermClient rec = new PermClient();
		CopyUtil.copyPropertiesIgnoreNull(param, rec);
		permClientMapper.saveIgnoreNull(rec);

		this.saveClientRole(rec, param.getRoleCode());

		syncService.syncAppSecretConfig(Sets.newHashSet(param.getApp()));
	}
	
	@Api(name = "app.client.update")
	void updateClient(AppClientForm param) {
		PermClient rec = permClientMapper.getById(param.getId());
		CopyUtil.copyPropertiesIgnoreNull(param, rec);
		permClientMapper.updateIgnoreNull(rec);

		this.saveClientRole(rec, param.getRoleCode());

		syncService.syncAppSecretConfig(Sets.newHashSet(param.getApp()));
	}

	void saveClientRole(PermClient client, List<String> roleCodeList) {
		Query query = new Query();
		query.eq("client_id", client.getId());
		permClientRoleMapper.deleteByQuery(query);

		List<PermClientRole> tobeSaveList = new ArrayList<>(roleCodeList.size());
		for (String roleCode : roleCodeList) {
			PermClientRole rec = new PermClientRole();
			rec.setClientId(client.getId());
			rec.setRoleCode(roleCode);
			tobeSaveList.add(rec);
		}
		permClientRoleMapper.saveBatch(tobeSaveList);
	}

}
