package org.springclouddev.integral.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;
import org.springclouddev.integral.aviator.common.CommonCode;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 * 
 * @author huangqingsong
 *
 */
@Service
public class TransMonthFunction extends AbstractFunction  implements MyFunction {

	
	@Override
	public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
		String a1 = FunctionUtils.getStringValue(arg1, env);
		String a2 = FunctionUtils.getStringValue(arg2, env).split(" ")[0].split("-")[1];
		
		return new AviatorRuntimeJavaType(a1.contains(Integer.parseInt(a2)+""));
	}
	
	@Override
	public String getName() {
		return CommonCode.TRANS_MONTH;
	}

}
