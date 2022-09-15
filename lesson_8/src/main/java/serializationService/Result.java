package serializationService;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Object> keys;
    private final Object result;

    public Result(List<Object> keys, Object invokeResult) {
        this.keys = keys;
        this.result = invokeResult;
    }

    public List<Object> getKeys() {
        return keys;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "keys=" + keys +
                ", result=" + result +
                '}';
    }

}
