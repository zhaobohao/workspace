package org.springclouddev.integral.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.springclouddev.integral.aviator.common.CommonCode;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IntegralMultiplication extends AbstractFunction implements MyFunction {


	@Override
	public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
		String a1 = FunctionUtils.getStringValue(arg1, env);
		String a2 = FunctionUtils.getStringValue(arg2, env);
		return new AviatorBigInt((int)(Double.parseDouble(a1)*Double.parseDouble(a2)));
	}
	
	@Override
	public String getName() {
		return CommonCode.MULTIPLICATION;
	}

	
	
}
