package it.polimi.ingsw.view.tui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProvaEscape {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("\033[2J");
        System.out.println("\033[H");
        System.out.println("ciao1");
        System.out.println("ciao2");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\033[s");
            System.out.println("\033[2;5H");
            System.out.println("\033[1J");
            System.out.println("\033[H");

            System.out.println("ciao3");
            System.out.println("ciao4");
            System.out.println("\033[u");
        }
        ).start();
        String input = bufferedReader.readLine();
        System.out.println("input: " + input);


    }
}

class Write implements Runnable{

    @Override
    public void run() {

    }
}