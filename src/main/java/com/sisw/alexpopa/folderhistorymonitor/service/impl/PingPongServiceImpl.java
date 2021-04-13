package com.sisw.alexpopa.folderhistorymonitor.service.impl;

import com.sisw.alexpopa.folderhistorymonitor.grpc.PingPongServiceGrpc;
import com.sisw.alexpopa.folderhistorymonitor.grpc.PingRequest;
import com.sisw.alexpopa.folderhistorymonitor.grpc.PongResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author Alex Daniel Popa
 */
@GrpcService
public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {

    @Override
    public void ping(PingRequest request, StreamObserver<PongResponse> responseObserver) {
        String ping = new StringBuilder()
                .append("pong")
                .toString();

        PongResponse response = PongResponse.newBuilder()
                .setPong(ping)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
