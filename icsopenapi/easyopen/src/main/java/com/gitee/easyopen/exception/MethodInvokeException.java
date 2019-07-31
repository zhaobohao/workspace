package com.gitee.easyopen.exception;

/**
 * @author tanghc
 */
public class MethodInvokeException extends Exception {
    private static final long serialVersionUID = -182458442281350023L;

    private Exception target;

    public MethodInvokeException(Exception e) {
        super(e);
        this.target = e;
    }

    public Exception getTarget() {
        return target;
    }

}
