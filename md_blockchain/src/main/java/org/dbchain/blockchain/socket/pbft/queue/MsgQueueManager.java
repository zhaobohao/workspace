package org.dbchain.blockchain.socket.pbft.queue;

import org.dbchain.blockchain.socket.pbft.msg.VoteMsg;
import org.dbchain.blockchain.ApplicationContextProvider;
import org.dbchain.blockchain.socket.pbft.VoteType;
import org.springframework.stereotype.Component;

/**
 * @author zhaobo create on 2020/4/25.
 */
@Component
public class MsgQueueManager {

    public void pushMsg(VoteMsg voteMsg) {
    	BaseMsgQueue baseMsgQueue = null;
        switch (voteMsg.getVoteType()) {
            case VoteType
                    .PREPREPARE:
                baseMsgQueue = ApplicationContextProvider.getBean(PreMsgQueue.class);
                break;
            case VoteType.PREPARE:
                baseMsgQueue = ApplicationContextProvider.getBean(PrepareMsgQueue.class);
                break;
            case VoteType.COMMIT:
                baseMsgQueue = ApplicationContextProvider.getBean(CommitMsgQueue.class);
                break;
            default:
                break;
        }
        if(baseMsgQueue != null) {
        	baseMsgQueue.push(voteMsg);
        }
    }
}
