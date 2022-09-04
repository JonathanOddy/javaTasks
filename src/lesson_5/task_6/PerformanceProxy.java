package lesson_5.task_6;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalTime;

public class PerformanceProxy implements InvocationHandler {

    private final Object delegate;

    public PerformanceProxy(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(!method.isAnnotationPresent(Metric.class)) return invoke(method, args);
        else {
            LocalTime timeBeforeCalculation  = LocalTime.now();
            Object result = invoke(method, args);
            LocalTime timeAfterCalculation = LocalTime.now();

            System.out.println("Время выполнения программы: "
                    + Duration.between(timeBeforeCalculation,timeAfterCalculation).toMillis() + "мс");
            return result;
        }
    }

    private Object invoke(Method method, Object[] args) {
        try {
            return method.invoke(delegate,args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Impossible",e);
        }
    }
}
