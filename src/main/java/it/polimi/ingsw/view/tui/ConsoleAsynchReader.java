package it.polimi.ingsw.view.tui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleAsynchReader implements Runnable{
    private TUIStateMachine tuiStateMachine;
    public ConsoleAsynchReader(TUIStateMachine tuiStateMachine) {
        this.tuiStateMachine = tuiStateMachine;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                tuiStateMachine.newLine(bufferedReader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
