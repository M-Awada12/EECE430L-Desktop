package com.awada.exchange;

import java.util.prefs.Preferences;

public class Authentication {
    private static final String TOKEN_KEY = "TOKEN";
    private static Authentication instance;
    private final Preferences pref;
    private String token;

    private Authentication() {
        pref = Preferences.userRoot().node(getClass().getName());
        token = pref.get(TOKEN_KEY, null);
    }

    public static synchronized Authentication getInstance() {
        if (instance == null) {
            instance = new Authentication();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void saveToken(String token) {
        this.token = token;
        pref.put(TOKEN_KEY, token);
    }

    public void deleteToken() {
        this.token = null;
        pref.remove(TOKEN_KEY);
    }
}
