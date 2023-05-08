package it.polimi.ingsw.view.tui;

import java.io.*;

public class CustomConsole implements Runnable{

    private static final int INITIAL_CAPACITY = 64;
    private InputStreamReader reader;
    private PrintWriter printWriter;

    private char[] buffer;

    private int currentBufferIndex;

    private final Object lock;

    public CustomConsole(InputStream inputStream, OutputStream outputStream) {
        this.reader = new InputStreamReader(inputStream);
        this.printWriter = new PrintWriter(outputStream);
        buffer = new char[INITIAL_CAPACITY];
        lock = new Object();
        currentBufferIndex = 0;
    }

    public String readLine() {
        return null;
    }

    @Override
    public void run() {
        

    }
}
