package lesson_5.task_5.cachedInFileInvocationHandler;

import lesson_5.task_5.annotations.Cache;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CachedInFileInvocationHandler implements InvocationHandler {

    private final String filename = System.getProperty("user.dir") + "/src/lesson_5/task_5/resources/cache.txt";
    private final Map<List<String>, String> calculationResults = readAllCacheFromFile();
    private final Object delegate;

    public CachedInFileInvocationHandler(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        if(!method.isAnnotationPresent(Cache.class)) return invoke(method, args);
        List<String> key = key(method, args);

        if (calculationResults.containsKey(key)) {
            System.out.println("Результат вычислений взят из кэша. Метод "
                    + method.getName() + ", аргументы: " + Arrays.toString(args));
            return Integer.parseInt(calculationResults.get(key));
        } else {
            System.out.println("Вычисляем значение и записываем в файл. Метод "
                    + method.getName() + ", аргументы: " + Arrays.toString(args));

                Object result = invoke(method, args);
                writeInFile(method, args, result);
                calculationResults.put(key(method,args), result.toString());
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

    private void writeInFile(Method method, Object[] args, Object result)  {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            printWriter.print("METHOD=");
            printWriter.print(method.getName());
            printWriter.print(",ARGUMENTS=");
            for (Object arg : args) {
                printWriter.print(arg);
                printWriter.print(",");
            }
            printWriter.print("RESULT=");
            printWriter.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<List<String>, String> readAllCacheFromFile() {
        try {
            return Files
                    .lines(Paths.get(filename))
                    .collect(Collectors.toMap(
                            line -> StreamSupport.stream(
                                            new MatchItr(Pattern.compile("[a-z0-9][\\w]*")
                                                    .matcher(line.substring(0, line.lastIndexOf("RES")))
                                            ),
                                            false)
                                    .collect(Collectors.toList()),
                            line -> line.substring(line.lastIndexOf("=") + 1)
                    ));
        } catch (NoSuchFileException e) {
            return new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> key(Method method, Object[] args) {
        List<String> key = new ArrayList<>();
        key.add(method.getName());
        for (Object arg : args) {
            key.add(arg.toString());
        }
        return key;
    }
}
