package org.springclouddev.integral.aviator.util;

import cn.hutool.core.util.StrUtil;
import org.springclouddev.integral.entity.ActPrm;
import org.springclouddev.integral.aviator.common.ComputeType;

public class CreatePrmExecUtil {

	public static final String VALUE = "#value#";

	public static String createPrmExec(ActPrm actPrm) {
		// 父code
		String pCode = actPrm.getParentValue();
		// 子code
		String code = actPrm.getCode();
		// 字符串、数字 data_mode_str data_mode_int
		String dataModeId = actPrm.getDataModeId();
		// 数据类型 selector_mode_date selector_mode_time
		String selectorModeId = actPrm.getSelectorModeId();
		// 计算方式 condition_type_day condition_type_month condition_type_trans_month
		// condition_type_week
		String conditionTypeId = actPrm.getConditionTypeId();
		// 日期类型 date_type_date date_type_datetime
		String dateType = actPrm.getDateType();

		if(StrUtil.isBlank(pCode)) {
			pCode = code;
		}
		
		switch (selectorModeId) {
		// HH:mm:ss
		case ComputeType.SELECTOR_MODE_TIME:
			pCode = changeTime(pCode);
			break;
		case ComputeType.SELECTOR_MODE_DATE:
			pCode = changeDate(pCode, dateType);
			break;
		default:
			break;
		}

		switch (conditionTypeId) {
		case ComputeType.TYPE_MONTH:
			return CreateExecUtil.createThisMonth(pCode);
		case ComputeType.TYPE_TODAY:
			return CreateExecUtil.createThisToday(pCode);
		case ComputeType.TYPE_WEEK:
			return CreateExecUtil.createThisWeek(pCode);
		case ComputeType.TYPE_TRANS_MONTH:
			return CreateExecUtil.createTransactionsMonth(pCode, VALUE);
		case ComputeType.TYPE_EQRUAL:
			return CreateExecUtil.createContains(pCode, VALUE, selectorModeId);
		case ComputeType.TYPE_TAG_LARGER_PARAM:
			return CreateExecUtil.createSmallCompare(pCode, VALUE, dataModeId);
		case ComputeType.TYPE_TAG_SMALL_PARAM:
			return CreateExecUtil.createBigCompare(pCode, VALUE, dataModeId);
		case ComputeType.TYPE_RANGE:
			return CreateExecUtil.createBetween(pCode, VALUE, dataModeId);
		default:
			return null;
		}

	}

	private static String changeTime(String pCode) {
		return "string.split(" + pCode + ",' ')[1]";
	}

	private static String changeDate(String pCode, String dateType) {
		return dateType.indexOf("time") > 0 ? pCode : "string.split(" + pCode + ",' ')[0]";
	}

}
