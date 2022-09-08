package task_2.classLoaders;

import task_1.Plugin;
import task_2.encryptionUtils.EncryptionPlusKeyUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
//
/**
 * Написать EncryptedClassloader
 */
public class  Main {
    public static void main(String[] args) throws IOException {

        int encryptionKey = 1;
        String pluginClassName = "RainbowBrackets";
        // шифрование pluginClassName.class путем увеличения каждого его байта на значение, равное encryptKey
        EncryptionPlusKeyUtil.encrypt(encryptionKey, pluginClassName);

        File encryptedDir = new File(System.getProperty("user.dir") + "/lesson_7/pluginRootDirectory/encryptedPlugins");
        EncryptedPlusKeyClassLoader encryptedPlusKeyClassLoader =
                new EncryptedPlusKeyClassLoader(encryptionKey, encryptedDir, ClassLoader.getSystemClassLoader());
        try {
            // дешифрование и загрузка pluginClassName.class путем уменьшения каждого его байта на значение, равное encryptKey
            Class<?> clazz = encryptedPlusKeyClassLoader.findClass(pluginClassName);
            Plugin plugin = (Plugin) clazz.getConstructor().newInstance();
            System.out.println(pluginClassName);
            plugin.doUsefull();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
