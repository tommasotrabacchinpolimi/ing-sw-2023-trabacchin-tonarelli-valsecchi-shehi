package it.polimi.ingsw.net;

import java.io.Serializable;

public class NetMessage implements Serializable {
    private static final long serialVersionUID = 5465465224565456L;
    private final String methodName;
    private final Object[] params;
    public NetMessage(String methodName, Object[] params){
        this.methodName = methodName;
        this.params = params;
    }

    public String getMethod() {
        return methodName;
    }
    public Object[] getParams(){
        return params;
    }
}
