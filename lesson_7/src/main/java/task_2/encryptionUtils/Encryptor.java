package task_2.encryptionUtils;

import javax.crypto.*;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    private final String ALGORITHM;
    private SecretKey secretKey;

    public Encryptor(String ALGORITHM){
        this.ALGORITHM = ALGORITHM;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(256);
            this.secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] cryptByteArray(byte[] data, int cipherMode) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(cipherMode, this.secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
