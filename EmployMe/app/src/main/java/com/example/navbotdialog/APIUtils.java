package com.example.navbotdialog;

public class APIUtils {
    private static final String BASE_URL = "http://192.168.43.249:3000/";

    public static String getFullUrl(String route) {
        return BASE_URL + route;
    }
}
