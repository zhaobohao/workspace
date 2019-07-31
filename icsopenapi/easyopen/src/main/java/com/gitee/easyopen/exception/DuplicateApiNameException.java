package com.gitee.easyopen.exception;

/**
 * @author tanghc
 */
public class DuplicateApiNameException extends Exception {
    private static final long serialVersionUID = 5967310644989187499L;

    public DuplicateApiNameException() {
        super();
    }

    public DuplicateApiNameException(String message) {
        super(message);
    }

}
