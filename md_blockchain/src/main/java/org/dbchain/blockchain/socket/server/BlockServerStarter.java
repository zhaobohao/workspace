package org.dbchain.blockchain.socket.server;

import org.dbchain.blockchain.socket.common.Const;
import org.springframework.stereotype.Component;
import org.tio.server.ServerTioConfig;
import org.tio.server.TioServer;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * server启动器
 *
 * @author zhaobo create on 2020/3/12.
 */
@Component
public class BlockServerStarter {

    @PostConstruct
    public void serverStart() throws IOException {
        ServerAioHandler serverAioHandler = new BlockServerAioHandler();
        ServerAioListener serverAioListener = new BlockServerAioListener();
        ServerTioConfig serverGroupContext = new ServerTioConfig(serverAioHandler, serverAioListener);
        TioServer aioServer = new TioServer(serverGroupContext);
        //本机启动服务
        aioServer.start(null, Const.PORT);
    }
}
