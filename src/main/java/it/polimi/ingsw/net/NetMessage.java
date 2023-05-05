package it.polimi.ingsw.net;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 27/03/2023
 */

public class NetMessage implements Serializable {
    @Serial
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
