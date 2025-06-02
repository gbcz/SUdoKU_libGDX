package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHelper {
    private static final String PREFS_NAME = "sudoku_data";
    private final Preferences prefs;
    private static final String SALT = "fixed_salt_value";

    public DatabaseHelper() {
        prefs = Gdx.app.getPreferences(PREFS_NAME);
    }

    public boolean registerUser(String username, String password) {
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            return false;
        }

        if (username.length() < 3 || password.length() < 6) {
            return false;
        }

        if (prefs.contains("user_" + username + "_password")) {
            return false;
        }

        String hashedPassword = hashPassword(password);
        prefs.putString("user_" + username + "_password", hashedPassword);
        prefs.putInteger("user_" + username + "_level", 1);
        prefs.putInteger("user_" + username + "_games", 0);
        prefs.flush();

        return true;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(SALT.getBytes());
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Gdx.app.error("Database", "Password hashing failed", e);
            throw new RuntimeException("Password hashing not available", e);
        }
    }

    public User loginUser(String username, String password) {
        if (username == null || password == null) return null;

        String storedPasswordHash = prefs.getString("user_" + username + "_password", null);
        if (storedPasswordHash == null) return null;

        if (storedPasswordHash.equals(hashPassword(password))) {
            return new User(
                username.hashCode(),
                username,
                prefs.getInteger("user_" + username + "_level", 1),
                prefs.getInteger("user_" + username + "_games", 0)
            );
        }

        return null;
    }

    public void updateUserStats(User user) {
        if (user == null) return;

        prefs.putInteger("user_" + user.getUsername() + "_level", user.getLevel());
        prefs.putInteger("user_" + user.getUsername() + "_games", user.getGamesPlayed());
        prefs.flush();
    }

    public void saveUserSettings(User user) {
        prefs.putString("user_" + user.getUsername() + "_theme", user.getTheme());
        prefs.putString("user_" + user.getUsername() + "_colors", user.getColorScheme());
        prefs.flush();
    }

    public void loadUserSettings(User user) {
        user.setTheme(prefs.getString("user_" + user.getUsername() + "_theme", "default"));
        user.setColorScheme(prefs.getString("user_" + user.getUsername() + "_colors", "classic"));
    }
}
