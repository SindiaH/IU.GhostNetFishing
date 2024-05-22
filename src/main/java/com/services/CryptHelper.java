package com.services;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptHelper {

    private static final String salt = "8i@ZqPVJtzuJxKN%c&HSFhpZCJ8eh5rDmwzQ%zf2X7phjqQV@m!AQ3X**oejSi57vrbWkLyt$idTSuE6!Hi$5^3r9G4L!C2ez29G8dw*rJeSwoE^76vJ%WtqYG!E9hdK";


    public static String encrypt(String value) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, CryptHelper.getSecretKey());
            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
            return null; 
        }
    }

    public static String decrypt(String encryptedValue) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, CryptHelper.getSecretKey());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(decryptedBytes);
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e);
            return null; 
        }
    }
    
    private static SecretKey getSecretKey() {
        byte[] keyBytes = salt.getBytes();
        byte[] shortenedKeyBytes = new byte[16];
        System.arraycopy(keyBytes, 0, shortenedKeyBytes, 0, 16);
        return new SecretKeySpec(shortenedKeyBytes, "AES");
    }
}
