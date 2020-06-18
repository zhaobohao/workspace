package org.springexample.statemachine.sf.action;

import org.springexample.statemachine.core.action.Action;
import org.springexample.statemachine.core.context.StateContext;
import org.springexample.statemachine.sf.enumerate.GrantEvent;
import org.springexample.statemachine.sf.enumerate.GrantState;
import org.springexample.statemachine.workflow.action.WorkFlowAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.springexample.statemachine.sf.enumerate.GrantConstant.DOCUMENT_CREDIT_STATUS;
import static org.springexample.statemachine.sf.enumerate.GrantConstant.DOCUMENT_CREDIT_SUCCESS;

@Slf4j
@Component
public class DocumentCreditAction implements WorkFlowAction<GrantState, GrantEvent> {

    @Override
    public void execute(StateContext<GrantState, GrantEvent> context) {
        log.info("建档授信");
        addHeader(context,DOCUMENT_CREDIT_STATUS,DOCUMENT_CREDIT_SUCCESS);
    }

    public Action<GrantState, GrantEvent> errorAction() {
        return (s) -> log.info("建档授信异常，最后一次尝试");
    }
}
