package org.springclouddev.integral.controller.data_dic;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.druid.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.common.tool.CommonUtil;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.secure.annotation.PreAuth;
import org.springclouddev.core.secure.utils.SecureUtil;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.jackson.JsonUtil;
import org.springclouddev.integral.entity.DataDicPrm;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.data_dic.IDataDicPrmService;
import org.springclouddev.integral.vo.data_dic.DataDicPrmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api("数据字典参数接口")
@RestController
@RequestMapping("/dataDic")
public class SysDataDicPrmController {
	
	private static Logger logger= LoggerFactory.getLogger(SysDataDicPrmController.class);
	
	@Autowired
	private IDataDicPrmService dataDicPrmService;
	
	@Autowired
	private LocalDictCache localDictCache;
	
	/*
	 * @ApiOperation("查询数据字典参数列表")
	 * 
	 * @PostMapping("qryDDs") public Object qryDataDic(@RequestParam Integer
	 * pageNum,
	 * 
	 * @RequestParam(value="code",required = false) String code,
	 * 
	 * @RequestParam(value="name",required = false) String name,
	 * 
	 * @RequestParam Integer pageSize ) { DataDicPrm sysDataDicPrm=new
	 * DataDicPrm(); sysDataDicPrm.setCode(code); sysDataDicPrm.setName(name);
	 * sysDataDicPrm.setpCode("0"); try { List<DataDicPrm>
	 * list=dataDicPrmService.qryDataDicPrms(sysDataDicPrm,pageNum,pageSize);
	 * 
	 * return R.data(list); } catch (Exception e) { // TODO: handle
	 * exception return R.fail("操作失败"); } }
	 */
	
	@ApiOperation("查询数据字典参数子项列表")
	@GetMapping("qryDDSitems")
	@PreAuth("admin:datadic:qry")
	public Object qryDataDicSubitems(@RequestParam(value="page",defaultValue ="1",required = false) Integer pageNum,
										@RequestParam(value="code",required = false) String code,
										@RequestParam(value="name",required = false) String name,
										@RequestParam(defaultValue ="0") Long pCode,
										@RequestParam(value="limit",defaultValue ="10",required = false) Integer pageSize ) {
		DataDicPrm sysDataDicPrm=new DataDicPrm();
		sysDataDicPrm.setCode(code);
		sysDataDicPrm.setName(name);
		sysDataDicPrm.setParentId(pCode);
		try {
			List<DataDicPrm> list=dataDicPrmService.qryDataDicPrms(sysDataDicPrm,pageNum,pageSize);
			for(int i=0;i<list.size();i++) {
				list.set(i, setDataDicPrm(list.get(i)));
			}

			return R.data(list);
		} catch (Exception e) {
			// TODO: handle exception
			return R.fail("操作失败");
		}

	}
	
	@ApiOperation("新增数据字典参数")
	@PostMapping("crtDD")
	@PreAuth("admin:datadic:add")
	public Object crtDD(@RequestBody DataDicPrm sysDataDicPrm) {
		String code=sysDataDicPrm.getCode();
		if(StringUtils.isEmpty(code)) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		if(dataDicPrmService.getOne(Condition.getQueryWrapper(new DataDicPrm().setCode(code)))!=null) {
			return R.fail(ReturnCode.DUOLICATE_KEY_ERROR);
		}
		sysDataDicPrm.setCreateTime(new Date());
		sysDataDicPrm.setCreateUser(SecureUtil.getUserId());
		if(sysDataDicPrm.getParentId().equals(0L)) {
			sysDataDicPrm.setParentId(0L);
		}
		try {
			if(dataDicPrmService.crtDataDicPrm(sysDataDicPrm)>0) {
				localDictCache.reload();
				return R.success("操作成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return R.fail("操作失败");
		}

		
		return R.fail(ReturnCode.CRT_DD_ERROR);
	}
	
	@ApiOperation("删除数据字典")
	@PostMapping("delDD")
	@PreAuth("admin:datadic:del")
	public Object delDD(@RequestBody String body) {
		Long id = JsonUtil.toMap(body,Long.class).get("id");
		if(id==null) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		try {
			if(dataDicPrmService.delDataDicPrmById(id)) {
				localDictCache.reload();
				return R.success("操作成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return R.fail("操作失败");
		}
		return R.fail(ReturnCode.DEL_DD_ERROR);
	}

	@ApiOperation("查询字典项详情")
	@GetMapping("qryDDById")
	public Object qryDataDicInfoById(@RequestParam(value="id") Long  id) {
		DataDicPrm sysDataDicPrm=dataDicPrmService.qryDataDicPrmById(id);
		if(sysDataDicPrm!=null) {
			sysDataDicPrm=setDataDicPrm(sysDataDicPrm);
			return R.data(sysDataDicPrm);
		}
		return R.fail(ReturnCode.QRY_DD_BY_ID_ERROR);
	}

	@ApiOperation("修改字典项详情")
	@PostMapping("updDD")
	@PreAuth("admin:datadic:upd")
	public Object updDD(@RequestBody DataDicPrm sysDataDicPrm) {

		sysDataDicPrm.setUpdateUser(SecureUtil.getUserId());
		if(dataDicPrmService.updDataDicPrm(sysDataDicPrm)>0) {
			localDictCache.reload();
			return R.success("操作成功");
		}
		return R.fail(ReturnCode.UPD_DD_ERROR);
	}


	
	
	private static DataDicPrm setDataDicPrm(DataDicPrm sysDataDicPrm) {
		if(sysDataDicPrm==null) {
			return null;
		}
		DataDicPrmVo dataDicPrmVo=new DataDicPrmVo();
		BeanUtil.copyProperties(sysDataDicPrm, dataDicPrmVo);
		dataDicPrmVo.setpName(LocalDictCache.getDicNameById(String.valueOf(dataDicPrmVo.getParentId())));
		return dataDicPrmVo;
	}

	
}
