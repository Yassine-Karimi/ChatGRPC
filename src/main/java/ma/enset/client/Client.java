package ma.enset.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Chat;
import ma.enset.stubs.chatServiceGrpc;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    static chatServiceGrpc.chatServiceStub asyStub;
    static String userName;

    public static void login(){
        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",1111)
                .usePlaintext()
                .build();
        asyStub= chatServiceGrpc.newStub(managedChannel);
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter user");
        userName=myObj.nextLine();
        Chat.connect requestLogin= Chat.connect.newBuilder()
                .setUserName(userName)
                .build();
        asyStub.login(requestLogin, new StreamObserver<Chat.messageResponse>() {

            @Override
            public void onNext(Chat.messageResponse messageResponse) {
                System.out.println(messageResponse);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    public static void send(){
        Scanner myObj = new Scanner(System.in);
        while (true){
            System.out.println("Enter receiver");
            String to=myObj.nextLine();
            System.out.println("Enter message");
            String message=myObj.nextLine();
            Chat.messageRequest request= Chat.messageRequest.newBuilder()
                    .setFrom(userName)
                    .setTo(to)
                    .setMessage(message)
                    .build();

            asyStub.sendTo(request, new StreamObserver<Chat.messageResponse>() {

                @Override
                public void onNext(Chat.messageResponse messageResponse) {

                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {

                }
            });
        }
    }


    public static void main(String[] args) throws IOException {
        login();
        System.out.println("!!!!!!!!!!!!!!!");
        send();
        System.in.read();
    }
}
