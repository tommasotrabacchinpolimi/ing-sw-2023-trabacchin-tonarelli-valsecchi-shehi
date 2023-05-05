package it.polimi.ingsw.net;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 06/04/2023
 */

public class SocketAccepter<L extends RemoteInterface, R extends RemoteInterface> implements Runnable{
    private final int portNumber;
    private final Object localTarget;
    private final TypeToken<R> remoteTargetClass;
    private final UserAccepter<R> userAccepter;
    private final Supplier<UserAdapterInterface<R>> userAdapterSupplier;
    private final TypeToken<L> localTargetClass;
    public SocketAccepter(int portNumber, UserAccepter<R> userAccepter, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService){
        this.portNumber = portNumber;
        this.localTarget = localTarget;
        this.remoteTargetClass = remoteTargetClass;
        this.userAccepter = userAccepter;
        this.userAdapterSupplier = null;
        this.localTargetClass = null;
    }
    public SocketAccepter(int portNumber, UserAccepter<R> userAccepter, Object localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService, Supplier<UserAdapterInterface<R>> userAdapterSupplier, TypeToken<L> localTargetClass){
        this.portNumber = portNumber;
        this.localTarget = localTarget;
        this.remoteTargetClass = remoteTargetClass;
        this.userAccepter = userAccepter;
        this.userAdapterSupplier = userAdapterSupplier;
        this.localTargetClass = localTargetClass;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while(true){
                Socket socket = serverSocket.accept();
                User<R> user = new User<R>();
                if(userAccepter.acceptUser(user)){
                    SocketConnectionManager<L, R> socketConnectionManager = new SocketConnectionManager<>();
                    if(userAdapterSupplier==null){
                        socketConnectionManager.init(socket,user,(L)localTarget, remoteTargetClass);
                    }
                    else{
                        UserAdapterInterface<R> userAdapterInterface = userAdapterSupplier.get();
                        userAdapterInterface.setUser(user);
                        userAdapterInterface.setTarget(localTarget);
                        L newLocalTarget = (L) Proxy.newProxyInstance(localTargetClass.getRawType().getClassLoader(),new Class[]{localTargetClass.getRawType()},userAdapterInterface);
                        socketConnectionManager.init(socket,user,newLocalTarget, remoteTargetClass);
                    }
                    user.setConnectionManager(socketConnectionManager);
                    userAccepter.registerConnectionDownListener(user);
                }
                else{
                    try {
                        socket.close();
                    }catch(IOException ignored){
                        ignored.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
