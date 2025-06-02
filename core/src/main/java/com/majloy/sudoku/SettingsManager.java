package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsManager {
    private final Preferences prefs;

    public SettingsManager() {
        prefs = Gdx.app.getPreferences("sudoku_settings");
    }

    public boolean isSoundEnabled() {
        return prefs.getBoolean("sound_enabled", true);
    }

    public void setSoundEnabled(boolean enabled) {
        prefs.putBoolean("sound_enabled", enabled);
        prefs.flush();
    }

    public String getTheme() {
        return prefs.getString("theme", "classic");
    }

    public void setTheme(String theme) {
        prefs.putString("theme", theme);
        prefs.flush();
    }

    public void resetToDefaults() {
        prefs.clear();
        prefs.flush();
    }
}
