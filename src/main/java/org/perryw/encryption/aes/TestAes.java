package org.perryw.encryption.aes;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TestAes {
    public static String encrypt(String key, String initVector, String value){
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encode(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String key, String initVectro, String encrypted){
        try {
            IvParameterSpec iv = new IvParameterSpec(initVectro.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher=  Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] s) {
        String key = "Bar12345Bar12345";  // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV
        String original = "Hello AES!";

        String encrypted = encrypt(key, initVector, original);
        String decrypted = decrypt(key, initVector, encrypted);

        System.out.println("original: " + original);
        System.out.println("encrypted: " + encrypted);
        System.out.println("decrypted: " + decrypted);
    }
}
