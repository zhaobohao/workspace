package org.springclouddev.integral.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;
import org.springclouddev.integral.aviator.common.CommonCode;
import org.springclouddev.integral.aviator.util.MyDayOfYear;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;

/**
 * 生日当周判断
 * @author huangqingsong
 *
 */
@Service
public class ThisWeekFunction extends AbstractFunction implements MyFunction  {

	@Override
	public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
		boolean flag = true;
		Calendar calendar = Calendar.getInstance();
		String[] dateTime = FunctionUtils.getStringValue(arg1, env).split(" ")[0].split("-");
		int dayOfYear = MyDayOfYear.getDayOfYear(calendar.get(Calendar.YEAR), Integer.parseInt(dateTime[1]), Integer.parseInt(dateTime[2]));
		int nowDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		int nowDayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)-1);
		if(nowDayOfWeek == 6) {
			if(dayOfYear < nowDayOfYear - nowDayOfWeek || dayOfYear > nowDayOfYear) {
				flag = false;
			}
		}else {
			if(dayOfYear < nowDayOfYear - nowDayOfWeek || dayOfYear > 6 + nowDayOfYear - nowDayOfWeek) {
				flag = false;
			}
		}
        return new AviatorRuntimeJavaType(flag);
	}
	
	private  int getDayOfWeek(int dayOfWeek) {
		return dayOfWeek==0?6:dayOfWeek-1;
	}
	
	@Override
	public String getName() {
		return CommonCode.THIS_WEEK;
	}

}
