package it.polimi.ingsw.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 21/04/2023
 */
public class SynchronizedObjectOutputStream  {
    private final ObjectOutputStream objectOutputStream;
    public SynchronizedObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }


    public synchronized final void writeObject(Object object) throws IOException {
        this.objectOutputStream.writeObject(object);
        this.objectOutputStream.flush();
    }

    public synchronized final void flush() throws IOException {
        this.objectOutputStream.flush();
    }

    public synchronized final void reset() throws IOException {
        this.objectOutputStream.reset();
    }


}
