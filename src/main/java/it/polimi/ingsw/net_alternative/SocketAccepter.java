package it.polimi.ingsw.net_alternative;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketAccepter implements Runnable{

    private final ServerDispatcher serverDispatcher;

    private final int port;

    private final OnServerConnectionLostListener onServerConnectionLostListener;
    public SocketAccepter(ServerDispatcher serverDispatcher, int port, OnServerConnectionLostListener onServerConnectionLostListener) {
        this.port = port;
        this.serverDispatcher = serverDispatcher;
        this.onServerConnectionLostListener = onServerConnectionLostListener;
    }
    @Override
    public void run() {
        try{
            System.out.println("receiver running...");
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                try{
                    Socket socket = serverSocket.accept();
                    System.out.println("client received...");

                    ClientSocketImpl clientSocket = new ClientSocketImpl(socket, serverDispatcher, onServerConnectionLostListener);

                    ServerHeartBeater serverHeartBeater = new ServerHeartBeater(clientSocket, 5000, onServerConnectionLostListener);
                    new Thread(clientSocket).start();
                    new Thread(serverHeartBeater).start();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
