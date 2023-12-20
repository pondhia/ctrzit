package com.github.pondhia.ctrzit.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 Utils
 */
public class SHA256Utils {


    private static final ThreadLocal<MessageDigest> DIGESTER_FACTORY =
            ThreadLocal.withInitial(() -> {
                try {
                    return MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            });

    /**
     * Create a thread local SHA256 digester.
     */

    public static MessageDigest getDigester() {
        MessageDigest digester = DIGESTER_FACTORY.get();
        digester.reset();
        return digester;
    }

    public static String toString(byte[] hash) {
        try {
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}