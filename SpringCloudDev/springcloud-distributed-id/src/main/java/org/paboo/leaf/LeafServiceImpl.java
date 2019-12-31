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

import io.grpc.stub.StreamObserver;
import org.paboo.leaf.proto.LeafMsg;
import org.paboo.leaf.proto.LeafReq;
import org.paboo.leaf.proto.LeafResp;
import org.paboo.leaf.proto.LeafServiceGrpc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonard Woo
 */
public class LeafServiceImpl extends LeafServiceGrpc.LeafServiceImplBase {
    private static Map<Integer, SnowflakeImpl> snowflakeMap = new HashMap<Integer, SnowflakeImpl>(64);
    static {
        snowflakeMap.put(0,new SnowflakeImpl(0, 0));
    }
    @Override
    public void idGen(LeafReq request, StreamObserver<LeafResp> responseObserver) {
        SnowflakeImpl sf;
        if (snowflakeMap.containsKey(request.getServiceId() * 100 + request.getNodeId())) {
            sf = snowflakeMap.get(request.getServiceId() * 100 + request.getNodeId());
        } else {
            sf = new SnowflakeImpl(request.getServiceId(), request.getNodeId());
            snowflakeMap.put(request.getServiceId() * 100 + request.getNodeId(), sf);
        }
        responseObserver.onNext(LeafResp.newBuilder().setId(sf.nextId()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void idPar(LeafResp request, StreamObserver<LeafMsg> responseObserver) {
        responseObserver.onNext(LeafMsg.newBuilder().setMessage(snowflakeMap.get(0).formatId(request.getId())).build());
        responseObserver.onCompleted();
    }

    public static void main(String[] args) {
        SnowflakeImpl sf = new SnowflakeImpl(2, 3);
        for (int i = 0; i < 1000; i++) {
            System.out.println(sf.nextId());
            System.out.println(sf.formatId(sf.nextId()));
        }
    }

}
