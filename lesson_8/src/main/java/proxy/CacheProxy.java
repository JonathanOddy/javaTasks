package proxy;

import annotations.Cache;
import annotations.CacheType;
import serializationService.Result;
import serializationService.SerializationUtil;
import service.Service;
import service.ServiceImpl;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CacheProxy implements InvocationHandler {

    private static final String cacheFileRoot = System.getProperty("user.dir") + "/lesson_8/src/main/resources/";
    private static final String formatOfCacheFile = ".ser";
    private final Object delegate;
    private final Map<List<Object>, Object> cacheInMemory = new HashMap();
    private final Map<List<Object>, Object> cacheInFile = new HashMap();

    public CacheProxy(Object delegate) throws IOException, ClassNotFoundException {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IOException, ClassNotFoundException {
        if(!method.isAnnotationPresent(Cache.class)) {
            return invoke(method, args);
        } else {
            return cacheInvoke(method, args);
        }
    }

    private Object cacheInvoke(Method method, Object[] args) throws IOException, ClassNotFoundException {
        Cache annotation = method.getAnnotation(Cache.class);
        File cacheFile = getCacheFile(method, annotation);

        List<Object> keys = Arrays.asList(method.getName(), List.of(args));
        if (resultInCache(keys) != null) {
            System.out.println(localTime() + "Результат метода " + keys + " взят из кэша");
            return resultInCache(keys);
        } else {
            Result result = new Result(method, args, invoke(method, args));
            loadResultInCache(annotation, result, cacheFile);
            return result.getResult();
        }
    }

    private File getCacheFile(Method method, Cache annotation) throws IOException, ClassNotFoundException {

        String filename = getFileName(method, annotation);
        File cacheFile = new File(cacheFileRoot + filename + formatOfCacheFile);
        if (cacheFile.exists()) {
            List<Result> initialCacheFromFile = SerializationUtil.deserialize(cacheFile);
            initialCacheFromFile.forEach(res -> cacheInFile.put(res.getKeys(), res.getResult()));
        }
        return cacheFile;
    }

    private String getFileName(Method method, Cache annotation) {
        String filename = annotation.fileNamePrefix();
        if (filename.equals("")) {
            filename = method.getName();
        }
        return filename;
    }

    private void loadResultInCache(Cache annotation, Result result, File cacheFile) throws IOException, ClassNotFoundException {
        switch (annotation.cacheType()) {
            case IN_MEMORY: {
                cacheInMemory.put(result.getKeys(), result.getResult());
                System.out.println(localTime() + "Результат метода " + result.getKeys() + " записан в оперативную память");
                break;
            }
            case FILE: {
                cacheInFile.put(result.getKeys(), result.getResult());
                loadResultInCache(cacheFile, result);
                System.out.println(localTime() + "Результат метода " + result.getKeys() + " записан в файл");
                break;
            }
        }
    }

    private String localTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss")) +"\t";
    }


    private void loadResultInCache(File cacheFile, Result result) throws IOException, ClassNotFoundException {
        if (cacheFile.exists()) {
            List<Result> initialCacheFromFile = SerializationUtil.deserialize(cacheFile);
            initialCacheFromFile.forEach(res -> cacheInFile.put(res.getKeys(), res.getResult()));
        }
        SerializationUtil.serialize(result, cacheFile);
    }


    private Object resultInCache(List<Object> key) {
        if (cacheInMemory.containsKey(key)) {
            return cacheInMemory.get(key);
        } else {
            if (cacheInFile.containsKey(key)) {
                return cacheInFile.getOrDefault(key, null);
            }
            else return null;
        }
    }

    private List<Object> readKeys(Method method, Object[] args) {
        List<Object> keys = new ArrayList<>();
        keys.add(method);
        keys.addAll(List.of(args));
        return keys;
    }

    private Object invoke(Method method, Object[] args) {
        try {
            return method.invoke(delegate, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Impossible", e);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Service service = new ServiceImpl();

        Service serviceProxy = (Service) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(), service.getClass().getInterfaces(), new CacheProxy(service)
        );

        serviceProxy.work("log").forEach(System.out::println);
        serviceProxy.work("book").forEach(System.out::println);
        serviceProxy.work("comic").forEach(System.out::println);

        serviceProxy.work("coins").forEach(System.out::println);
        serviceProxy.work("tickets").forEach(System.out::println);
    }
}
