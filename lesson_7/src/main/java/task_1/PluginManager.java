package task_1;

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

    private final String pluginRoot = System.getProperty("user.dir") + "/lesson_7/pluginRootDirectory/";
    private final List<URL> URLs = new ArrayList<>();
    private final List<Plugin> plugins = new ArrayList<>();

    public Plugin load(String pluginFolder, String pluginClassName) {
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{
                    new File(pluginRoot + "/" + pluginFolder).toURI().toURL()
            });
            Class<?> clazz = urlClassLoader.loadClass(pluginClassName);
            return (Plugin) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException | ClassNotFoundException | MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

