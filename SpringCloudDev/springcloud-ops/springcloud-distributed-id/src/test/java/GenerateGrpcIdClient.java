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

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.paboo.leaf.proto.LeafReq;
import org.paboo.leaf.proto.LeafResp;
import org.paboo.leaf.proto.LeafMsg;
import org.paboo.leaf.proto.LeafServiceGrpc;

import java.util.concurrent.TimeUnit;

/**
 * @author Leonard Woo
 */
public class GenerateGrpcIdClient {


    private final ManagedChannel channel;
    private final LeafServiceGrpc.LeafServiceBlockingStub blockingStub;

    public GenerateGrpcIdClient(String host, int port) {
        this( ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() );
    }

    GenerateGrpcIdClient(ManagedChannelBuilder<?> channelBuilder) {
        this.channel = channelBuilder.build();
        this.blockingStub = LeafServiceGrpc.newBlockingStub(this.channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     *
     * serviceid [0,31]
     * nodeid  [0,31]
     * @return
     */
    public long getId(){
        LeafReq req = LeafReq.newBuilder().setServiceId(1).setNodeId(2).build();
        LeafResp resp = blockingStub.idGen(req);
        return resp.getId();
    }

    public String formatId(long id){
        LeafResp req = LeafResp.newBuilder().setId(id).build();
        LeafMsg resp = blockingStub.idPar(req);
        return resp.getMessage();
    }


    public static void main(String[] args) throws Exception{
        GenerateGrpcIdClient client = new GenerateGrpcIdClient("localhost", 9800);
        for(int i=0;i<10;i++) {
            long id = client.getId();
            System.out.println(id);
            System.out.println(client.formatId(id));
        }
    }
}