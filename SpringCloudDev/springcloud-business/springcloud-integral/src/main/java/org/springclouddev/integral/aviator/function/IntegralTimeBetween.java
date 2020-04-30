package org.springclouddev.integral.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;
import org.springclouddev.integral.aviator.common.CommonCode;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IntegralTimeBetween extends AbstractFunction implements MyFunction {


	@Override
	public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
		String a1 = FunctionUtils.getStringValue(arg1, env).replace("-", "").replace(":", "").replace(" ", "");
		String a2 = FunctionUtils.getStringValue(arg2, env).replace("-", "").replace(":", "").replace(" ", "");
		if(a2.indexOf(",")==0) {
			return new AviatorRuntimeJavaType(Double.parseDouble(a1) < Double.parseDouble(a2.substring(1, a2.length())));
		}else if(a2.indexOf(",") == a2.length()-1) {
			return new AviatorRuntimeJavaType(Double.parseDouble(a1) > Double.parseDouble(a2.substring(0, a2.length()-1)));
		}else {
			String[] split = a2.split(",");
			return new AviatorRuntimeJavaType(Double.parseDouble(a1) < Double.parseDouble(split[1])
					&& Double.parseDouble(a1) > Double.parseDouble(split[0]));
		}
	}
	
	@Override
	public String getName() {
		return CommonCode.TIME_BETWEEN;
	}

	
	
}
