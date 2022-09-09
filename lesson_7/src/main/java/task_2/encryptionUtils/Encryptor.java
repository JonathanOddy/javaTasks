package task_2.encryptionUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    private final String ALGORITHM;
    private SecretKeySpec secretKey;

    public Encryptor(String ALGORITHM) {
        this.ALGORITHM = ALGORITHM;
        //            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
//            keyGenerator.init(keyBitSize);
//            this.secretKey = keyGenerator.generateKey();

        byte[] keys = {-77, 50, -115, 63, 109, -13, 126, -25, 101, 34, -19, 56, 61, -11, -75, 103};
        this.secretKey  = new SecretKeySpec(keys, 0, keys.length, "AES");

    }

    public byte[] cryptByteArray(byte[] data, int cipherMode) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(cipherMode, this.secretKey);
            return cipher.doFinal(data);
            // must add padding before doFinal because block size is 16 bytes
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
