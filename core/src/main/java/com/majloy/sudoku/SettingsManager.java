package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;

public class SettingsManager {
    private final GamePreferences prefs;

    public SettingsManager() {
        prefs = GamePreferences.getInstance();
    }

    public boolean isSoundEnabled() {
        return prefs.isSoundEnabled();
    }

    public void setSoundEnabled(boolean enabled) {
        prefs.saveSettings(enabled, getTheme(), getColorScheme());
    }

    public String getTheme() {
        return prefs.getTheme();
    }

    public void setTheme(String theme) {
        prefs.saveSettings(isSoundEnabled(), theme, getColorScheme());
    }

    public String getColorScheme() {
        return prefs.getColorScheme();
    }

    public void setColorScheme(String colorScheme) {
        prefs.saveSettings(isSoundEnabled(), getTheme(), colorScheme);
    }

    public void resetToDefaults() {
        prefs.saveSettings(true, "classic", "default");
    }
}
