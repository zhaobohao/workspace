/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.paboo.leaf;

import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.paboo.leaf.config.LeafConfiguration;

import java.io.IOException;

/**
 * @author Leonard Woo
 */
@Slf4j
public class LeafApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
        LeafConfiguration.getInstance().printBanner(System.out);

        int port = LeafConfiguration.getInstance().loadConfig().getPort();
        Server server = NettyServerBuilder.forPort(port)
                .addService(new LeafServiceImpl())
                .build();
        log.info("Service stating ... [0.0.0.0:" + port + "]");
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown()));
        server.awaitTermination();
    }
}
