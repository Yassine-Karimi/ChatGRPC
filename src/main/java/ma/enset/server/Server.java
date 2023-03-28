package ma.enset.server;

import io.grpc.ServerBuilder;
import ma.enset.service.chatServiceServer;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        io.grpc.Server server= ServerBuilder.forPort(1111)
                .addService(new chatServiceServer())
                .build();
        server.start();
        server.awaitTermination();
    }
}
