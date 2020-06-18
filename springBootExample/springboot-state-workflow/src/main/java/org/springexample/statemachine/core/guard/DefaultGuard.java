package org.springexample.statemachine.core.guard;


import org.springexample.statemachine.core.exception.StateMachineException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author zhaobo
 */
@Slf4j
public class DefaultGuard {
    public static <S, E> Guard<S, E> condition(String key, String expect) {
        return (s) -> {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(expect)) {
                throw new StateMachineException("key or expect can not be blank...");
            }
            if (s.getMessage().getHeaders() == null) return false;
            Object actualValue = s.getMessage().getHeaders().getHeader(key);
            if (actualValue == null) return false;
            return expect.equals(actualValue);
        };
    }
}
