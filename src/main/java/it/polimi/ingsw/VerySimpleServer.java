package it.polimi.ingsw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class VerySimpleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = serverSocket.accept();
        BufferedWriter  w = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String name = bufferedInputStream.readLine();
        w.write("ciao "+name+"\n");
        w.flush();
        System.out.println("success");
    }
}
