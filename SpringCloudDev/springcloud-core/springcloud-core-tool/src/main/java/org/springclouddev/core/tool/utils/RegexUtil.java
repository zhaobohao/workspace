
package org.springclouddev.core.tool.utils;

import org.springframework.lang.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 * 正规表达式目前共 25种
 * 1.匹配图象
 *
 *  icon_regexp;
 *
 *  2 匹配email地址
 *
 *  email_regexp;
 *
 *  3 匹配匹配并提取url
 *
 *  url_regexp;
 *
 *  4 匹配并提取http
 *
 *  http_regexp;
 *
 *  5.匹配日期
 *
 *  date_regexp;
 *  date_regexp2 --此为复杂表达式;
 *
 *  6 匹配电话
 *
 *  phone_regexp;
 *
 *  7 匹配身份证
 *
 *  ID_card_regexp;
 *
 *  8 匹配邮编代码
 *
 *  ZIP_regexp
 *
 *  9. 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号&circ; 单引号' 双引号&quot; 分号; 逗号, 帽号:
 *  数学减号- 右尖括号&gt; 左尖括号&lt;  反斜杠\ 即空格,制表符,回车符等
 *
 *  non_special_char_regexp;
 *
 *  10 匹配非负整数（正整数 + 0)
 *
 *  non_negative_integers_regexp;
 *
 *  11  匹配不包括零的非负整数（正整数 &gt; 0)
 *
 *  non_zero_negative_integers_regexp;
 *
 *  12 匹配正整数
 *
 *  positive_integer_regexp;
 *
 *  13  匹配非正整数（负整数 + 0）
 *
 *  non_positive_integers_regexp;
 *
 *  14 匹配负整数
 *
 *  negative_integers_regexp;
 *
 *  15. 匹配整数
 *
 *  integer_regexp;
 *
 *  16 匹配非负浮点数（正浮点数 + 0）
 *
 *  non_negative_rational_numbers_regexp
 *
 *  17. 匹配正浮点数
 *
 *  positive_rational_numbers_regexp
 *
 *  18 匹配非正浮点数（负浮点数 + 0）
 *
 *  non_positive_rational_numbers_regexp;
 *
 *  19 匹配负浮点数
 *
 *  negative_rational_numbers_regexp;
 *
 *  20 .匹配浮点数
 *
 *  rational_numbers_regexp;
 *
 *  21. 匹配由26个英文字母组成的字符串
 *
 *  letter_regexp;
 *
 *  22. 匹配由26个英文字母的大写组成的字符串
 *
 *  upward_letter_regexp;
 *
 *  23 匹配由26个英文字母的小写组成的字符串
 *
 *  lower_letter_regexp
 *
 *  24 匹配由数字和26个英文字母组成的字符串
 *
 *  letter_number_regexp;
 *
 *  25  匹配由数字、26个英文字母或者下划线组成的字符串
 *
 *  letter_number_underline_regexp;
 *
 * </pre>
 *
 * @mail wuzhi2000@hotmail.com
 * @author ygj
 *
 * @author zhaobohao
 */
public class RegexUtil {
	/**
	 * 用户名
	 */
	public static final String USER_NAME = "^[a-zA-Z\\u4E00-\\u9FA5][a-zA-Z0-9_\\u4E00-\\u9FA5]{1,11}$";

	/**
	 * 密码
	 */
	public static final String USER_PASSWORD = "^.{6,32}$";

	/**
	 * 邮箱
	 */
	public static final String EMAIL = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";

	/**
	 * 手机号
	 */
	public static final String PHONE = "^1[3456789]\\d{9}$";

	/**
	 * 手机号或者邮箱
	 */
	public static final String EMAIL_OR_PHONE = EMAIL + "|" + PHONE;

	/**
	 * URL路径
	 */
	public static final String URL = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})(:[\\d]+)?([\\/\\w\\.-]*)*\\/?$";

	/**
	 * 身份证校验，初级校验，具体规则有一套算法
	 */
	public static final String ID_CARD = "^\\d{15}$|^\\d{17}([0-9]|X)$";

	/**
	 * 域名校验
	 */
	public static final String DOMAIN = "^[0-9a-zA-Z]+[0-9a-zA-Z\\.-]*\\.[a-zA-Z]{2,4}$";
	/** 保放有四组对应分隔符 */


	/**
	 * 匹配图象 <br>
	 * 格式: /相对路径/文件名.后缀 (后缀为gif,dmp,png) 匹配 :
	 * /forum/head_icon/admini2005111_ff.gif 或 admini2005111.dmp<br>
	 * 不匹配: c:/admins4512.gif
	 */
	public static final String	icon_regexp								= "^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$";

	/**
	 * 匹配email地址 <br>
	 * 格式: X%%@X%%.X%%.%% 匹配 : foo@bar.com 或 foobar@foobar.com.au <br>
	 * 不匹配: foo@bar 或 $$$@bar.com
	 */
	public static final String	email_regexp							= "(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)";

	/**
	 * 匹配匹配并提取url <br>
	 * 格式: %%%%://%%X.%%X.%%X.%%/%%X.%%X?%%X=%%X 匹配 : http://www.suncer.com
	 * 或news://www<br>
	 * 提取(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	 * matchResult.group(4) = /index.html?login=true 不匹配: c:\window
	 */
	public static final String	url_regexp								= "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * 匹配并提取http <br>
	 * 格式: http://%%X.%%X.%%X.%%/%%X.%%X?%%X=%%X 或 ftp://%%X.%%X.%%X 或
	 * https://%%X 匹配 : http://www.suncer.com:8080/index.html?login=true<br>
	 * 提取(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	 * matchResult.group(4) = /index.html?login=true 不匹配: news://www
	 */
	public static final String	http_regexp								= "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * 匹配日期 <br>
	 * 格式(首位不为0): %%%%-%%-%% 或 %%%% %% %% 或 %%%%-X-X <br>
	 * 范围:1900--2099 <br>
	 * 匹配 : 2005-04-04 <br>
	 * 不匹配: 01-01-01
	 */
	public static final String	date_regexp								= "^((((19){1}|(20){1})d{2})|d{2})[-\\s]{1}[01]{1}d{1}[-\\s]{1}[0-3]{1}d{1}$";									// 匹配日期

	/**
	 * 匹配日期 <br>
	 * 格式(首位不为0): 全部 <br>
	 * 范围:全部 <br>
	 * 匹配 : 全部 <br>
	 * 不匹配:全部
	 */
	public static final String	date_regexp2							= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\-\\s]?((((0?"
		+ "[13578])|(1[02]))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))"
		+ "|(((0?[469])|(11))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
		+ "(0?2[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12"
		+ "35679])|([13579][01345789]))[\\-\\-\\s]?((((0?[13578])|(1[02]))"
		+ "[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
		+ "[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\-\\s]?((0?[" + "1-9])|(1[0-9])|(2[0-8]))))))";

	/**
	 * 匹配电话 <br>
	 * 格式为: 0%%X-%%%%%%(10-13位首位必须为0) 或0%%X %%%%%%X(10-13位首位必须为0) 或 <br>
	 * (0%%X)%%%%%%%%(11-14位首位必须为0) 或 %%%%%%%%(6-8位首位不为0) 或
	 * %%%%%%%%%%X(11位首位不为0) <br>
	 * 匹配 : 0371-123456 或 (0371)1234567 或 (0371)12345678 或 010-123456 或
	 * 010-12345678 或 12345678912 <br>
	 * 不匹配: 1111-134355 或 0123456789
	 */
	public static final String	phone_regexp							= "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

	/**
	 * 匹配身份证 <br>
	 * 格式为: %%%%%%%%%%(10位) 或 %%%%%%%%%%%%X(13位) 或 %%%%%%%%%%%%%%X(15位) 或
	 * %%%%%%%%%%%%%%%%%%(18位) <br>
	 * 匹配 : 0123456789123 <br>
	 * 不匹配: 0123456
	 */
	public static final String	ID_card_regexp							= "^\\d{10}|\\d{13}|\\d{15}|\\d{18}$";

	/**
	 * 匹配邮编代码 <br>
	 * 格式为: %%%%%%(6位) <br>
	 * 匹配 : 012345 <br>
	 * 不匹配: 0123456
	 */
	public static final String	ZIP_regexp								= "^[0-9]{6}$";																								// 匹配邮编代码

	/**
	 * 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号< 反斜杠\
	 * 即空格,制表符,回车符等 )<br>
	 * 格式为: x 或 一个一上的字符 <br>
	 * 匹配 : 012345 <br>
	 * 不匹配: 0123456
	 */
	public static final String	non_special_char_regexp					= "^[^'\"\\;,:-<>\\s].+$";																						// 匹配邮编代码

	/**
	 * 匹配非负整数（正整数 + 0)
	 */
	public static final String	non_negative_integers_regexp			= "^\\d+$";

	/**
	 * 匹配不包括零的非负整数（正整数 > 0)
	 */
	public static final String	non_zero_negative_integers_regexp		= "^[1-9]+\\d*$";

	/**
	 * 匹配正整数
	 */
	public static final String	positive_integer_regexp					= "^[0-9]*[1-9][0-9]*$";

	/**
	 * 匹配非正整数（负整数 + 0）
	 */
	public static final String	non_positive_integers_regexp			= "^((-\\d+)|(0+))$";

	/**
	 * 匹配负整数
	 */
	public static final String	negative_integers_regexp				= "^-[0-9]*[1-9][0-9]*$";

	/**
	 * 匹配整数
	 */
	public static final String	integer_regexp							= "^-?\\d+$";

	/**
	 * 匹配非负浮点数（正浮点数 + 0）
	 */
	public static final String	non_negative_rational_numbers_regexp	= "^\\d+(\\.\\d+)?$";

	/**
	 * 匹配正浮点数
	 */
	public static final String	positive_rational_numbers_regexp		= "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";

	/**
	 * 匹配非正浮点数（负浮点数 + 0）
	 */
	public static final String	non_positive_rational_numbers_regexp	= "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";

	/**
	 * 匹配负浮点数
	 */
	public static final String	negative_rational_numbers_regexp		= "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

	/**
	 * 匹配浮点数
	 */
	public static final String	rational_numbers_regexp					= "^(-?\\d+)(\\.\\d+)?$";

	/**
	 * 匹配由26个英文字母组成的字符串
	 */
	public static final String	letter_regexp							= "^[A-Za-z]+$";

	/**
	 * 匹配由26个英文字母的大写组成的字符串
	 */
	public static final String	upward_letter_regexp					= "^[A-Z]+$";

	/**
	 * 匹配由26个英文字母的小写组成的字符串
	 */
	public static final String	lower_letter_regexp						= "^[a-z]+$";

	/**
	 * 匹配由数字和26个英文字母组成的字符串
	 */
	public static final String	letter_number_regexp					= "^[A-Za-z0-9]+$";

	/**
	 * 匹配由数字、26个英文字母或者下划线组成的字符串
	 */
	public static final String	letter_number_underline_regexp			= "^\\w+$";

	/**
	 * 编译传入正则表达式和字符串去匹配,忽略大小写
	 *
	 * @param regex        正则
	 * @param beTestString 字符串
	 * @return {boolean}
	 */
	public static boolean match(String regex, String beTestString) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(beTestString);
		return matcher.matches();
	}

	/**
	 * 编译传入正则表达式在字符串中寻找，如果匹配到则为true
	 *
	 * @param regex        正则
	 * @param beTestString 字符串
	 * @return {boolean}
	 */
	public static boolean find(String regex, String beTestString) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(beTestString);
		return matcher.find();
	}

	/**
	 * 编译传入正则表达式在字符串中寻找，如果找到返回第一个结果
	 * 找不到返回null
	 *
	 * @param regex         正则
	 * @param beFoundString 字符串
	 * @return {boolean}
	 */
	@Nullable
	public static String findResult(String regex, String beFoundString) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(beFoundString);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

}
