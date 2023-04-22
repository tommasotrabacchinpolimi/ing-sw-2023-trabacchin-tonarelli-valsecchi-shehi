package it.polimi.ingsw;

import java.io.*;
import java.net.Socket;

public class VerySimpleClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        BufferedWriter w = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        w.write("tommy\n");
        w.flush();
        System.out.println(bufferedInputStream.readLine());
    }
}
