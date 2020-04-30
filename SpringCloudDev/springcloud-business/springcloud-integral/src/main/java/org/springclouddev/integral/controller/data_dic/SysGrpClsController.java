package org.springclouddev.integral.controller.data_dic;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.druid.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springclouddev.common.tool.CommonUtil;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.jackson.JsonUtil;
import org.springclouddev.integral.entity.GrpCls;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.data_dic.IGrpClsService;
import org.springclouddev.integral.vo.data_dic.GrpClsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api("系统组群接口")
@RestController
@RequestMapping("/grpCls")
public class SysGrpClsController {

	@Autowired
	private IGrpClsService grpClsService;
	
	
	@ApiOperation("查询组群列表")
	@GetMapping("qryGrpClses")
	public Object qryGrpClses(@RequestParam(value="page",defaultValue ="1",required = false) Integer pageNum,
                              @RequestParam(value="limit",defaultValue ="10",required = false) Integer pageSize,
                              @RequestParam(value="code",required = false) String code, @RequestParam(value="name",required = false) String name,
                              @RequestParam(value="type",required = false) String type,
                              @RequestParam(value="listId",required=false) String listId,
                              @RequestParam(value="isInList",required=false) String isInList) {
		GrpCls grpCls=new GrpCls();
		grpCls.setCode(code);
		grpCls.setName(name);
		grpCls.setListId(listId);
		grpCls.setType(type);
		List<GrpCls> list=grpClsService.qryGrpClses(grpCls, pageNum, pageSize,isInList);
		for(int i=0;i<list.size();i++) {
			list.set(i, setGrpClsVo(list.get(i)));
		}
		return R.data(list);

	}
	
	@ApiOperation("查询组群详情")
	@GetMapping("qryGrpClsById")
	public Object qryGrpClsById(@RequestParam Long id){
		if(id==null||id<1) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		GrpCls grpCls=setGrpClsVo(grpClsService.qryGrpClsById(id));
		return R.data(grpCls);
	}
	
	@ApiOperation("新建组群")
	@PostMapping("crtQryCls")
	public Object crtQryCls(@RequestBody GrpCls grpCls) {
		if(StringUtils.isEmpty(grpCls.getCode())) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		if(grpClsService.crtGrpCls(grpCls)<1) {
			return R.fail("操作失败");
		}
		return R.success("操作成功");
	}
	@ApiOperation("删除组群")
	@PostMapping("delGrpClsById")
	public Object delGrpClsById(@RequestParam Long id) {
		if(id==null||id<1) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		if(!grpClsService.delGrpClsById(id)) {
			return R.fail("操作失败");
		}
		return R.success("操作成功");
	}
	
	@ApiOperation("修改组群")
	@PostMapping("updGrpClsById")
	public Object updGrpClsById(@RequestBody GrpCls grpCls) {
		if(grpCls==null||grpCls.getId()==null||grpCls.getId()<1) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		if(grpClsService.updGrpCls(grpCls)<1) {
			return R.fail("操作失败");
		}
		return R.success("操作成功");
	}
	
	@ApiOperation("组群加入集合")
	@PostMapping("clsesIntoList")
	public Object clsesIntoList(@RequestBody String body) {
        String listId = JsonUtil.getString(body, "ListId");
        Long[] ids = JsonUtil.getLongArr(body, "ids");
        if(ids!=null) {
        	for(int i=0;i<ids.length;i++) {
        		GrpCls grpCls=grpClsService.qryGrpClsById(ids[i]);
        		if(grpCls!=null) {
        			grpCls.setListId(grpCls.getListId().concat(","+listId));
        		}
        		grpClsService.updGrpCls(grpCls);
        	}
        }
		
		return R.success("操作成功");
	}
	
	
	@ApiOperation("组群退出集合")
	@PostMapping("clsesPutOutList")
	public Object clsesPutOutList(@RequestBody String body) {
        String listId = JsonUtil.getString(body, "listId");
        Long[] ids = JsonUtil.getLongArr(body, "ids");
        if(ids!=null) {
        	for(int i=0;i<ids.length;i++) {
        		GrpCls grpCls=grpClsService.qryGrpClsById(ids[i]);
        		if(grpCls!=null) {
        			grpCls.setListId(grpCls.getListId().replaceFirst(","+listId+",", ","));
        		}
        		grpClsService.updGrpCls(grpCls);
        	}
        }
		
		return R.success("操作成功");
	}
	
	
	private static GrpCls setGrpClsVo(GrpCls grpCls) {
		if(grpCls==null) {
			return null;
		}
		GrpClsVo grpClsVo=new GrpClsVo();
		BeanUtil.copyProperties(grpCls, grpClsVo);
		grpClsVo.setSortName(LocalDictCache.getDicNameById(grpClsVo.getType()));
		
		return grpClsVo;
	}
}
