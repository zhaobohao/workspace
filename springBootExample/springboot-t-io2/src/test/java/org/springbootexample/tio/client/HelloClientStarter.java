package org.springbootexample.tio.client;

import cn.hutool.core.util.RandomUtil;
import org.springbootexample.tio.packet.HelloPacket;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientTioConfig;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;

public class HelloClientStarter {
    //服务器节点
    public static Node serverNode = new Node(Const.SERVER, Const.PORT);
    //handler, 包括编码、解码、消息处理
    public static ClientAioHandler tioClientHandler = new HelloClientAioHandler();
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
    public static void main(String[] args) throws Exception {
        clientTioConfig.setName("first application");
        clientTioConfig.setHeartbeatTimeout(Const.TIMEOUT);
        tioClient = new TioClient(clientTioConfig);
        clientChannelContext = tioClient.connect(serverNode);
        //连上后，发条消息玩玩,通过测试可知，收和发是异步的。
        for (int i = 0; i < 1; i++) {
            send(RandomUtil.randomNumbers(5));
        }
        //下面是测试多客户端连接
//        for (int i = 0; i <1000 ; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        HelloPacket packet = new HelloPacket();
//                        packet.setBody(RandomUtil.randomString(10).getBytes(HelloPacket.CHARSET));
//                        System.out.println(new String(packet.getBody()));
//                        Tio.send( new TioClient(clientTioConfig).connect(serverNode), packet);
//                        Thread.sleep(RandomUtil.randomLong(100,3000));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }

    }

    private static void send() throws Exception {
       send("hello world!");
    }
    private static void send(String content) throws Exception {
        HelloPacket packet = new HelloPacket();
        packet.setBody(content.getBytes(HelloPacket.CHARSET));
        //Tio.send(clientChannelContext, packet);
        Tio.synSend(clientChannelContext,packet,3000);
    }
}
