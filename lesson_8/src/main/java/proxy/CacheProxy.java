package proxy;

import annotations.Cache;
import serializationUtil.Result;
import serializationUtil.SerializationUtil;
import zipUtil.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheProxy implements InvocationHandler {

    private static final String cacheFileRoot = System.getProperty("user.dir") + "/lesson_8/src/main/resources/";
    private static final String formatOfCacheFile = ".ser";
    private static final String zipFormatOfCacheFile = ".zip";
    private final Object delegate;
    private final Map<List<Object>, Object> cacheInMemory = new HashMap();
    private final Map<List<Object>, Object> cacheInFile = new HashMap();
    private Cache annotation;
    File temporaryCacheFile;


    public CacheProxy(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        if(!method.isAnnotationPresent(Cache.class)) {
            return invoke(method, args);
        } else {
            annotation = method.getAnnotation(Cache.class);
            return cacheInvoke(method, args);
        }
    }

    private Object cacheInvoke(Method method, Object[] args) {

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

    private File getFile(Method method, String formatOfFile) {
        String filename = getFileName(method);
        File file = new File(cacheFileRoot + filename + formatOfFile);
        return file;
    }


    private void loadCacheFromFileHandler(File file) {
        if (annotation.zip()) {
            if (file.exists()) {
                try {
                    temporaryCacheFile = new File (file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("."))
                            + "Temporary" + formatOfCacheFile);
                    temporaryCacheFile.createNewFile();
                    ZipUtil.unZip(file, temporaryCacheFile);
                    loadCacheFromFile(temporaryCacheFile);
                } catch (IOException e) {
                    throw new RuntimeException("Ошибка при разархивации. Очистите кэш директорию:\n" +
                            cacheFileRoot);
                }
            }
        } else {
            loadCacheFromFile(file);
        }
    }


    private String getFileName(Method method) {
        String filename = annotation.fileNamePrefix();
        if (filename.equals("")) {
            filename = method.getName();
        }
        return filename;
    }

    private void loadResultInCache(Method method, Result result, File cacheFile)  {

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

    private void zipCacheFIle(Method method, File cacheFile) {
        System.out.println(localTime() + "Файл " + getFileName(method) + " заархивирован");
        File zipOfCacheFile = new File(cacheFileRoot + getFileName(method) + zipFormatOfCacheFile);
        try {
            ZipUtil.zip(cacheFile, zipOfCacheFile);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при архивации. Очистите кэш директорию:\n" +
                    cacheFileRoot);
        }
    }

    private String localTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss")) +"\t";
    }

    private void loadResultInCache(File cacheFile, Result result)  {
        loadCacheFromFile(cacheFile);
        try {
            SerializationUtil.serialize(result, cacheFile);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сериализации. Создайте пустую директорию:\n" +
                    cacheFileRoot);
        }
    }

    private void loadCacheFromFile(File cacheFile) {
        if (cacheFile.exists()) {
            try {
                List<Result> initialCacheFromFile  = SerializationUtil.deserialize(cacheFile);
                initialCacheFromFile.forEach(res -> cacheInFile.put(res.getKeys(), res.getResult()));
            } catch (IOException e) {
                throw new RuntimeException("Ошибка десериализации. Очистите кэш директорию:\n" +
                        cacheFileRoot);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Ошибка загрузки " + Result.class + ". Используйте системный Classloader");
            }
        }
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
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Базовый метод " + method + " вызвал исключение");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Impossible", e);
        }
    }
}