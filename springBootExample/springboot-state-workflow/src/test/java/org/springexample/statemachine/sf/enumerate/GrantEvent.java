package org.springexample.statemachine.sf.enumerate;

public enum GrantEvent {
    //开二类户
    CREATE_CARDII,
    //建档授信
    DOCUMENT_CREDIT,
    //建档授信回调
    DOCUMENT_CREDIT_CALLBACK,
    //放款
    GRANTED,
    //放款校验
    GRANT_CHECKED,
    //结束
    FINISHED
}
