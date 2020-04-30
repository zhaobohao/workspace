package org.springclouddev.integral.controller.data_dic;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.core.secure.annotation.PreAuth;
import org.springclouddev.core.secure.utils.SecureUtil;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.jackson.JsonUtil;
import org.springclouddev.integral.entity.ActPrm;
import org.springclouddev.integral.entity.ActPrmCate;
import org.springclouddev.integral.entity.DataDicPrm;
import org.springclouddev.integral.entity.RuleInfo;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.config.ActPrmCache;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.data_dic.IActPrmCateService;
import org.springclouddev.integral.service.data_dic.IActPrmService;
import org.springclouddev.integral.service.data_dic.IDataDicPrmService;
import org.springclouddev.integral.service.ruleInfo.IRuleInfoService;
import org.springclouddev.integral.vo.data_dic.ActPrmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Api("系统活动参数接口")
@RestController
@RequestMapping("/actPrm")
public class SysActPrmController {
	
	private static Logger logger= LoggerFactory.getLogger(SysActPrmController.class);
	
	@Autowired
	private IActPrmService actPrmService;
	@Autowired
	private IDataDicPrmService dataDicService;
	
	@Autowired
	private IActPrmCateService actPrmCateService;
	
	@Autowired
	private ActPrmCache actPrmCache;
	
	@Autowired
	private IRuleInfoService ruleInfoService;
	
	@ApiOperation("查询全量活动列表")
	@GetMapping("qryAll")
	public Object qryAll() {
		List<ActPrm> list =actPrmService.qryAllStartActPrms();
		for(int i=0;i<list.size();i++) {
			list.set(i, setActPrmVo(list.get(i)));
		}
		Collections.sort(list, new Comparator<ActPrm>() {
			@Override
			public int compare(ActPrm o1, ActPrm o2) {
				// TODO Auto-generated method stub
				return o1.getSort()>o2.getSort()?1:-1;
			}
		});
		return R.data(list);
		
	}
	
	
	@ApiOperation("查询活动参数列表")
	@GetMapping("qryActPrms")
	@PreAuth("admin:actprm:qry")
	public Object qryActPrms(@RequestParam(value="page",defaultValue ="1",required = false) Integer pageNum,
								@RequestParam(value="limit",defaultValue ="10",required = false) Integer pageSize,
								@RequestParam(value="code",required = false) String code,
								@RequestParam(value="name",required = false) String name,
								@RequestParam(value="sysId",required = false) String sysId,
								@RequestParam(value="parentCoderId",required = false) String parentCoderId) {
		ActPrm actPrm=new ActPrm();
		actPrm.setCode(code);
		actPrm.setName(name);
		actPrm.setSysId(sysId);
		actPrm.setParentCoderId(parentCoderId);
		List<ActPrm> list =actPrmService.qryActPrms(actPrm, pageNum, pageSize);
		for(int i=0;i<list.size();i++) {
			list.set(i, setActPrmVo(list.get(i)));
		}
		Collections.sort(list, new Comparator<ActPrm>() {
			@Override
			public int compare(ActPrm o1, ActPrm o2) {
				// TODO Auto-generated method stub
				return o1.getSort()>o2.getSort()?1:-1;
			}
		});
		return R.data(list);
	}
	
	@ApiOperation("新增活动参数")
	@PostMapping("crtActPrm")
	@PreAuth("admin:actprm:add")
	public Object crtActPrm(@RequestBody ActPrm actPrm) {
		String code=actPrm.getCode();
		if(StringUtils.isEmpty(code)) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		if(actPrmService.qryActPrmById(code)!=null) {
			return R.fail(ReturnCode.DUOLICATE_KEY_ERROR);
		}
		if(StringUtils.isEmpty(actPrm.getParentValue())){
			actPrm.setParentValue(actPrm.getCode());
		}
		actPrm.setUpdateUser(SecureUtil.getUserId());
		actPrm.setUpdateTime(LocalDateTime.now());
		if(actPrmService.crtActPrm(actPrm)==1) {
			actPrmCache.reload();
			return R.success("操作成功");
		}
		return R.fail("操作失败");
	}
	
	
	@ApiOperation("查询活动参数详情")
	@GetMapping("qryActPrmById")
	public Object qryActPrmById(@RequestParam String code) {
		if(StringUtils.isEmpty(code)) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		ActPrm actPrm=actPrmService.qryActPrmById(code);;
		if(actPrm!=null) {
			return R.data(setActPrmVo(actPrm));
		}
		
		return R.fail("操作失败");
	}
	
	@ApiOperation("修改活动参数")
	@PostMapping("updActPrm")
	@PreAuth("admin:actprm:upd")
	public Object updActPrm(@RequestBody ActPrm actPrm) {
		String code=actPrm.getCode();
		if(StringUtils.isEmpty(code)) {
			return R.fail(ReturnCode.MISS_MAST_FIELD);
		}
		actPrm.setUpdateUser(SecureUtil.getUserId());
		if(actPrmService.updActPrm(actPrm)>0) {
			actPrmCache.reload();
			return R.success("操作成功");
		}
		
		return R.fail("操作失败");
		
	}
	
	@ApiOperation("删除活动参数")
	@PostMapping("delActPrmById")
	@PreAuth("admin:actprm:del")
	public Object delActPrmById(@RequestBody String body) {
		Long id= JsonUtil.toMap(body,Long.class).get("id");
		if(actPrmService.delActPrm(id)) {
			actPrmCache.reload();
			return R.success("操作成功");
		}
		
		return R.fail("操作失败");
	}
	
	
	@ApiOperation("获取活动参数及其子项内容列表")
	@GetMapping("qryActPrmsAndCates")
	public Object qryActPrmsAndCates(@RequestParam(required = false) Integer ruleId) {
		List<ActPrm> actPrms=actPrmService.qryAllStartActPrms();
		Collections.sort(actPrms, new Comparator<ActPrm>() {
			@Override
			public int compare(ActPrm o1, ActPrm o2) {
				// TODO Auto-generated method stub
				int i=1;
				try {
					i=o1.getSelectorModeId().hashCode()>o2.getSelectorModeId().hashCode()?1:-1;
				} catch (Exception e) {
					logger.error(o1.getCode()+"或"+o2.getCode()+"缺少selectormodeid");
				}
				return i;
			}
		});
		List<Map> maplList=new ArrayList<Map>();
		Map endMap=new HashMap();
		if(ruleId!=null) {
			RuleInfo ruleInfo=ruleInfoService.selectRuleInfo(Long.valueOf(ruleId));
			if(ruleInfo!=null) {
				String exp=ruleInfo.getRuleExp();
				if(exp != null) {
					maplList = JSONObject.parseArray(exp, Map.class);
					if (maplList.size() > 0) {
						endMap = maplList.get(maplList.size() - 1);
//					 System.out.println("endMap="+endMap);
					}

				}
			}
		}
		
		List<DataDicPrm> parentCoderDDList=dataDicService.qryDataDicPrmsByPcode("parent_coder");
		ArrayList<Map<String, Object>> resultList=new ArrayList<Map<String, Object>>();
		
//		for(DataDicPrm DataDicPrm:parentCoderDDList) {
//			Map<String, Object> ddMap=new HashMap<String, Object>();
//			ddMap.put("label",DataDicPrm.getName());
//			ddMap.put("type", DataDicPrm.getCode());
//			ddMap.put("data",new ArrayList<Map<String,Object>>());
//			resultList.add(ddMap);		
//			}
		
		addDataDicToList(resultList, parentCoderDDList);
		for(ActPrm actPrm:actPrms) {
			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("label", actPrm.getName());
//			map.put("value", actPrm.getCode());
//			
//			map.put("dateType", LocalDictCache.getDicNameById(actPrm.getDateType()));
//			if(StringUtils.equals(LocalDictCache.getDicNameById(actPrm.getDateType()), "date")||StringUtils.equals(LocalDictCache.getDicNameById(actPrm.getDateType()), "daterange"))
//			{
//				map.put("valueFormat","yyyy-MM-dd");
//			}else if(StringUtils.equals(LocalDictCache.getDicNameById(actPrm.getDateType()), "time")) {
//				map.put("valueFromat","HH:mm:ss");
//			}
//			else {
//				map.put("valueFormat","yyyy-MM-dd HH:mm:ss");
//			}
//			map.put("condType",getCondType(actPrm.getConditionTypeId()));
			
			setBaseActPrmMap(map, actPrm);
			map.put("type", LocalDictCache.getDicNameById(actPrm.getSelectorModeId()));
		
			map.put("dateType", LocalDictCache.getDicNameById(actPrm.getDateType()));
			map.put("condType",getCondType(actPrm.getConditionTypeId()));
			setItemValue(maplList, map, actPrm);
			//map.put("itemValue",getItemValue(maplList,LocalDictCache.getDicNameById(actPrm.getParentCoderId()),actPrm.getCode()));
			
			List<ActPrmCate> actPrmCates=actPrmCateService.qryCatesByApCode(actPrm.getCode(), -1, -1);
			setSubItems(map, actPrmCates);
			
//			List<Map<String, Object>> subitems=new ArrayList<Map<String,Object>>();
//			for(ActPrmCate actPrmCate:actPrmCates) {
//				Map<String, Object> cateMap=new HashMap<String, Object>();
//				cateMap.put("key", actPrmCate.getCode());
//				cateMap.put("value", actPrmCate.getName());
//				subitems.add(cateMap);
//			}
//			map.put("subItems", subitems);
			for(Map<String, Object> m:resultList) {
				if(((String)m.get("type")).equals(actPrm.getParentCoderId())){
					((ArrayList<Map<String,Object>>)(m.get("data"))).add(map);
				}
			}
		}
		if(!endMap.isEmpty()) {
			resultList.add(endMap);
		}
		
		JSONArray array= JSON.parseArray(JSONObject.toJSONString(resultList));
		return R.data(array);
		
	}
	
	@ApiOperation("获取试算活动参数及其子项内容列表")
	@GetMapping("qryActPrmsAndCatesForTrial")
	public Object qryActPrmsAndCatesForTrial() {
		List<ActPrm> actPrms=actPrmService.qryAllStartActPrms();
		Collections.sort(actPrms, new Comparator<ActPrm>() {
			@Override
			public int compare(ActPrm o1, ActPrm o2) {
				// TODO Auto-generated method stub
				int i=1;
				try {
					i=o1.getSelectorModeId().hashCode()>o2.getSelectorModeId().hashCode()?1:-1;
				} catch (Exception e) {
					logger.error(o1.getCode()+"或"+o2.getCode()+"缺少selectormodeid");
				}
				return i;
			}
		});

		
		List<DataDicPrm> parentCoderDDList=dataDicService.qryDataDicPrmsByPcode("parent_coder");
		ArrayList<Map<String, Object>> resultList=new ArrayList<Map<String, Object>>();
//		for(DataDicPrm DataDicPrm:parentCoderDDList) {
//			Map<String, Object> ddMap=new HashMap<String, Object>();
//			ddMap.put("label",DataDicPrm.getName());
//			ddMap.put("type", DataDicPrm.getCode());
//			ddMap.put("data",new ArrayList<Map<String,Object>>());
//			resultList.add(ddMap);		
//			}
		
		addDataDicToList(resultList, parentCoderDDList);
		for(ActPrm actPrm:actPrms) {
			if(actPrm.getParentValue()!=null&&!StringUtils.equals(actPrm.getCode(), actPrm.getParentValue())) {
				continue;
			}
			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("label", actPrm.getName());
//			map.put("value", actPrm.getCode());
//			map.put("dateType", LocalDictCache.getDicNameById(actPrm.getDateType()));
//			if(StringUtils.equals(LocalDictCache.getDicNameById(actPrm.getDateType()), "date")||StringUtils.equals(LocalDictCache.getDicNameById(actPrm.getDateType()), "daterange"))
//			{
//				map.put("valueFormat","yyyy-MM-dd");
//			}else if(StringUtils.equals(LocalDictCache.getDicNameById(actPrm.getDateType()), "time")) {
//				map.put("valueFromat","HH:mm:ss");
//			}else {
//				map.put("valueFormat","yyyy-MM-dd HH:mm:ss");
//			}
//			map.put("condType",getCondType(actPrm.getConditionTypeId()));
			
			setBaseActPrmMap(map, actPrm);
			if(actPrm.getSelectorModeId().endsWith("-group")) {
				map.put("type", "select");
			} else if (actPrm.getSelectorModeId().endsWith("input_range")) {
				map.put("type", "input");
			}  else {
				map.put("type", LocalDictCache.getDicNameById(actPrm.getSelectorModeId()));
			}
			if(actPrm.getDateType()!=null&&actPrm.getDateType().endsWith("range")) {
				map.put("dateType", LocalDictCache.getDicNameById(actPrm.getDateType().replace("range", "")));
			}else {
				map.put("dateType", LocalDictCache.getDicNameById(actPrm.getDateType()));
			}
			map.put("condType",getCondType(actPrm.getConditionTypeId(),actPrm.getSelectorModeId()));
			List<ActPrmCate> actPrmCates=actPrmCateService.qryCatesByApCode(actPrm.getCode(), -1, -1);
			setSubItems(map, actPrmCates);
//			List<ActPrmCate> actPrmCates=actPrmCateService.qryCatesByApCode(actPrm.getCode(), -1, -1);
//			List<Map<String, Object>> subitems=new ArrayList<Map<String,Object>>();
//			for(ActPrmCate actPrmCate:actPrmCates) {
//				Map<String, Object> cateMap=new HashMap<String, Object>();
//				cateMap.put("key", actPrmCate.getName());
//				cateMap.put("value", actPrmCate.getCode());
//				subitems.add(cateMap);
//			}
//			map.put("subItems", subitems);
			
			for(Map<String, Object> m:resultList) {
				if(((String)m.get("type")).equals(actPrm.getParentCoderId())){
					((ArrayList<Map<String,Object>>)(m.get("data"))).add(map);
				}
			}
		}
		JSONArray array= JSON.parseArray(JSONObject.toJSONString(resultList));
	
		return R.data(array);
		
	}
	private static void addDataDicToList(ArrayList<Map<String, Object>> resultList,List<DataDicPrm> parentCoderDDList) {
		for(DataDicPrm DataDicPrm:parentCoderDDList) {
			Map<String, Object> ddMap=new HashMap<String, Object>();
			ddMap.put("label",DataDicPrm.getName());
			ddMap.put("type", DataDicPrm.getCode());
			ddMap.put("data",new ArrayList<Map<String,Object>>());
			resultList.add(ddMap);		
			}
	}
	
	private static void setBaseActPrmMap(Map map,ActPrm actPrm) {
		map.put("label", actPrm.getName());
		map.put("value", actPrm.getCode());
		
		if(StringUtils.equals(LocalDictCache.getDicNameById(actPrm.getDateType()), "date")|| StringUtils.equals(LocalDictCache.getDicNameById(actPrm.getDateType()), "daterange"))
		{
			map.put("valueFormat","yyyy-MM-dd");
		}else if(StringUtils.equals(LocalDictCache.getDicNameById(actPrm.getDateType()), "time")) {
			map.put("valueFromat","HH:mm:ss");
		}else {
			map.put("valueFormat","yyyy-MM-dd HH:mm:ss");
		}
		
	}
	
	private static void setSubItems(Map map,List<ActPrmCate> actPrmCates) {
		List<Map<String, Object>> subitems=new ArrayList<Map<String,Object>>();
		for(ActPrmCate actPrmCate:actPrmCates) {
			Map<String, Object> cateMap=new HashMap<String, Object>();
			cateMap.put("key", actPrmCate.getName());
			cateMap.put("value", actPrmCate.getCode());
			subitems.add(cateMap);
		}
		map.put("subItems", subitems);
	}

	
	private static String getCondType(String conditionTypeId) {
		String condType="";
		switch (conditionTypeId) {
		case "condition_type_equal":
			condType="eq";
			break;
		case "condition_type_day":
			condType="eq";
			break;
		case "condition_type_month":
			condType="eq";
			break;
		case "condition_type_trans_month":
			condType="eq";
			break;
		case "condition_type_week":
			condType="eq";
			break;
		case "condition_type_greater_than":
			condType="gt";
			break;
		case "condition_type_less_than":
			condType="lt";
			break;
		case "condition_type_between":
			condType="between";
		default:
			break;
		}
		return condType;
	}
	
	private static String getCondType(String conditionTypeId,String selectorModeId) {
		String condType="";
		switch (conditionTypeId) {
		case "condition_type_equal":
			condType="eq";
			break;
		case "condition_type_day":
			condType="eq";
			break;
		case "condition_type_month":
			condType="eq";
			break;
		case "condition_type_trans_month":
			condType="eq";
			break;
		case "condition_type_week":
			condType="eq";
			break;
		case "condition_type_greater_than":
			condType="gt";
			break;
		case "condition_type_less_than":
			condType="lt";
			break;
		case "condition_type_between":
			if(StringUtils.equals(selectorModeId,"selector_mode_time")) {
				condType="eq";
			}else {
				condType="between";
			}
		default:
			break;
		}
		return condType;
	}

	private static Object getItemValue(List<Map> maplList,String pLable,String code){
		for(int i=0;i<maplList.size();i++) {
			if(StringUtils.equals((String)(maplList.get(i).get("label")),pLable)) {
				if(maplList.get(i).get("data")!=null){
					Set<String> set=((Map<String, Object>)maplList.get(i).get("data")).keySet();
					Iterator it=set.iterator();
					while (it.hasNext()) {
						String key = (String) it.next();
						if(StringUtils.equals(key, code)) {
							Object object=((Map<String, Object>)maplList.get(i).get("data")).get(key);
//							System.out.println("obj.class="+object.getClass()+"  obj="+object);
//							if(object instanceof JSONArray) {
//								return object;
//							}
							return object;
						}
					}
				}
			}
		}
		return "";
	}
	
	private static void setItemValue(List<Map> maplList,Map map,ActPrm actPrm) {
		String pLable=LocalDictCache.getDicNameById(actPrm.getParentCoderId());
		String code=actPrm.getCode();
		map.put("itemValue", "");
		for(int i=0;i<maplList.size();i++) {
			if(StringUtils.equals((String)(maplList.get(i).get("label")),pLable)) {
				if(maplList.get(i).get("data")!=null){
					Set<String> set=((Map<String, Object>)maplList.get(i).get("data")).keySet();
					Iterator it=set.iterator();
					while (it.hasNext()) {
						String key = (String) it.next();
						if(StringUtils.equals(key, code)) {
							Object object=((Map<String, Object>)maplList.get(i).get("data")).get(key);
//							System.out.println("obj.class="+object.getClass()+"  obj="+object);
//							if(object instanceof JSONArray) {
//								map.put("itemValue", object);
//							}
							map.put("itemValue", object);
						}
					}
				}
			}
		}
	}
	
	private static ActPrmVo setActPrmVo(ActPrm actPrm) {
		if(actPrm==null) {
			return null;
		}
		ActPrmVo actPrmVo=new ActPrmVo();
		BeanUtil.copyProperties(actPrm, actPrmVo);
		actPrmVo.setSysName(LocalDictCache.getDicNameById(actPrmVo.getSysId()));
		actPrmVo.setpCoderName(LocalDictCache.getDicNameById(actPrmVo.getParentCoderId()));
		actPrmVo.setDataModeName(LocalDictCache.getDicNameById(actPrmVo.getDataModeId()));
		actPrmVo.setHideDeptName(LocalDictCache.getDicNameById(actPrmVo.getHideDeptId()));
		actPrmVo.setConditionTypeName(LocalDictCache.getDicNameById(actPrmVo.getConditionTypeId()));
		actPrmVo.setSelectorModeName(LocalDictCache.getDicNameById(actPrmVo.getSelectorModeId()));
		actPrmVo.setStartSignName(LocalDictCache.getDicNameById(actPrmVo.getStat()));
		return actPrmVo;
	}
	
}
