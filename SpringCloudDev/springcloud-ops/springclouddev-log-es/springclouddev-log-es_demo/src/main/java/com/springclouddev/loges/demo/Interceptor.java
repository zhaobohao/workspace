package com.springclouddev.loges.demo;
import com.springclouddev.loges.core.TraceId;
import com.springclouddev.loges.core.util.IdWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Component
public class Interceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IdWorker worker = new IdWorker(1,1,1);
        TraceId.logTraceID.set(String.valueOf(worker.nextId()));
        return true;
    }
}
