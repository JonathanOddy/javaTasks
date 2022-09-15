package proxy;

import annotations.Cache;
import serializationService.Result;
import serializationService.SerializationUtil;
import service.*;
import service.ServiceImpl;
import zipUtil.ZipUtil;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CacheProxy implements InvocationHandler {

    private static final String cacheFileRoot = System.getProperty("user.dir") + "/lesson_8/src/main/resources/";
    private static final String formatOfCacheFile = ".ser";
    private static final String zipFormatOfCacheFile = ".zip";
    private final Object delegate;
    private final Map<List<Object>, Object> cacheInMemory = new HashMap();
    private final Map<List<Object>, Object> cacheInFile = new HashMap();
    private Cache annotation;
    File temporaryCacheFile;


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

        if (temporaryCacheFile != null) {
            cacheFile = temporaryCacheFile;
        }

        List<Object> keys = getKeys(method, args);
        if (resultInCache(keys) != null) {
            System.out.println(localTime() + "Результат метода " + keys + " взят из кэша");
            temporaryCacheFile.delete();    // самая гениальная строчка в коде
            return resultInCache(keys);
        } else {
            Result result = new Result(keys, invoke(method, args));
            loadResultInCache(method, result, cacheFile);
            return result.getResult();
        }
    }

    private List<Object> getKeys(Method method, Object[] args) {

        List<Object> keys = new ArrayList<>();
        keys.add(method.getName());
        for (Object arg: args) {
            for (Class clazz: annotation.identityBy()) {
                if (clazz.isInstance(arg)) {
                    keys.add(arg);
                }
            }
        }
        return keys;
    }

    private File getFile(Method method, String formatOfFile) throws IOException, ClassNotFoundException {
        String filename = getFileName(method);
        File file = new File(cacheFileRoot + filename + formatOfFile);
        return file;
    }


    private void loadCacheFromFileHandler(File file) throws IOException, ClassNotFoundException {
        if (annotation.zip()) {
            if (file.exists()) {
                temporaryCacheFile = new File (file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("."))
                       + "Temporary" + formatOfCacheFile);
                temporaryCacheFile.createNewFile();
                ZipUtil.unZip(file, temporaryCacheFile);
                loadCacheFromFile(temporaryCacheFile);
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
                if (cacheInMemory.size() < annotation.memoryCacheSize()) {
                    cacheInMemory.put(result.getKeys(), result.getResult());
                    System.out.println(localTime() + "Результат метода " + result.getKeys() + " записан в оперативную память");
                } else {
                    System.out.println(localTime() + "Кэш в оперативной памяти переполнен");
                }
                break;
            }
            case FILE: {
                cacheInFile.put(result.getKeys(), result.getResult());
                loadResultInCache(cacheFile, result);
                System.out.println(localTime() + "Результат метода " + result.getKeys() + " записан в файл " + getFileName(method));
                if (annotation.zip()) {
                    zipCacheFIle(method, cacheFile);
                    cacheFile.delete();

                }
                break;
            }
        }
    }

    private void zipCacheFIle(Method method, File cacheFile) throws IOException {
        System.out.println(localTime() + "Файл " + getFileName(method) + " заархивирован");
        File zipOfCacheFile = new File(cacheFileRoot + getFileName(method) + zipFormatOfCacheFile);
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

    private void closeResources() {
        temporaryCacheFile.delete();
    }
}

