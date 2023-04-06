package it.polimi.ingsw.net;

import com.google.gson.reflect.TypeToken;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ServerTest<R extends RemoteInterface> implements UserAccepter<R>{

    public static void main(String[] args){
        ServerTest<TestClientInterface> serverTest = new ServerTest<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        TypeToken<TestClientInterface> typeToken = new TypeToken<>(){};
        TypeToken<TestServerInterface> typeToken1 = new TypeToken<>(){};
        Supplier<UserAdapterInterface<TestClientInterface>> userAdapterInterfaceSupplier = UserAdapter::new;
        SocketAccepter<TestServerInterface,TestClientInterface> socketAccepter = new SocketAccepter<TestServerInterface,TestClientInterface>(2147,serverTest,serverTest,typeToken,executorService,userAdapterInterfaceSupplier,typeToken1);
        new Thread(socketAccepter).start();
        System.out.println("Server pronto");
    }
    /*public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        ServerTest<TestClientInterface> serverTest = new ServerTest<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        TypeToken<TestServerInterface<TestClientInterface>> typeToken1 = new TypeToken<>(){};
        TypeToken<TestClientInterface> typeToken2 = new TypeToken<>(){};
        Supplier<UserAdapterInterface<TestClientInterface>> userAdapterInterfaceSupplier = UserAdapter::new;
        RmiAccepter<TestServerInterface<TestClientInterface>,TestClientInterface> rmiAccepter = new RmiAccepter<TestServerInterface<TestClientInterface>, TestClientInterface>(2147,serverTest,serverTest,serverTest,typeToken2,typeToken1,executorService,userAdapterInterfaceSupplier);
        Registry registry = LocateRegistry.createRegistry(2147);
        RemoteAccepterInterface<ServerInterface<ClientInterface>,ClientInterface> stub = (RemoteAccepterInterface<ServerInterface<ClientInterface>,ClientInterface>) UnicastRemoteObject.exportObject(rmiAccepter,2147);
        registry.bind("default",stub);
        System.out.println("Server pronto");
    }*/

    @Override
    public boolean acceptUser(User<R> user) {
        System.out.println("new user");
        return true;
    }


    public void check(User<TestClientInterface> user, String string) throws RemoteException {
        System.out.println(string);
        user.getConnectionManager().getRemoteTarget().check("ciao anche a te!!!!");
    }
    public void nop(User<TestClientInterface> user){
        //System.out.println("nop");
    }
}
