package org.springclouddev.integral.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;
import org.springclouddev.integral.aviator.common.CommonCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;

/**
 * 生日当天判断
 * 
 * @author huangqingsong
 *
 */
@Service
public class ThisTodayFunction extends AbstractFunction  implements MyFunction {

	@Override
	public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
		Calendar calendar = Calendar.getInstance();
		String[] dateTime = FunctionUtils.getStringValue(arg1, env).split(" ")[0].split("-");
		String month = (calendar.get(Calendar.MONTH) + 1) + "";
		String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
		boolean equals = StringUtils.equals(month, dateTime[1]);
		boolean equals2 = StringUtils.equals(day, dateTime[2]);
		
		return new AviatorRuntimeJavaType(
				StringUtils.equals(month, dateTime[1]) && StringUtils.equals(day, dateTime[2]));
	}

	@Override
	public String getName() {
		return CommonCode.THIS_TODAY;
	}

}
