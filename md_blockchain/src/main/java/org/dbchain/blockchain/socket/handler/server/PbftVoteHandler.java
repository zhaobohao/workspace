package org.dbchain.blockchain.socket.handler.server;

import org.dbchain.blockchain.socket.pbft.msg.VoteMsg;
import org.dbchain.blockchain.socket.pbft.queue.MsgQueueManager;
import org.dbchain.blockchain.ApplicationContextProvider;
import org.dbchain.blockchain.socket.base.AbstractBlockHandler;
import org.dbchain.blockchain.socket.body.VoteBody;
import org.dbchain.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * pbft投票处理
 *
 * @author zhaobo create on 2020/3/12.
 */
public class PbftVoteHandler extends AbstractBlockHandler<VoteBody> {
    private Logger logger = LoggerFactory.getLogger(PbftVoteHandler.class);


    @Override
    public Class<VoteBody> bodyClass() {
        return VoteBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, VoteBody voteBody, ChannelContext channelContext) {
        VoteMsg voteMsg = voteBody.getVoteMsg();
        logger.info("收到来自于<" + voteMsg.getAppId() + "><投票>消息，投票信息为[" + voteMsg + "]");

        ApplicationContextProvider.getBean(MsgQueueManager.class).pushMsg(voteMsg);
        return null;
    }
}
