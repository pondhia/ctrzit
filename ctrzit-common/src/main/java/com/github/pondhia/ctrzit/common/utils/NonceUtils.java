package com.github.pondhia.ctrzit.common.utils;

import java.security.SecureRandom;

/**
 * Nonce Utils
 */
public class NonceUtils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

    /**
     * Generate nonce
     *
     * @param length length
     * @return Nonce
     */
    public static String generateNonce(int length) {
        StringBuilder nonce = new StringBuilder(length);
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomCharIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomCharIndex);
            nonce.append(randomChar);
        }
        return nonce.toString();
    }
}
