package org.dbchain.blockchain.socket.body;

import org.dbchain.blockchain.socket.pbft.msg.VoteMsg;

/**
 * pbft投票
 * @author zhaobo create on 2020/4/25.
 */
public class VoteBody extends BaseBody {
    private VoteMsg voteMsg;

    public VoteBody() {
        super();
    }

    public VoteBody(VoteMsg voteMsg) {
        super();
        this.voteMsg = voteMsg;
    }

    public VoteMsg getVoteMsg() {
        return voteMsg;
    }

    public void setVoteMsg(VoteMsg voteMsg) {
        this.voteMsg = voteMsg;
    }
}
