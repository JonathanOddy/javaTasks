package task_2.classLoaders;

import task_1.Plugin;
import task_1.PluginManager;
import task_2.encryptionUtils.EncryptionUtil;
import task_2.encryptionUtils.Encryptor;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Написать EncryptedClassloader
 */
public class  Main {
    public static void main(String[] args) throws IOException {
        PluginManager pluginManager = new PluginManager();

        int encryptionKey = 1;
        String[] pluginClassName = {"RainbowBrackets", "MavenHelper"};
        String pluginFolder = "encryptedPlugins";

        // шифрование pluginClassName.class путем увеличения каждого его байта на значение, равное encryptKey
        EncryptionUtil.encrypt(encryptionKey, pluginClassName[0]);

        Plugin rainbowBracketsPlugin = pluginManager.load(pluginFolder, pluginClassName[0], encryptionKey);
        System.out.println(pluginClassName[0]);
        rainbowBracketsPlugin.doUsefull();

        Encryptor encryptor = new Encryptor("AES");

        // шифрование pluginClassName.class при помощи алгоритма AES
        EncryptionUtil.encrypt(encryptor, pluginClassName[1]);
        Plugin wakaTimePlugin = pluginManager.load(pluginFolder, pluginClassName[1], encryptor);
        System.out.println(pluginClassName[1]);
        wakaTimePlugin.doUsefull();

    }
}
