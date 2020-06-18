package org.springexample.statemachine.sf.action;


import org.springexample.statemachine.core.context.StateContext;
import org.springexample.statemachine.sf.enumerate.GrantEvent;
import org.springexample.statemachine.sf.enumerate.GrantState;
import org.springexample.statemachine.workflow.action.WorkFlowAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateCardIIAction implements WorkFlowAction<GrantState, GrantEvent> {

    public void execute(StateContext<GrantState, GrantEvent> context) {
        log.info("开二类户");
    }


}
