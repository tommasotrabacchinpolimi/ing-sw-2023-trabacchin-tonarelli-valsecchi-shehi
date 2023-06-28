package it.polimi.ingsw.net_alternative;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The {@code SocketAccepter} class is responsible for accepting incoming client connections on a specified port.
 * Each accepted connection is wrapped in a {@link ClientSocketImpl} instance and starts a new thread for communication.
 * Additionally, a {@link ServerHeartBeater} thread is started to monitor the client's connection status.

 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class SocketAccepter implements Runnable {
    /**
     * Used to properly manage requests from client to server
     */
    private final ServerDispatcher serverDispatcher;
    /**
     * Port used for connection
     */
    private final int port;
    /**
     * To properly manage the loss of connection of the Server
     */
    private final OnServerConnectionLostListener onServerConnectionLostListener;

    /**
     * Constructs a {@code SocketAccepter} object with the specified parameters.
     *
     * @param serverDispatcher             the server dispatcher to handle incoming client messages.
     * @param port                         the port number to listen for incoming connections.
     * @param onServerConnectionLostListener the listener to notify when a server connection is lost.
     */
    public SocketAccepter(ServerDispatcher serverDispatcher, int port, OnServerConnectionLostListener onServerConnectionLostListener) {
        this.port = port;
        this.serverDispatcher = serverDispatcher;
        this.onServerConnectionLostListener = onServerConnectionLostListener;
    }

    /**
     * Starts accepting incoming client connections and creates corresponding client socket instances.
     * Each accepted connection spawns a new thread for communication and a server heartbeater thread.
     */
    @Override
    public void run() {
        try {
            System.out.println("SocketAccepter running...");
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    socket.setSoTimeout(10000);
                    System.out.println("Client connection accepted...");

                    ClientSocketImpl clientSocket = new ClientSocketImpl(socket, serverDispatcher, onServerConnectionLostListener);

                    ServerHeartBeater serverHeartBeater = new ServerHeartBeater(clientSocket, 1000, onServerConnectionLostListener);
                    new Thread(clientSocket).start();
                    new Thread(serverHeartBeater).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
