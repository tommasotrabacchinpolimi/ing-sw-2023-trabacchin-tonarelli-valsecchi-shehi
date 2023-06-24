package it.polimi.ingsw.view.tui_alternative;

import java.io.BufferedReader;
import java.io.IOException;

public class AsyncReader implements Runnable{
    private final TUI tui;

    private final BufferedReader bufferedReader;

    public AsyncReader(TUI tui, BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        this.tui = tui;
    }

    @Override
    public void run() {
        while(true) {
            try {
                tui.readLine(bufferedReader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
