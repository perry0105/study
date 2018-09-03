package org.perryw.encryption.aes;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class TestdSha {
    public static String encrypt(String origin){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(origin.getBytes(StandardCharsets.UTF_8));
            String result = Base64.encode(bytes);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static void main(String[] s) {
        String original = "a@nvidia.com:Test1111";
        String encrypted = encrypt(original);

        System.out.println("original: " + original);
        System.out.println("encrypted: " + encrypted);
    }
}
