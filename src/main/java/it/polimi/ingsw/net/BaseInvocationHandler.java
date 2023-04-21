package it.polimi.ingsw.net;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BaseInvocationHandler implements InvocationHandler {

    public static final Method HASH_CODE;
    public static final Method EQUALS;
    public static final Method TO_STRING;

    static {
        Class<Object> object = Object.class;
        try {
            HASH_CODE = object.getDeclaredMethod("hashCode");
            EQUALS = object.getDeclaredMethod("equals", object);
            TO_STRING = object.getDeclaredMethod("toString");
        } catch (NoSuchMethodException e) {
            throw new Error(e);
        }
    }
    private final InvocationHandler specificInvocationHandler;

    public BaseInvocationHandler(InvocationHandler specificInvocationHandler) {
        this.specificInvocationHandler = specificInvocationHandler;
    }

    @Override
    public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.equals(EQUALS)) {
            return proxy == args[0];
        }
        else if (method.equals(HASH_CODE)) {
            return System.identityHashCode(proxy);
        }
        else if (method.equals(TO_STRING)) {
            return proxy.getClass() + "@" + Integer.toHexString(System.identityHashCode(proxy));
        }
        else {
            return specificInvocationHandler.invoke(proxy, method, args);
        }
    }
}
