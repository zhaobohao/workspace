package com.gitee.easyopen.support;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.ApiResult;
import com.gitee.easyopen.Result;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.interceptor.ApiInterceptorAdapter;
import com.gitee.easyopen.util.RedisLockUtil;

/**
 * redis分布式锁解决方案。<br>
 * <pre>
 * 核心思想：
<code>
try {
    锁（用户id + 接口名） {
        执行业务代码
    }
} finally {
    释放锁
}
</code>
 * 在锁的内部执行业务代码时，其它线程进来都将拒之门外。
 * </pre>
 * @author tanghc
 */
public abstract class BaseLockInterceptor extends ApiInterceptorAdapter {
    protected Logger log = LoggerFactory.getLogger(getClass());
    
    private static final String REQUEST_ID_NAME = "easyopen_lock_request_id";
    
    private static final int ERROR_CODE = -102;
    private static final String ERROR_MSG = "您已提交，请耐心等待哦";
    /** 锁过期时间，毫秒 */
    private static final int LOCK_EXPIRE_MILLISECONDS = 60 * 1000;

    /**
     * 返回redis客户端
     * @return 返回RedisTemplate
     */
    @SuppressWarnings("rawtypes")
    protected abstract RedisTemplate getRedisTemplate();

    /**
     * 获取用户唯一标示。确保肯定有值，如果返回null或空字符串，将不起作用。
     * @return 返回唯一标示
     */
    protected abstract String getUserId();
    

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu)
            throws Exception {

        String id = getUserId();
        if(StringUtils.isEmpty(id) || Consts.NULL.equals(id)) {
            return true;
        }
        String lockKey = getLockKey(id);
        String requestId = UUID.randomUUID().toString();
        
        boolean hasLock = RedisLockUtil.tryGetDistributedLock(getRedisTemplate(), lockKey, requestId,
                getLockExpireMilliseconds());
        
        if(hasLock) {
            request.setAttribute(REQUEST_ID_NAME, requestId);
            return true;
        } else { // 没有取到锁，直接返回
            this.noLockHandler(request, response);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object serviceObj,
            Object argu, Object result, Exception e) throws Exception {
        String requestId = (String)request.getAttribute(REQUEST_ID_NAME);
        if(StringUtils.hasText(requestId)) {
            String id = getUserId();
            String lockKey = getLockKey(id);
            
            RedisLockUtil.releaseDistributedLock(getRedisTemplate(), lockKey, requestId);
        }

    }
    
    /**
     * 没有拿到锁后的处理
     * @param request
     * @param response
     */
    protected void noLockHandler(HttpServletRequest request, HttpServletResponse response) {
        Result result = getErrorResult();
        response.setCharacterEncoding(Consts.UTF8);
        response.setContentType(Consts.CONTENT_TYPE_JSON);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            log.error("response.getWriter().write报错", e);
        }
    }
    
    /**
     * 返回错误结果
     * @return 返回结果
     */
    protected Result getErrorResult() {
        ApiResult result = new ApiResult();
        result.setCode(getErrorCode());
        result.setMsg(getErrorMsg());
        return result;
    }
    
    /**
     * 锁过期时间，毫秒
     * @return 返回过期时间，毫秒
     */
    protected int getLockExpireMilliseconds() {
        return LOCK_EXPIRE_MILLISECONDS;
    }
    
    protected Object getErrorCode() {
        return ERROR_CODE;
    }
    
    protected String getErrorMsg() {
        return ERROR_MSG;
    }

    protected String getLockKey(String userId) {
        return "easyopen_lock_key:" + userId;
    }

}
