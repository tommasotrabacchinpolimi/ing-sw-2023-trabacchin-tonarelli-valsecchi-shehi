package it.polimi.ingsw.view.tui;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * The AsyncReader class implements the {@link Runnable} interface and is responsible for asynchronously reading input from a {@link BufferedReader}.
 * It continuously reads input from the {@link BufferedReader} and passes it to the associated {@link TUI} for processing.
 *
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AsyncReader implements Runnable{
    /**
     * The {@link TUI} instance associated with the class.
     */
    private final TUI tui;
    /**
     * Attribute that represents the {@link BufferedReader}.
     */
    private final BufferedReader bufferedReader;

    /**
     * Constructs an AsyncReader object with the specified {@link TUI} and {@link BufferedReader}.
     * @param tui the {@link TUI} (Text User Interface) object
     * @param bufferedReader the {@link BufferedReader} to read input from
     */
    public AsyncReader(TUI tui, BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        this.tui = tui;
    }

    /**
     * Continuously reads input from the {@link BufferedReader} and passes it to the {@link TUI} for processing.
     * If an {@link IOException} occurs while reading the input, a {@link RuntimeException} is thrown.
     *
     * @see Runnable#run()
     */
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
