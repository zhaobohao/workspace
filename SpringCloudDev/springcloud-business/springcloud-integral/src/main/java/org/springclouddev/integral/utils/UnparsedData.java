package org.springclouddev.integral.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springclouddev.core.tool.utils.SpringUtil;
import org.springclouddev.integral.aviator.model.TagIntegralResult;
import org.springclouddev.integral.aviator.model.TagParameterBean;
import org.springclouddev.integral.entity.ActPrm;
import org.springclouddev.integral.mapper.ActPrmMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnparsedData {
	/**
	 * {"time"： { "birday": ["2019-10-28","2019-10-31"],"birday_dat": true, "ssss"：
	 * { "gender": ["女"], "martialStatus": ["已婚","再婚"], "actUserType": ["对公客户"],
	 * "actUserLevel": ["铂金", "黄金"] }
	 *
	 *
	 */

//	public static void main(String[] args) {
//		String ruleExp = "[{\n" +
//				"	\"label\": \"活动客户\",\n" +
//				"	\"fieldList\": [{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"证件类型\",\n" +
//				"		\"type\": \"input\",\n" +
//				"		\"value\": \"cert_type\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"客户证件号\",\n" +
//				"		\"type\": \"input\",\n" +
//				"		\"value\": \"cert_no\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [{\n" +
//				"			\"value\": \"已婚\",\n" +
//				"			\"key\": \"1\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"未婚\",\n" +
//				"			\"key\": \"2\"\n" +
//				"		}],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"婚姻状况\",\n" +
//				"		\"type\": \"checkbox-group\",\n" +
//				"		\"value\": \"marry_status\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [{\n" +
//				"			\"value\": \"男\",\n" +
//				"			\"key\": \"1\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"女\",\n" +
//				"			\"key\": \"2\"\n" +
//				"		}],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"性别\",\n" +
//				"		\"type\": \"checkbox-group\",\n" +
//				"		\"value\": \"gender\"\n" +
//				"	}],\n" +
//				"	\"data\": {\n" +
//				"		\"cert_type\": \"\",\n" +
//				"		\"cert_no\": \"\",\n" +
//				"		\"marry_status\": [],\n" +
//				"		\"gender\": []\n" +
//				"	}\n" +
//				"},\n" +
//				"{\n" +
//				"	\"label\": \"活动事件\",\n" +
//				"	\"fieldList\": [],\n" +
//				"	\"data\": {\n" +
//				"\n" +
//				"	}\n" +
//				"},\n" +
//				"{\n" +
//				"	\"label\": \"活动范围\",\n" +
//				"	\"fieldList\": [{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"MCC码\",\n" +
//				"		\"type\": \"input\",\n" +
//				"		\"value\": \"mcc_code\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"客户名单\",\n" +
//				"		\"type\": \"input\",\n" +
//				"		\"value\": \"client_list\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"地址\",\n" +
//				"		\"type\": \"input\",\n" +
//				"		\"value\": \"address\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"交易所属机构\",\n" +
//				"		\"type\": \"checkbox\",\n" +
//				"		\"value\": \"txn_bank\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"所属机构\",\n" +
//				"		\"type\": \"checkbox\",\n" +
//				"		\"value\": \"card_bank\"\n" +
//				"	}],\n" +
//				"	\"data\": {\n" +
//				"		\"mcc_code\": \"\",\n" +
//				"		\"client_list\": \"\",\n" +
//				"		\"address\": \"\",\n" +
//				"		\"txn_bank\": \"\",\n" +
//				"		\"card_bank\": \"\"\n" +
//				"	}\n" +
//				"},\n" +
//				"{\n" +
//				"	\"label\": \"时间类型\",\n" +
//				"	\"fieldList\": [{\n" +
//				"		\"dateType\": \"daterange\",\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd\",\n" +
//				"		\"condType\": \"between\",\n" +
//				"		\"label\": \"交易日期\",\n" +
//				"		\"type\": \"date\",\n" +
//				"		\"value\": \"txn_date\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"dateType\": \"daterange\",\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd\",\n" +
//				"		\"condType\": \"between\",\n" +
//				"		\"label\": \"发卡日期\",\n" +
//				"		\"type\": \"date\",\n" +
//				"		\"value\": \"sendcard_date\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"dateType\": \"daterange\",\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd\",\n" +
//				"		\"condType\": \"between\",\n" +
//				"		\"label\": \"开户日期\",\n" +
//				"		\"type\": \"date\",\n" +
//				"		\"value\": \"open_date\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"dateType\": \"daterange\",\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd\",\n" +
//				"		\"condType\": \"between\",\n" +
//				"		\"label\": \"出生日期\",\n" +
//				"		\"type\": \"date\",\n" +
//				"		\"value\": \"birday\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"between\",\n" +
//				"		\"label\": \"交易时间\",\n" +
//				"		\"type\": \"time\",\n" +
//				"		\"value\": \"txn_time\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [{\n" +
//				"			\"value\": \"1月\",\n" +
//				"			\"key\": \"1\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"2月\",\n" +
//				"			\"key\": \"2\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"3月\",\n" +
//				"			\"key\": \"3\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"4月\",\n" +
//				"			\"key\": \"4\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"5月\",\n" +
//				"			\"key\": \"5\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"6月\",\n" +
//				"			\"key\": \"6\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"7月\",\n" +
//				"			\"key\": \"7\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"8月\",\n" +
//				"			\"key\": \"8\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"9月\",\n" +
//				"			\"key\": \"9\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"10月\",\n" +
//				"			\"key\": \"10\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"11月\",\n" +
//				"			\"key\": \"11\"\n" +
//				"		},\n" +
//				"		{\n" +
//				"			\"value\": \"12月\",\n" +
//				"			\"key\": \"12\"\n" +
//				"		}],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"交易月份\",\n" +
//				"		\"type\": \"checkbox-group\",\n" +
//				"		\"value\": \"txn_month\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"生日当周\",\n" +
//				"		\"type\": \"checkbox\",\n" +
//				"		\"value\": \"birthday_week\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"生日当天\",\n" +
//				"		\"type\": \"checkbox\",\n" +
//				"		\"value\": \"birthday_today\"\n" +
//				"	},\n" +
//				"	{\n" +
//				"		\"subItems\": [],\n" +
//				"		\"itemValue\": \"\",\n" +
//				"		\"valueFormat\": \"yyyy-MM-dd HH:mm:ss\",\n" +
//				"		\"condType\": \"eq\",\n" +
//				"		\"label\": \"生日月份\",\n" +
//				"		\"type\": \"checkbox\",\n" +
//				"		\"value\": \"birthday_month\"\n" +
//				"	}],\n" +
//				"	\"data\": {\n" +
//				"		\"txn_date\": [],\n" +
//				"		\"sendcard_date\": [],\n" +
//				"		\"open_date\": [],\n" +
//				"		\"birday\": [],\n" +
//				"		\"txn_time\": \"\",\n" +
//				"		\"txn_month\": [\"2\",\n" +
//				"		\"6\"],\n" +
//				"		\"birthday_week\": \"\",\n" +
//				"		\"birthday_today\": \"\",\n" +
//				"		\"birthday_month\": \"\"\n" +
//				"	}\n" +
//				"},\n" +
//				"{\n" +
//				"	\"tradeType\": 0,\n" +
//				"	\"overAmount\": \"0\",\n" +
//				"	\"topScore\": \"0\",\n" +
//				"	\"integralRate\": [\"0\",\n" +
//				"	\"0\"],\n" +
//				"	\"perCountFixScore\": \"0\",\n" +
//				"	\"tradeCount\": \"0\"\n" +
//				"},\n" +
//				"{\n" +
//				"	\"tradeType\": 0,\n" +
//				"	\"overAmount\": \"0\",\n" +
//				"	\"topScore\": \"0\",\n" +
//				"	\"integralRate\": [\"0\",\n" +
//				"	\"0\"],\n" +
//				"	\"perCountFixScore\": \"0\",\n" +
//				"	\"tradeCount\": \"0\"\n" +
//				"},\n" +
//				"{\n" +
//				"	\"tradeType\": 0,\n" +
//				"	\"overAmount\": \"0\",\n" +
//				"	\"topScore\": \"0\",\n" +
//				"	\"integralRate\": [\"0\",\n" +
//				"	\"0\"],\n" +
//				"	\"perCountFixScore\": \"0\",\n" +
//				"	\"tradeCount\": \"0\"\n" +
//				"}]";

//		JSONArray obj = new JSONArray();
//		JSONObject obj1 = new JSONObject();
//		JSONObject obj2 = new JSONObject();
//		JSONObject obj3 = new JSONObject();
//		obj.add(obj1);
//		obj.add(obj2);
//		obj.add(obj3);
//		new TagIntegralResult(id, beyondAmount, array, topGrade, fixedIntegral, transactionsNumber)
//		List<TagParameterBean> collect = new ArrayList<TagParameterBean>();
//
//		List<Map> parseArray = JSONArray.parseArray(ruleExp, Map.class);
//		parseArray.stream().filter(UnparsedData::removeNoData).map(
//				UnparsedData::dataNodeJsonToMap)
//				.filter(UnparsedData::HasMapMsg)
//				.map(x -> x.entrySet().stream()
//						.filter(UnparsedData::valueIsBotNull)
//						.map(v -> new TagParameterBean(v.getKey() + "", v.getValue(), new ActPrm()))
//						.collect(Collectors.toList()))
//				.forEach(collect::addAll);
//		System.out.println(collect);
//
//	}

	public static boolean removeNoData(Map map) {
		return map.containsKey("data");
	}

	public static boolean HasMapMsg(Map map) {
		return map.size() > 0;
	}

	public static boolean valueIsBotNull(Map.Entry map) {
		Object value = map.getValue();
		if(value instanceof List) {
			return ((List)value).size() > 0;
		}else {
			return StrUtil.isNotBlank(map.getValue().toString());
		}
	}

	public static boolean hasTradeType(Map map) {
		return map.containsKey("tradeType");
	}

	@SuppressWarnings("unchecked")
	public static List<TagParameterBean> getUnparsedData(String ruleExp) {
		List<TagParameterBean> collect = new ArrayList<TagParameterBean>();
		ActPrmMapper actPrmMapper= SpringUtil.getBean("actPrmMapper");
		List<Map> parseArray = JSONArray.parseArray(ruleExp, Map.class);
		parseArray.stream()
				.filter(UnparsedData::removeNoData)
				.map(UnparsedData::dataNodeJsonToMap)
				.filter(UnparsedData::HasMapMsg)
				.map(x -> x.entrySet().stream()
						.filter(UnparsedData::valueIsBotNull)
						.map(v -> new TagParameterBean(v.getKey() + "", v.getValue(), actPrmMapper.selectById(v.getKey())))
						.collect(Collectors.toList()))
				.forEach(collect::addAll);
		
		
		return collect;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> dataNodeJsonToMap(Map x) {
		return (Map<String, Object>) JSONObject.parseObject(JSONObject.toJSONString(x.get("data")), Map.class);
	}

	public static TagIntegralResult getCalculateData(String ruleExp) {

		List<Map> parseArray = JSONArray.parseArray(ruleExp, Map.class);
		List<TagIntegralResult> collect = parseArray.stream()
				.filter(UnparsedData::hasTradeType)
				.map(x->new TagIntegralResult(
						Integer.parseInt(x.get("tradeType").toString()),
						Integer.parseInt(x.get("overAmount").toString()),
						x.get("integralRate"),
						Integer.parseInt(x.get("topScore").toString()),
						Integer.parseInt(x.get("perCountFixScore").toString()),
						Integer.parseInt(x.get("tradeCount").toString())))
				.collect(Collectors.toList());
		
		return collect.get(0);
	}
	
	

}
