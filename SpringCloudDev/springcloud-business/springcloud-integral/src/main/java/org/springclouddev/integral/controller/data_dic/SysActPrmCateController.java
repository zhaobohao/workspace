package org.springclouddev.integral.controller.data_dic;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.druid.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.core.secure.annotation.PreAuth;
import org.springclouddev.core.secure.utils.SecureUtil;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.jackson.JsonUtil;
import org.springclouddev.integral.entity.ActPrmCate;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.config.ActPrmCache;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.data_dic.IActPrmCateService;
import org.springclouddev.integral.vo.data_dic.ActPrmCateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Api("系统活动参数类别项接口")
@RestController
@RequestMapping("/actPrmCate")
public class SysActPrmCateController {
	private static Logger logger= LoggerFactory.getLogger(SysActPrmCateController.class);
	
	@Autowired
	private IActPrmCateService actPrmCateService;
	
	
	@ApiOperation("根据活动参数Code查询活动参数类别项列表")
	@GetMapping("qryActPrmCates")
	@PreAuth("admin:actprmcate:qry")
	public Object qryActPrmCates(@RequestParam(value="page",defaultValue ="1",required = false) Integer pageNum,
			@RequestParam(value="limit",defaultValue ="10",required = false) Integer pageSize,
			@RequestParam(value="apCode") String apCode) {
		if(StringUtils.isEmpty(apCode)) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		List<ActPrmCate> list=actPrmCateService.qryCatesByApCode(apCode, pageNum, pageSize);
		for(int i=0;i<list.size();i++) {
			list.set(i, setActPrmCateVo(list.get(i)));
		}
		Collections.sort(list, new Comparator<ActPrmCate>() {

			@Override
			public int compare(ActPrmCate o1, ActPrmCate o2) {
				// TODO Auto-generated method stub
				return o1.getApOrder()>o2.getApOrder()?1:-1;
			}
			
		});
		return R.data(list);
		
	}
	
	@ApiOperation("新增活动参数类别项")
	@PostMapping("crtActPrmCate")
	@PreAuth("admin:actprmcate:add")
	public Object crtActPrmCate(@RequestBody ActPrmCate actPrmCate) {
		String apCode=actPrmCate.getApCode();
		String code=actPrmCate.getCode();

		if(StringUtils.isEmpty(apCode)|| StringUtils.isEmpty(code)) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		if(actPrmCateService.qryCateByApCodeAndCode(apCode, code)!=null) {
			return R.fail(ReturnCode.DUOLICATE_KEY_ERROR);
		}
		actPrmCate.setUpdateUser(SecureUtil.getUserId());
		actPrmCate.setCreateTime(new Date());
		if(actPrmCateService.crtCate(actPrmCate)==1) {
			return R.success("操作成功");
		}
		return R.fail("操作失败！");
	}
	
	@ApiOperation("查询活动参数类别项")
	@GetMapping("qryActPrmCateById")
	public Object qryActPrmCateById(@RequestParam String pCode, @RequestParam String code) {
		if(StringUtils.isEmpty(pCode)|| StringUtils.isEmpty(code)) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		ActPrmCate actPrmCate=actPrmCateService.qryCateByApCodeAndCode(pCode, code);
		if(actPrmCate!=null) {
			return R.data(setActPrmCateVo(actPrmCate));
		}
		return R.fail("操作失败！");
	}
	
	@ApiOperation("修改活动参数类别项")
	@PostMapping("updActPrmCate")
	@PreAuth("admin:actprmcate:upd")
	public Object updActPrmCate(@RequestBody ActPrmCate actPrmCate) {
		String apCode=actPrmCate.getApCode();
		String code=actPrmCate.getCode();
		if(StringUtils.isEmpty(apCode)|| StringUtils.isEmpty(code)) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		actPrmCate.setUpdateUser(SecureUtil.getUserId());
		if(actPrmCateService.updCateByApCodeAndCode(actPrmCate)>0) {
			return R.success("操作成功");
		}
		return R.fail("操作失败！");
	}
	
	@ApiOperation("删除活动参数类别项")
	@PostMapping("delActPrmCateById")
	@PreAuth("admin:actprmcate:del")
	public Object delActPrmCateById(@RequestBody String body) {
		String apCode= (String) JsonUtil.toMap(body).get("apCode");
		String code=(String) JsonUtil.toMap(body).get("code");
		if(StringUtils.isEmpty(apCode)|| StringUtils.isEmpty(code)) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		if(actPrmCateService.delCateByApCodeAndCode(apCode, code)) {
			return R.success("操作成功");
		}
		return R.fail("操作失败！");
	}
	
	
	private static ActPrmCate setActPrmCateVo(ActPrmCate actPrmCate) {
		if(actPrmCate==null) {
			return null;
		}
		ActPrmCateVo actPrmCateVo=new ActPrmCateVo();
		BeanUtil.copyProperties(actPrmCate, actPrmCateVo);
		actPrmCateVo.setStartSignName(LocalDictCache.getDicNameById(String.valueOf(actPrmCate.getStatus())));
		actPrmCateVo.setpName(ActPrmCache.getActPrmNameById(actPrmCate.getApCode()));
		return actPrmCateVo;
	}

}
