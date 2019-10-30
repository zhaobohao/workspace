package org.springbootexample.tio.handler;

import cn.hutool.core.util.RandomUtil;
import org.springbootexample.tio.packet.HelloPacket;
import org.springbootexample.tio.utils.PacketUtil;
import org.tio.common.starter.annotation.TioServerMsgHandler;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.TioConfig;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import java.nio.ByteBuffer;

/**
 * 消息处理 handler, 通过加 {@link TioServerMsgHandler} 注解启用，否则不会启用
 * 注意: handler 是必须要启用的，否则启动会抛出 {@link TioMsgHandlerNotFoundException} 异常
 *
 * @author yangjian
 */
@TioServerMsgHandler
public class HelloServerMsgHandler implements ServerAioHandler {
    /**
     * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
     * 总的消息结构：消息头 + 消息体
     * 消息头结构：    4个字节，存储消息体的长度
     * 消息体结构：   对象的json串的byte[]
     */
    @Override
    public HelloPacket decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException
    {
        HelloPacket packet=  PacketUtil.decode(buffer, limit, position, readableLength, channelContext);
        if(packet.getSynSeq()!=null)
        {
            try {
                handler(packet,channelContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return packet;
    }
    /**
     * 编码：把业务消息包编码为可以发送的ByteBuffer
     * 总的消息结构：消息头 + 消息体
     * 消息头结构：    4个字节，存储消息体的长度
     * 消息体结构：   对象的json串的byte[]
     */
    @Override
    public ByteBuffer encode(Packet packet, TioConfig tioConfig, ChannelContext channelContext)
    {
        return PacketUtil.encode(packet, tioConfig, channelContext);
    }
    /**
     * 处理消息
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        HelloPacket helloPacket = (HelloPacket) packet;
        byte[] body = helloPacket.getBody();
        if (body != null) {
            String str = new String(body, HelloPacket.CHARSET);
            System.out.println("收到消息：" + str);
            HelloPacket resppacket = new HelloPacket();
            resppacket.setBody(("收到了你的消息，你的消息是:" + str).getBytes(HelloPacket.CHARSET));
            Tio.send(channelContext, resppacket);
        }
        return;
    }
}
