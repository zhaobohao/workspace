package org.dbchain.blockchain.socket.pbft.listener;

import org.dbchain.blockchain.socket.packet.BlockPacket;
import org.dbchain.blockchain.socket.packet.PacketBuilder;
import org.dbchain.blockchain.socket.packet.PacketType;
import org.dbchain.blockchain.socket.pbft.event.MsgCommitEvent;
import org.dbchain.blockchain.socket.pbft.msg.VoteMsg;
import org.dbchain.blockchain.socket.body.VoteBody;
import org.dbchain.blockchain.socket.client.PacketSender;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听block可以commit消息
 * @author zhaobo create on 2020/4/25.
 */
@Component
public class CommitEventListener {
    @Resource
    private PacketSender packetSender;

    /**
     * block已经开始进入commit状态，广播消息
     *
     * @param msgCommitEvent
     *         msgCommitEvent
     */
    @EventListener
    public void msgIsCommit(MsgCommitEvent msgCommitEvent) {
        VoteMsg voteMsg = (VoteMsg) msgCommitEvent.getSource();

        //群发消息，通知所有节点，我已对该Block Prepare
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(new
                VoteBody(voteMsg)).build();

        //广播给所有人我已commit
        packetSender.sendGroup(blockPacket);
    }
}
