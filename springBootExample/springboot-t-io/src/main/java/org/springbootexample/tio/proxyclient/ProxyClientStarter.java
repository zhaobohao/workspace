package org.springbootexample.tio.proxyclient;

import org.springbootexample.tio.packet.HelloPacket;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientTioConfig;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;

public class ProxyClientStarter {
    //服务器节点
    public static Node serverNode = new Node(ProxyConst.SERVER, ProxyConst.PORT);
    //handler, 包括编码、解码、消息处理
    public static ClientAioHandler tioClientHandler = new ProxyClientAioHandler();
    //事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
    public static ClientAioListener aioListener = null;
    //断链后自动连接的，不想自动连接请设为null
    private static ReconnConf reconnConf = new ReconnConf(5000L);
    //一组连接共用的上下文对象
    public static ClientTioConfig clientTioConfig = new ClientTioConfig(tioClientHandler, aioListener, reconnConf);
    public static TioClient tioClient = null;
    public static ClientChannelContext clientChannelContext = null;

    /**
     * 启动程序入口
     */
    private static void init() throws Exception {
        if (clientChannelContext == null) {
            clientTioConfig.setName("first application");
            clientTioConfig.setHeartbeatTimeout(ProxyConst.TIMEOUT);
            tioClient = new TioClient(clientTioConfig);
            clientChannelContext = tioClient.connect(serverNode);
        }
    }

    public static void send() throws Exception {
        send("hello world!");
    }

    public static void send(String content) throws Exception {
        init();
        HelloPacket packet = new HelloPacket();
        packet.setBody(content.getBytes(HelloPacket.CHARSET));
        Tio.send(clientChannelContext, packet);
//        Tio.synSend(clientChannelContext,packet,3000);
    }
}
