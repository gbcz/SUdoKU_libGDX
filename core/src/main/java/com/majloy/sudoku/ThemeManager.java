package com.majloy.sudoku;

import com.badlogic.gdx.graphics.Color;

import java.util.Map;

public class ThemeManager {
    private static final Map<String, Color[]> THEMES = Map.of(
        "classic", new Color[]{Color.WHITE, Color.BLACK, new Color(0.8f, 0.8f, 0.8f, 1)},
        "dark", new Color[]{new Color(0.1f, 0.1f, 0.1f, 1), Color.WHITE, new Color(0.3f, 0.3f, 0.3f, 1)}
    );

    public static Color[] getThemeColors(String themeName) {
        return THEMES.getOrDefault(themeName, THEMES.get("classic"));
    }
}
