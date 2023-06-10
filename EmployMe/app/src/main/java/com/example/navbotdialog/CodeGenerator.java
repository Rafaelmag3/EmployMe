package com.example.navbotdialog;

import java.security.SecureRandom;
import android.util.Base64;

public class CodeGenerator {
    private static final int CODE_LENGTH = 8;

    public static String generateCode() {
        byte[] randomBytes = new byte[CODE_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return Base64.encodeToString(randomBytes, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
    }
}
