package ma.enset.service;

import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Chat;
import ma.enset.stubs.chatServiceGrpc;
import java.util.HashMap;
import java.util.Map;

public class chatServiceServer extends chatServiceGrpc.chatServiceImplBase{
    Map<String ,StreamObserver<Chat.messageResponse>> userList=new HashMap<>();
    @Override
    public void login(Chat.connect request, StreamObserver<Chat.messageResponse> responseObserver) {
        System.out.println("loginnnnnnn");
        String userName=request.getUserName();
        userList.put(userName,responseObserver);
        Chat.messageResponse response = Chat.messageResponse.newBuilder()
                .setFrom("server")
                .setMessage("Hello "+userName+" welcome to chatPlaform")
                .build();
        responseObserver.onNext(response);
    }

    @Override
        public void sendTo(Chat.messageRequest request, StreamObserver<Chat.messageResponse> responseObserver) {
            System.out.println("sendTooo");
            String from=request.getFrom();
            String to=request.getTo();
            String message=request.getMessage();
        if(userList.containsKey(to)){
            Chat.messageResponse response = Chat.messageResponse.newBuilder()
                    .setFrom(from)
                    .setMessage(message)
                    .build();
            userList.get(to).onNext(response);
        }
    }

    private void broadcast(Chat.messageResponse request) {
        for (StreamObserver<Chat.messageResponse> observer : userList.values()) {
            observer.onNext(request);
        }
    }
}
