package lesson_5.task_7;

import lesson_5.task_6.Metric;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanUtils {
    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) {

        List<Method> gettersFrom = getMethodsStartWith(from.getClass(), "get");
        List<Method> settersTo = getMethodsStartWith(to.getClass(), "set");

        for (Method getterFrom : gettersFrom) {
            for (Method setterTo : settersTo) {
                if (getSubNameOfMethod(getterFrom, "get")
                        .equals(getSubNameOfMethod(setterTo, "set"))) {
                    try {
                        setterTo.invoke(to, getterFrom.invoke(from));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static List<Method> getMethodsStartWith(Class clazz, String startWith) {
        return Stream
                .iterate(clazz, Objects::nonNull, Class::getSuperclass)
                .flatMap(cl -> Stream.of(cl.getDeclaredMethods()))
                .filter(m -> m.getName().startsWith(startWith))
                .collect(Collectors.toList());
    }

    private static String getSubNameOfMethod(Method method, String startAfter) {
        return method.getName().substring(
                method.getName().lastIndexOf(startAfter) + startAfter.length());
    }

}

