package it.polimi.ingsw.net;

import java.beans.Statement;
import java.lang.reflect.Method;

public class UserAdapter<R extends RemoteInterface> implements UserAdapterInterface<R>{
    private User<R> user;
    private Object target;
    @Override
    public void setUser(User<R> user) {
        this.user = user;
    }

    @Override
    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        Object[] completeArgs;
        if(args!=null){
            completeArgs = new Object[args.length+1];
            completeArgs[0] = user;
            System.arraycopy(args, 0, completeArgs, 1, completeArgs.length - 1);
        }
        else{
            completeArgs = new Object[1];
            completeArgs[0] = user;
        }
        java.beans.Statement st = new Statement(target, method.getName(), completeArgs);
        st.execute();
        return null;
    }
}
