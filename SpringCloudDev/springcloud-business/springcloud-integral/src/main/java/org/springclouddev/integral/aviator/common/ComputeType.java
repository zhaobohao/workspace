package org.springclouddev.integral.aviator.common;

/**
 * 运算符
 * @author kouseishou
 *
 */
public class ComputeType {
	 
	/**
	 * 最大
	 */
	public static final String MAX = "rule_type_max";
	/**
	 * 累加
	 */
	public static final String ADD = "rule_type_sum";
	/**
	 * 最小
	 */
	public static final String MIN = "rule_type_min";
	/**
	 * 优先
	 */
	public static final String GRADE = "rule_type_pirority";
	
	
	/**
	 * 生日当月
	 */
	public static final String TYPE_MONTH = "condition_type_month";
	/**
	 * 生日当周
	 */
	public static final String TYPE_WEEK = "condition_type_week";
	/**
	 * 生日当天
	 */
	public static final String TYPE_TODAY = "condition_type_day";
	/**
	 * tagcode < value
	 */
	public static final String TYPE_TAG_SMALL_PARAM = "condition_type_less_than";
	/**
	 * tagcode > value
	 */
	public static final String TYPE_TAG_LARGER_PARAM = "condition_type_greater_than";
	/**
	 * 大于-小于
	 */
	public static final String TYPE_RANGE = "condition_type_between";
	/**
	 * 字符串包含
	 */
	public static final String TYPE_EQRUAL = "condition_type_equal";
	/**
	 * 交易月
	 */
	public static final String TYPE_TRANS_MONTH = "condition_type_trans_month";

	
	
	
	public static final String DATA_MODE_STR = "data_mode_str";
	public static final String DATA_MODE_INT = "data_mode_int";
	
	
	public static final String SELECTOR_MODE_CHECKBOX = "selector_mode_checkbox";
	public static final String SELECTOR_MODE_CHECKBOX_GROUP = "selector_mode_checkbox-group";
	public static final String SELECTOR_MODE_DATE = "selector_mode_date";
	public static final String SELECTOR_MODE_INPUT = "selector_mode_input";
	public static final String SELECTOR_MODE_INPUT_RANGE = "selector_mode_input-range";
	public static final String SELECTOR_MODE_TIME = "selector_mode_time";
	public static final String SELECTOR_MODE_UPLOAD = "selector_mode_upload";
	
	
	public static final String DATE_TYPE_DATE = "date_type_date";
	public static final String DATE_TYPE_DATERANGE = "date_type_daterange";
	public static final String DATE_TYPE_DATETIME = "date_type_datetime";
	public static final String DATE_TYPE_DATETIMERANGE = "date_type_datetimerange";
	
	
	
}
