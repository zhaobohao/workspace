package org.dbchain.blockchain.socket.server;

import org.dbchain.blockchain.ApplicationContextProvider;
import org.dbchain.blockchain.socket.distruptor.base.BaseEvent;
import org.dbchain.blockchain.socket.distruptor.base.MessageProducer;
import org.dbchain.blockchain.socket.base.AbstractAioHandler;
import org.dbchain.blockchain.socket.packet.BlockPacket;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

/**
 * server端处理所有client请求的入口
 * @author zhaobo create on 2020/3/12.
 */
public class BlockServerAioHandler extends AbstractAioHandler implements ServerAioHandler {


    /**
     * 自己是server，此处接收到客户端来的消息。这里是入口
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) {
        BlockPacket blockPacket = (BlockPacket) packet;

        //使用Disruptor来publish消息。所有收到的消息都进入Disruptor，同BlockClientAioHandler
        ApplicationContextProvider.getBean(MessageProducer.class).publish(new BaseEvent(blockPacket, channelContext));
    }
}
