package proxy;

import annotations.Cache;
import annotations.CacheType;
import serializationService.Result;
import serializationService.SerializationUtil;
import service.Service;
import service.ServiceImpl;
import zipUtil.ZipUtil;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.*;

public class CacheProxy implements InvocationHandler {

    private static final String cacheFileRoot = System.getProperty("user.dir") + "/lesson_8/src/main/resources/";
    private static final String formatOfCacheFile = ".ser";
    private static final String zipFormatOfCacheFile = ".zip";
    private final Object delegate;
    private final Map<List<Object>, Object> cacheInMemory = new HashMap();
    private final Map<List<Object>, Object> cacheInFile = new HashMap();
    private Cache annotation;

    public CacheProxy(Object delegate) throws IOException, ClassNotFoundException {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IOException, ClassNotFoundException {
        if(!method.isAnnotationPresent(Cache.class)) {
            return invoke(method, args);
        } else {
            annotation = method.getAnnotation(Cache.class);
            return cacheInvoke(method, args);
        }
    }

    private Object cacheInvoke(Method method, Object[] args) throws IOException, ClassNotFoundException {


        File cacheFile = getFile(method, formatOfCacheFile);
        loadCacheFromFileHandler(cacheFile);

        File zipFile = getFile(method, zipFormatOfCacheFile);
        loadCacheFromFileHandler(zipFile);

        List<Object> keys = Arrays.asList(method.getName(), List.of(args));
        if (resultInCache(keys) != null) {
            System.out.println(localTime() + "Результат метода " + keys + " взят из кэша");
            return resultInCache(keys);
        } else {
            Result result = new Result(method, args, invoke(method, args));
            loadResultInCache(method, result, cacheFile);
            return result.getResult();
        }
    }

    private File getFile(Method method, String formatOfFile) throws IOException, ClassNotFoundException {
        String filename = getFileName(method);
        File file = new File(cacheFileRoot + filename + formatOfFile);
        return file;
    }


    private void loadCacheFromFileHandler(File file) throws IOException, ClassNotFoundException {
        if (annotation.zip()) {
            if (file.exists()) {
                File temporaryCacheFile = new File (file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("."))
                       + "Temporary" + formatOfCacheFile);
                temporaryCacheFile.createNewFile();
                ZipUtil.unZip(file, temporaryCacheFile);
                loadCacheFromFile(temporaryCacheFile);
                temporaryCacheFile.delete();
            }
        } else {
            loadCacheFromFile(file);
        }
    }

    private void loadCacheFromFile(File file) throws IOException, ClassNotFoundException {
        if (file.exists()) {
            List<Result> initialCacheFromFile = SerializationUtil.deserialize(file);
            initialCacheFromFile.forEach(res -> cacheInFile.put(res.getKeys(), res.getResult()));
        }

    }

    private String getFileName(Method method) {
        String filename = annotation.fileNamePrefix();
        if (filename.equals("")) {
            filename = method.getName();
        }
        return filename;
    }

    private void loadResultInCache(Method method, Result result, File cacheFile) throws IOException, ClassNotFoundException {
        switch (annotation.cacheType()) {
            case IN_MEMORY: {
                cacheInMemory.put(result.getKeys(), result.getResult());
                System.out.println(localTime() + "Результат метода " + result.getKeys() + " записан в оперативную память");
                break;
            }
            case FILE: {
                cacheInFile.put(result.getKeys(), result.getResult());
                loadResultInCache(cacheFile, result);
                System.out.println(localTime() + "Результат метода " + result.getKeys() + " записан в файл " + method.getName());
                if (annotation.zip()) {
                    zipCacheFIle(method, cacheFile);
                    cacheFile.delete();
                }
                break;
            }
        }
    }

    private void zipCacheFIle(Method method, File cacheFile) throws IOException {
        System.out.println(localTime() + "Файл " + method.getName() + " заархивирован");
        File zipOfCacheFile = new File(cacheFileRoot + method.getName() + zipFormatOfCacheFile);
        ZipUtil.zip(cacheFile, zipOfCacheFile);
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
        serviceProxy.work("log").forEach(System.out::println);
//        serviceProxy.work("book").forEach(System.out::println);
//        serviceProxy.work("comic").forEach(System.out::println);
//        serviceProxy.work("coins").forEach(System.out::println);
//        serviceProxy.work("tickets").forEach(System.out::println);

//        List<Result> deserialize = SerializationUtil.deserialize(new File
//                ("/Users/mac/Documents/Git/javaTasks/lesson_8/src/main/resources/work.ser"));
//        deserialize.forEach(System.out::println);


//        File file = new File( "/Users/mac/Documents/Git/javaTasks/lesson_8/src/main/resources/work.ser");
        File zipFile = new File("/Users/mac/Documents/Git/javaTasks/lesson_8/src/main/resources/work.zip");
        File newFile = new File("/Users/mac/Documents/Git/javaTasks/lesson_8/src/main/resources/newWork.ser");
//
//
//        ZipUtil.zip(file, zipFile);
//        ZipUtil.unZip(zipFile, newFile);
//
//        List<Result> deserialize = SerializationUtil.deserialize(newFile);
//        deserialize.forEach(System.out::println);

    }
}

