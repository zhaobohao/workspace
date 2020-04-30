package org.springclouddev.integral.aviator.util;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import org.springclouddev.integral.aviator.function.MyFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Service
public class InitFunction {
	
	@Autowired
    ApplicationContext applicationContext;
	
	public void init() {
		for (MyFunction mf : applicationContext.getBeansOfType(MyFunction.class).values()) {
			AviatorEvaluator.addFunction((AviatorFunction) mf);
		}
	}
	
	
}
