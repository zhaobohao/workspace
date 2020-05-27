package org.dbchain.blockchain.socket.pbft.event;

import org.dbchain.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.context.ApplicationEvent;

/**
 * 消息已被验证，进入到Prepare集合中
 * @author zhaobo create on 2020/4/25.
 */
public class MsgPrepareEvent extends ApplicationEvent {
    public MsgPrepareEvent(VoteMsg source) {
        super(source);
    }
}
