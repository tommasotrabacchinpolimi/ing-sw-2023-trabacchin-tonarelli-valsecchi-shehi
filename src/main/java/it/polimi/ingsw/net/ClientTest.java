package it.polimi.ingsw.net;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 06/04/2023
 */


public class ClientTest<R extends RemoteInterface> implements TestClientInterface{

    public static void main(String[] args) throws IOException {
        ClientTest<TestServerInterface> clientTest = new ClientTest<>();
        TypeToken<TestServerInterface> typeToken = new TypeToken<>(){};
        SocketConnectionManager<TestClientInterface,TestServerInterface> socketConnectionManager = ConnectionBuilder.buildSocketConnection("localhost",2147,clientTest,typeToken);
        TestServerInterface serverInterface = socketConnectionManager.getRemoteTarget();
        serverInterface.check("ciao!!!!");
    }



    /*public static void main(String[] args) throws IOException, NotBoundException {
        ClientTest<TestServerInterface<TestClientInterface>> clientTest = new ClientTest<>();
        TypeToken<TestClientInterface> typeToken1 = new TypeToken<>() {};
        TypeToken<TestServerInterface<TestClientInterface>> typeToken2 = new TypeToken<>() {};
        RmiConnectionManager<TestClientInterface, TestServerInterface<TestClientInterface>> rmiConnectionManager = ConnectionBuilder.buildRmiConnection("localhost", 2148, clientTest, typeToken2, typeToken1);
        TestServerInterface<TestClientInterface> serverInterface = rmiConnectionManager.getRemoteTarget();
        serverInterface.check("ciao!!!!");
        System.out.println("finito");
    }*/

    @Override
    public void check(String string)throws RemoteException {

        System.out.println("ricevuto: " + string);
    }

    public void nop(){
        //System.out.println("nop");

    }
}
