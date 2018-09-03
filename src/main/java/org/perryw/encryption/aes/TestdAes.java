package org.perryw.encryption.aes;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class TestdAes {
    public static String encrypt(byte[] key, byte[] initVector, String value){
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(initVector);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encode(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(byte[] key, byte[] initVectro, String encrypted){
        try {
            IvParameterSpec iv = new IvParameterSpec(initVectro);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher=  Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] s) throws Exception {
        String secretToken = "KwSmbL30hGrB1u0GSmN+q7fYs3elVmEI60cZPWE+vlk=";
        String original = "YNlPRVbEZxDhG27AP8jqSw4ROoyQfQMKAAHR2K0jSY8=";
        String encrypted = "gLwVczc/HydNCS8GaKll0WRqvtAqNGtgMeQTsuV8+Lu/WaRckOkXriaPsJoEqeDW";

        byte[] key = Base64.decode(secretToken);
        String encryptResult = encrypt(
                key,
                Arrays.copyOfRange(key, 0, 16),
                original);
        String decryptResult = decrypt(
                key,
                Arrays.copyOfRange(key, 0, 16),
                encrypted);

        System.out.println("encryptResult: " + encryptResult);
        System.out.println("decryptResult: " + decryptResult);
    }
}
