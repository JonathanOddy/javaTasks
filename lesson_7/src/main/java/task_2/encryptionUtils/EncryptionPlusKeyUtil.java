package task_2.encryptionUtils;

import java.io.*;

public class EncryptionPlusKeyUtil {

private static final String pluginRoot = System.getProperty("user.dir") + "/lesson_7/pluginRootDirectory/";
private static final String pluginOriginalFolder = "originalPlugins/";
private static final String pluginEncryptedFolder = "encryptedPlugins/";
private static int encryptionKey = 0;

    public static void encrypt(int key, String pluginClassName) {
        String originalFileName = pluginRoot + pluginOriginalFolder + pluginClassName + ".class";
        String encryptedFileName = pluginRoot + pluginEncryptedFolder + pluginClassName + ".class";
        byte[] readData = readByteArrayFromFile(originalFileName);
        encryptionKey = key;
        if (readData != null) {
            writeByteArrayToFile(encryptedFileName, encryptByteArray(readData));
        }
    }

    private static byte[] readByteArrayFromFile(String originalFileName) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(originalFileName));
            byte[] data = new byte[bufferedInputStream.available()];
            int resultOfRead = bufferedInputStream.read(data);
            bufferedInputStream.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encryptByteArray( byte[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (data[i] + encryptionKey);
        }
        return data;
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
