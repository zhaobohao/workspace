package org.springbootexample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springbootexample.tio.packet.HelloPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.Tio;
import org.tio.core.starter.TioServerBootstrap;

@RestController
public class HelloController {
    static Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private TioServerBootstrap bootstrap;
    @GetMapping("/")
    public String index()
    {
        return "Hello, tio-spring-boot-starter !!!";
    }
    /**
     * 推送消息到客户端
     * @throws Exception
     */
    @GetMapping("/push")
    public String pushMessage() throws Exception {
        HelloPacket packet = new HelloPacket();
        packet.setBody("This message is pushed by Tio Server.".getBytes(HelloPacket.CHARSET));
        Tio.sendToAll(bootstrap.getServerTioConfig(), packet);
        logger.info("Push a message to client successfully");
        return "Push a message to client successfully";
    }
}
