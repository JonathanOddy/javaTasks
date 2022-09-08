package task_1;

import java.net.MalformedURLException;

/**
 * Ваша задача написать загрузчик плагинов в вашу систему. Допустим вы пишите свой браузер и
 * хотите, чтобы люди имели имели возможность писать плагины для него. Соответственно,
 * разные разработчики могут назвать свои классы одинаковым именем, ваш загрузчик должен корректно это обрабатывать.
*/
public class Main {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {

        PluginManager pluginManager = new PluginManager();
        String[] pluginFolders = new String[] {"superPlugins", "coolPlugins"};
        String[] pluginClassNames = new String[] {"JumpToLinePlugin", "MavenHelper", "RainbowBrackets", "WakaTime"};

        for (String pluginClassName : pluginClassNames) {
            System.out.println(pluginClassName);
            for (String pluginFolder : pluginFolders) {
                Plugin jumpToLinePlugin = pluginManager.load(pluginFolder, pluginClassName);
                if (jumpToLinePlugin != null) jumpToLinePlugin.doUsefull();
            }
        }
    }
}

