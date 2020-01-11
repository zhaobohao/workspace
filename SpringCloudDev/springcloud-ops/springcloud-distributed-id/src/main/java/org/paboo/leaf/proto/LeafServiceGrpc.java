package org.paboo.leaf.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The leaf service definition
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.26.0)",
    comments = "Source: leaf.proto")
public final class LeafServiceGrpc {

  private LeafServiceGrpc() {}

  public static final String SERVICE_NAME = "org.paboo.leaf.proto.LeafService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<LeafReq,
      LeafResp> getIdGenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "IdGen",
      requestType = LeafReq.class,
      responseType = LeafResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<LeafReq,
      LeafResp> getIdGenMethod() {
    io.grpc.MethodDescriptor<LeafReq, LeafResp> getIdGenMethod;
    if ((getIdGenMethod = LeafServiceGrpc.getIdGenMethod) == null) {
      synchronized (LeafServiceGrpc.class) {
        if ((getIdGenMethod = LeafServiceGrpc.getIdGenMethod) == null) {
          LeafServiceGrpc.getIdGenMethod = getIdGenMethod =
              io.grpc.MethodDescriptor.<LeafReq, LeafResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "IdGen"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LeafReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LeafResp.getDefaultInstance()))
              .setSchemaDescriptor(new LeafServiceMethodDescriptorSupplier("IdGen"))
              .build();
        }
      }
    }
    return getIdGenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<LeafResp,
      LeafMsg> getIdParMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "IdPar",
      requestType = LeafResp.class,
      responseType = LeafMsg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<LeafResp,
      LeafMsg> getIdParMethod() {
    io.grpc.MethodDescriptor<LeafResp, LeafMsg> getIdParMethod;
    if ((getIdParMethod = LeafServiceGrpc.getIdParMethod) == null) {
      synchronized (LeafServiceGrpc.class) {
        if ((getIdParMethod = LeafServiceGrpc.getIdParMethod) == null) {
          LeafServiceGrpc.getIdParMethod = getIdParMethod =
              io.grpc.MethodDescriptor.<LeafResp, LeafMsg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "IdPar"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LeafResp.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  LeafMsg.getDefaultInstance()))
              .setSchemaDescriptor(new LeafServiceMethodDescriptorSupplier("IdPar"))
              .build();
        }
      }
    }
    return getIdParMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LeafServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LeafServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LeafServiceStub>() {
        @Override
        public LeafServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LeafServiceStub(channel, callOptions);
        }
      };
    return LeafServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LeafServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LeafServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LeafServiceBlockingStub>() {
        @Override
        public LeafServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LeafServiceBlockingStub(channel, callOptions);
        }
      };
    return LeafServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LeafServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LeafServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LeafServiceFutureStub>() {
        @Override
        public LeafServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LeafServiceFutureStub(channel, callOptions);
        }
      };
    return LeafServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The leaf service definition
   * </pre>
   */
  public static abstract class LeafServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void idGen(LeafReq request,
                      io.grpc.stub.StreamObserver<LeafResp> responseObserver) {
      asyncUnimplementedUnaryCall(getIdGenMethod(), responseObserver);
    }

    /**
     */
    public void idPar(LeafResp request,
                      io.grpc.stub.StreamObserver<LeafMsg> responseObserver) {
      asyncUnimplementedUnaryCall(getIdParMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getIdGenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                LeafReq,
                LeafResp>(
                  this, METHODID_ID_GEN)))
          .addMethod(
            getIdParMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                LeafResp,
                LeafMsg>(
                  this, METHODID_ID_PAR)))
          .build();
    }
  }

  /**
   * <pre>
   * The leaf service definition
   * </pre>
   */
  public static final class LeafServiceStub extends io.grpc.stub.AbstractAsyncStub<LeafServiceStub> {
    private LeafServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected LeafServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LeafServiceStub(channel, callOptions);
    }

    /**
     */
    public void idGen(LeafReq request,
                      io.grpc.stub.StreamObserver<LeafResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getIdGenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void idPar(LeafResp request,
                      io.grpc.stub.StreamObserver<LeafMsg> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getIdParMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The leaf service definition
   * </pre>
   */
  public static final class LeafServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<LeafServiceBlockingStub> {
    private LeafServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected LeafServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LeafServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public LeafResp idGen(LeafReq request) {
      return blockingUnaryCall(
          getChannel(), getIdGenMethod(), getCallOptions(), request);
    }

    /**
     */
    public LeafMsg idPar(LeafResp request) {
      return blockingUnaryCall(
          getChannel(), getIdParMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The leaf service definition
   * </pre>
   */
  public static final class LeafServiceFutureStub extends io.grpc.stub.AbstractFutureStub<LeafServiceFutureStub> {
    private LeafServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected LeafServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LeafServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<LeafResp> idGen(
        LeafReq request) {
      return futureUnaryCall(
          getChannel().newCall(getIdGenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<LeafMsg> idPar(
        LeafResp request) {
      return futureUnaryCall(
          getChannel().newCall(getIdParMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ID_GEN = 0;
  private static final int METHODID_ID_PAR = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LeafServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LeafServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ID_GEN:
          serviceImpl.idGen((LeafReq) request,
              (io.grpc.stub.StreamObserver<LeafResp>) responseObserver);
          break;
        case METHODID_ID_PAR:
          serviceImpl.idPar((LeafResp) request,
              (io.grpc.stub.StreamObserver<LeafMsg>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class LeafServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LeafServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return LeafProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LeafService");
    }
  }

  private static final class LeafServiceFileDescriptorSupplier
      extends LeafServiceBaseDescriptorSupplier {
    LeafServiceFileDescriptorSupplier() {}
  }

  private static final class LeafServiceMethodDescriptorSupplier
      extends LeafServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LeafServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (LeafServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LeafServiceFileDescriptorSupplier())
              .addMethod(getIdGenMethod())
              .addMethod(getIdParMethod())
              .build();
        }
      }
    }
    return result;
  }
}
