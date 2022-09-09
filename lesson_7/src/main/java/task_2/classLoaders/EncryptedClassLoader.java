package task_2.classLoaders;

import task_2.encryptionUtils.Encryptor;
import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncryptedClassLoader extends ClassLoader {

    private final int KEY;
    private final File DIR;
    private final Encryptor ENCRYPTOR;


    public EncryptedClassLoader(int key, File dir, ClassLoader parent) {
        super(parent);
        this.KEY = key;
        this.DIR = dir;
        this.ENCRYPTOR = null;
    }

    public EncryptedClassLoader(Encryptor encryptor, File dir, ClassLoader parent) {
        super(parent);
        this.KEY = 0;
        this.DIR = dir;
        this.ENCRYPTOR = encryptor;
    }

    @Override
    public Class findClass(String pluginClassName) {
        byte[] data = readByteArray(pluginClassName);
        data = decrypt(data);
        return defineClass(pluginClassName, data, 0, data.length);
    }

    public byte[] readByteArray(String pluginClassName) {
        try {
            String filename = DIR.getAbsolutePath() + "/" + pluginClassName + ".class";
            return Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decrypt(byte[] data) {
        if (ENCRYPTOR == null) {
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (data[i] - KEY);
            }
            return data;
        } else{
            return ENCRYPTOR.cryptByteArray(data, Cipher.DECRYPT_MODE);
        }
    }
}



