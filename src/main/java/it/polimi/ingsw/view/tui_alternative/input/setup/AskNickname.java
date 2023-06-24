package it.polimi.ingsw.view.tui_alternative.input.setup;

import it.polimi.ingsw.view.tui_alternative.TUI;
import it.polimi.ingsw.view.tui_alternative.input.Input;

import java.io.PrintStream;

public class AskNickname extends Input {

    public AskNickname(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(true);
    }

    @Override
    public void printPrompt() {
        getOut().print("""
                          .         .                                                                                                                                   \s
                         ,8.       ,8.   `8.`8888.      ,8'              d888888o.   8 8888        8 8 8888888888   8 8888         8 8888888888    8 8888 8 8888888888  \s
                        ,888.     ,888.   `8.`8888.    ,8'             .`8888:' `88. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                       .`8888.   .`8888.   `8.`8888.  ,8'              8.`8888.   Y8 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                      ,8.`8888. ,8.`8888.   `8.`8888.,8'               `8.`8888.     8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                     ,8'8.`8888,8^8.`8888.   `8.`88888'                 `8.`8888.    8 8888        8 8 888888888888 8 8888         8 888888888888  8 8888 8 888888888888\s
                    ,8' `8.`8888' `8.`8888.   `8. 8888                   `8.`8888.   8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                   ,8'   `8.`88'   `8.`8888.   `8 8888                    `8.`8888.  8 8888888888888 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                  ,8'     `8.`'     `8.`8888.   8 8888                8b   `8.`8888. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                 ,8'       `8        `8.`8888.  8 8888                `8b.  ;8.`8888 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                ,8'         `         `8.`8888. 8 8888                 `Y8888P ,88P' 8 8888        8 8 888888888888 8 888888888888 8 8888          8 8888 8 888888888888\s
                """);
        getOut().println("Welcome to My Shelfie!");
        getOut().println("Now insert your unique nickname: ");
    }

    @Override
    public void readLine(String line) {
        getTUI().setCurrentInput(new AskCreateOrJoin(getTUI(), getOut(), line));
    }
}
