package org.springclouddev.integral.aviator.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import org.springclouddev.integral.aviator.common.CommonCode;
import org.springclouddev.integral.aviator.common.ComputeType;
import org.springclouddev.integral.aviator.common.RuleResult;
import org.springclouddev.integral.aviator.model.TagIntegralResult;
import org.springclouddev.integral.aviator.model.TagParameterBean;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class CreateExecUtil {

//	public static void main(String[] args) {
//		JSONArray s = new JSONArray();
//		s.add("a");
//		s.add("b");
//		s.add("v");
//		s.add("ac");
//		String createFinalExec = getAndJointExec(s);
//		System.out.println(createFinalExec);
//	}

//	public static String getParams(List<TagParameterBean> list) {
//		StringBuffer sb = new StringBuffer();
//		list.forEach(value -> sb.append(value.getTagName()+"##"));
//		return sb.delete(sb.length()-2, sb.length()).toString();
//	}

	public static String getExec(TagParameterBean param) {
		//字符串		直接
		//list		,分割
		String value = "";
		if(param.getValue() instanceof JSONArray) {
			value = ((JSONArray)param.getValue()).stream().map(Object::toString).collect(Collectors.joining(","));
		}else {
			value = param.getValue().toString();
		}
		String value2 = param.getActPrm().getValue();
		if(StringUtils.isNotBlank(value2)) {
			return param.getActPrm().getValue().replace(CreatePrmExecUtil.VALUE, value);
		}else {
			return CreatePrmExecUtil.createPrmExec(param.getActPrm()).replace(CreatePrmExecUtil.VALUE, value);
		}
	}

	/**
	 * 生成表达式
	 * 
	 * @param list
	 * @param tagIntegralResult
	 * @return
	 */
	public static String getJointExec(List<TagParameterBean> list, TagIntegralResult tagIntegralResult) {
		if (list == null || list.size() <= 0 || tagIntegralResult == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		list.forEach(value -> sb.append("(").append(value.getExp()).append(")&&"));
		return sb.delete(sb.length() - 2, sb.length())
				.append("?(" + tagIntegralResult.getTagIntegralResultExp() + "):0").toString();

	}

	public static boolean removeResultIsZero(RuleResult result) {
		return result.getResult() != 0;
	}

	/**
	 * 按交易金额送积分
	 * 
	 * @param tagIntegralResult
	 * @return
	 */
	public static String getTagIntegralResultExpByBeyondAmount(TagIntegralResult tagIntegralResult) {
		String format = new DecimalFormat("0.00")
				.format((float) tagIntegralResult.getValue1() / tagIntegralResult.getValue2());
		if(tagIntegralResult.getTopGrade() == 0) {
			return tagIntegralResult.getTagCode() + "!=nil&&" + tagIntegralResult.getTagCode() + ">'"
					+ tagIntegralResult.getBeyondAmount() + "'?" + CommonCode.MULTIPLICATION + "(" + CommonCode.SUBTRACTION
					+ "(" + tagIntegralResult.getTagCode() + ",'" + tagIntegralResult.getBeyondAmount() + "'),'" + format
					+ "'):0";
		}else {
			return "min(" + tagIntegralResult.getTagCode() + "!=nil&&" + tagIntegralResult.getTagCode() + ">'"
			+ tagIntegralResult.getBeyondAmount() + "'?" + CommonCode.MULTIPLICATION + "(" + CommonCode.SUBTRACTION
			+ "(" + tagIntegralResult.getTagCode() + ",'" + tagIntegralResult.getBeyondAmount() + "'),'" + format
			+ "'):0," + tagIntegralResult.getTopGrade() + ")";
		}
	}

	/**
	 * 按笔数送积分
	 * 
	 * @param tagIntegralResult
	 * @return
	 */
	public static String getTagIntegralResultExpByTransNumber(TagIntegralResult tagIntegralResult) {

		return tagIntegralResult.getTagCode() + "!=nil&&" + "double(" + tagIntegralResult.getTagCode() + ")>="
				+ tagIntegralResult.getTransactionsNumber() + "?" + CommonCode.MULTIPLICATION + "("
				+ tagIntegralResult.getTagCode() + ",'" + tagIntegralResult.getFixedIntegral() + "')" + ":0";
	}

	/**
	 * 生日当月
	 * 
	 * @param tagCode
	 * @return
	 */
	public static String createThisMonth(String tagCode) {
		return tagCode+"!=nil&&"+CommonCode.THIS_MONTH + "(" + tagCode + ")";
	}

	/**
	 * 生日当天
	 * 
	 * @param tagCode yyyy-MM-dd
	 * @return
	 */
	public static String createThisToday(String tagCode) {
		return tagCode+"!=nil&&"+CommonCode.THIS_TODAY + "(" + tagCode + ")";
	}

	/**
	 * 生日当周
	 * 
	 * @param tagCode yyyy-MM-dd
	 * @return
	 */
	public static String createThisWeek(String tagCode) {
		return tagCode+"!=nil&&"+CommonCode.THIS_WEEK + "(" + tagCode + ")";
	}

	/**
	 * 交易月份
	 * 
	 * @param tagCode 标签名称
	 * @param months  交易月份列表 ,分割字符串
	 * @return
	 */
	public static String createTransactionsMonth(String tagCode, String months) {

		return tagCode+"!=nil&&"+CommonCode.TRANS_MONTH + "('"+months+"',"+tagCode+")";
//		include(string.split('" + months + "',','),string.split('" + tagCode + "','-')[1])";
	}

	/**
	 * 字符串包含
	 * 
	 * @param tagCode 字段名称
	 * @param msg     字段值  ,分割
	 * @return
	 */
	public static String createContains(String tagCode, String msg, String selectorModeId) {
		if (StrUtil.equals(selectorModeId, ComputeType.SELECTOR_MODE_CHECKBOX_GROUP)) {
			return tagCode+"!=nil&&include(string.split('" + msg + "',',')," + tagCode + ")";
		} else {
			return tagCode+"!=nil&&("+tagCode + "=='" + msg + "')";
		}
	}

	/**
	 * value1 < tagcode && value2 > tagCode
	 * 
	 * @param tagCode 字段名称
	 * @param msg  较小参数,较大参数    用,分割
	 * @return
	 */
	public static String createBetween(String tagCode, String msg, String type) {
//		return getTagCode(tagCode, type) + ">" + getValue(msg, 0,type) + "&&"
//				+ getTagCode(tagCode, type) + "<" + getValue(msg,1, type);
		return tagCode+"!=nil&&("+(StrUtil.equals(type, ComputeType.DATA_MODE_STR)?CommonCode.TIME_BETWEEN+"("+tagCode+",'"+msg+"')":CommonCode.INT_BETWEEN+"("+tagCode+",'"+msg+"')")+")";
	}

	private static String getTagCode(String tagCode, String type) {
		return tagCode+"!=nil&&("+(StrUtil.equals(type, ComputeType.DATA_MODE_STR) ? tagCode : "double(" + tagCode + "))");
	}

	private static String getValue(String value, String type) {
		value = "string.split('" + value + "',',')";
		return StrUtil.equals(type, ComputeType.DATA_MODE_STR) ? "'" + value + "'" : value;
	}

	/**
	 * tagcode > value1
	 * 
	 * @param tagCode 字段名称
	 * @param type  较小参数
	 * @return
	 */
	public static String createSmallCompare(String tagCode, String value, String type) {
		return tagCode+"!=nil&&"+getTagCode(tagCode, type) + ">" + getValue(value, type);
	}

	/**
	 * tagCode <= value2
	 * 
	 * @param tagCode 字段名称
	 * @param type  较大参数
	 * @return
	 */
	public static String createBigCompare(String tagCode, String value, String type) {
		return tagCode+"!=nil&&"+getTagCode(tagCode, type) + "<'" + getValue(value, type);
	}

}
