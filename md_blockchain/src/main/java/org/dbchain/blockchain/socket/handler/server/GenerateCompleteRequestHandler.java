package org.dbchain.blockchain.socket.handler.server;

import org.dbchain.blockchain.ApplicationContextProvider;
import org.dbchain.blockchain.block.Block;
import org.dbchain.blockchain.common.timer.TimerManager;
import org.dbchain.blockchain.core.manager.DbBlockManager;
import org.dbchain.blockchain.socket.base.AbstractBlockHandler;
import org.dbchain.blockchain.socket.body.RpcSimpleBlockBody;
import org.dbchain.blockchain.socket.packet.BlockPacket;
import org.dbchain.blockchain.socket.packet.NextBlockPacketBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import org.dbchain.blockchain.socket.client.PacketSender;

/**
 * 已生成了新区块的全网广播
 * @author zhaobo create on 2020/3/12.
 */
public class GenerateCompleteRequestHandler extends AbstractBlockHandler<RpcSimpleBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateCompleteRequestHandler.class);

    @Override
    public Class<RpcSimpleBlockBody> bodyClass() {
        return RpcSimpleBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcSimpleBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + "><生成了新的Block>消息，block hash为[" + rpcBlockBody.getHash() +
                "]");

        //延迟2秒校验一下本地是否有该区块，如果没有，则发请求去获取新Block
        //延迟的目的是可能刚好自己也马上就要生成同样的Block了，就可以省一次请求
        TimerManager.schedule(() -> {
            Block block = ApplicationContextProvider.getBean(DbBlockManager.class).getBlockByHash(rpcBlockBody
                    .getHash());
            //本地有了
            if (block == null) {
                logger.info("开始去获取别人的新区块");
                //在这里发请求，去获取group别人的新区块
                BlockPacket nextBlockPacket = NextBlockPacketBuilder.build();
                ApplicationContextProvider.getBean(PacketSender.class).sendGroup(nextBlockPacket);
            }
            return null;
        },2000);

        return null;
    }
}
