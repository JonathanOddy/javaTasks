package task_1;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Ваша задача написать загрузчик плагинов в вашу систему. Допустим вы пишите свой браузер и
 * хотите, чтобы люди имели имели возможность писать плагины для него. Соответственно,
 * разные разработчики могут назвать свои классы одинаковым именем, ваш загрузчик должен корректно это обрабатывать.
 * <p>
 * Усложненная версия задания. Система должна вести себя корректно, если в плагине
 * есть скомпилированные классы с именем, которые есть в вашем браузере(не в плагинах),
 * должны использоваться классы плагина, а не вашего браузера. Для этого придется поменять
 * модель делегирования класслоадера в методе loadClass
*/
public class Main {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {

        PluginManager pluginManager = new PluginManager();
        String[] pluginFolders = new String[] {"superPlugins", "coolPlugins"};

        for (String pluginFolder : pluginFolders) {
            Plugin jumpToLinePlugin = pluginManager.load(pluginFolder, "JumpToLinePlugin");
            jumpToLinePlugin.doUsefull();
        }

    }
}