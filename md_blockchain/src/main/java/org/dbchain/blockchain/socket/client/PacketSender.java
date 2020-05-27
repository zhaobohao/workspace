package org.dbchain.blockchain.socket.client;

import org.dbchain.blockchain.ApplicationContextProvider;
import org.dbchain.blockchain.core.event.ClientRequestEvent;
import org.dbchain.blockchain.socket.packet.BlockPacket;
import org.springframework.stereotype.Component;
import org.tio.client.ClientGroupContext;
import org.tio.core.Aio;

import javax.annotation.Resource;

import static org.dbchain.blockchain.socket.common.Const.GROUP_NAME;

/**
 * 发送消息的工具类
 * @author zhaobo create on 2020/3/12.
 */
@Component
public class PacketSender {
    @Resource
    private ClientGroupContext clientGroupContext;

    public void sendGroup(BlockPacket blockPacket) {
        //对外发出client请求事件
        ApplicationContextProvider.publishEvent(new ClientRequestEvent(blockPacket));
        //发送到一个group
        Aio.sendToGroup(clientGroupContext, GROUP_NAME, blockPacket);
    }

}
