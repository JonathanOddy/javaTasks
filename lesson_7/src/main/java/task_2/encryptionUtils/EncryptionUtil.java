package task_2.encryptionUtils;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncryptionUtil {

private static final String PLUGIN_ROOT = System.getProperty("user.dir") + "/lesson_7/pluginRootDirectory/";
private static final String PLUGIN_ORIGINAL_FOLDER = "originalPlugins/";
private static final String PLUGIN_ENCRYPTED_FOLDER = "encryptedPlugins/";

    public static void encrypt(int encryptionKey, String pluginClassName) {
        String originalFileName = PLUGIN_ROOT + PLUGIN_ORIGINAL_FOLDER + pluginClassName + ".class";
        String encryptedFileName = PLUGIN_ROOT + PLUGIN_ENCRYPTED_FOLDER + pluginClassName + ".class";
        byte[] data = readByteArrayFromFile(originalFileName);
        if (data != null) {
            encryptByteArray(data, encryptionKey);
            writeByteArrayToFile(encryptedFileName, data);
        }
    }

    public static void encrypt(Encryptor encryptor, String pluginClassName) {
        String originalFileName = PLUGIN_ROOT + PLUGIN_ORIGINAL_FOLDER + pluginClassName + ".class";
        String encryptedFileName = PLUGIN_ROOT + PLUGIN_ENCRYPTED_FOLDER + pluginClassName + ".class";
        byte[] data = readByteArrayFromFile(originalFileName);
        if (data != null) {
            data = encryptor.cryptByteArray(data, Cipher.ENCRYPT_MODE);
            writeByteArrayToFile(encryptedFileName, data);
        }
    }


    private static byte[] readByteArrayFromFile(String originalFileName) {
        try {
            return Files.readAllBytes(Paths.get(originalFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void encryptByteArray(byte[] data, int encryptionKey) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (data[i] + encryptionKey);
        }
    }

    private static void writeByteArrayToFile(String encryptedFileName, byte[] data) {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(encryptedFileName));
            bufferedOutputStream.write(data);
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
