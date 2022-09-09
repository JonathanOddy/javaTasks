package task_1;

import task_2.classLoaders.EncryptedClassLoader;
import task_2.encryptionUtils.Encryptor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * PluginManager ищет скомпилированные классы плагина в папке pluginRootDirectory/pluginFolder/
 */
public class PluginManager {

    private final String PLUGIN_ROOT = System.getProperty("user.dir") + "/lesson_7/pluginRootDirectory/";

    public Plugin load(String pluginFolder, String pluginClassName) {
        try {
            ClassLoader urlClassLoader = new URLClassLoader(new URL[]{
                    new File(PLUGIN_ROOT + "/" + pluginFolder).toURI().toURL()
            });
            Class<?> clazz = urlClassLoader.loadClass(pluginClassName);
            return (Plugin) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException | MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println(pluginClassName + ".class is not found in " + pluginFolder);
            return null;
        }
    }

    public Plugin load(String pluginFolder, String pluginClassName, int encryptionKey) {
            File encryptedDir = new File(System.getProperty("user.dir") + "/lesson_7/pluginRootDirectory/" + pluginFolder);
            ClassLoader classLoader = new EncryptedClassLoader(
                    encryptionKey, encryptedDir, ClassLoader.getSystemClassLoader()
            );
            return loadPlugin(classLoader, pluginClassName);

    }

    public Plugin load(String pluginFolder, String pluginClassName, Encryptor encryptor) {
            File encryptedDir = new File(System.getProperty("user.dir") + "/lesson_7/pluginRootDirectory/" + pluginFolder);
            ClassLoader classLoader = new EncryptedClassLoader(
                    encryptor, encryptedDir, ClassLoader.getSystemClassLoader()
            );
            return loadPlugin(classLoader, pluginClassName);
    }

    private Plugin loadPlugin(ClassLoader classLoader, String pluginClassName) {
        try {
            Class<?> clazz = classLoader.loadClass(pluginClassName);
            return (Plugin) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

