package org.springexample.statemachine.sf.action;


import org.springexample.statemachine.core.context.StateContext;
import org.springexample.statemachine.sf.enumerate.GrantEvent;
import org.springexample.statemachine.sf.enumerate.GrantState;
import org.springexample.statemachine.workflow.action.WorkFlowAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.springexample.statemachine.sf.enumerate.GrantConstant.GRANT_STATUS;
import static org.springexample.statemachine.sf.enumerate.GrantConstant.GRANT_SUCCESS;

@Component
@Slf4j
public class GrantAction implements WorkFlowAction<GrantState, GrantEvent> {

    /**
     * 放款
     * @param context 上下文
     */
    @Override
    public void execute(StateContext<GrantState, GrantEvent> context) {
        System.out.println("放款");
        addHeader(context, GRANT_STATUS, GRANT_SUCCESS);
    }

}
