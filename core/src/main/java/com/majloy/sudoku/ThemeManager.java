package com.majloy.sudoku;

import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class ThemeManager {
    private static String currentTheme = "classic";

    private static final Map<String, Theme> themes = new HashMap<>();

    static {
        // Классическая тема
        themes.put("classic", new Theme(
            new Color(1, 1, 1, 1),    // background
            new Color(0.2f, 0.2f, 0.2f, 1),   // lines
            new Color(0.1f, 0.1f, 0.1f, 1),   // block lines
            new Color(0, 0, 0, 1),    // numbers
            new Color(0.9f, 0.9f, 0.2f, 0.7f), // selection
            new Color(0.8f, 0.8f, 0.8f, 1),   // panel
            new Color(0.2f, 0.4f, 0.8f, 1),   // multiplayer
            new Color(0, 0, 0, 1),    // text
            new Color(0.8f, 0.5f, 0.1f, 1)    // title
        ));

        // Темная тема
        themes.put("dark", new Theme(
            new Color(0.1f, 0.1f, 0.1f, 1),
            new Color(0.4f, 0.4f, 0.4f, 1),
            new Color(0.6f, 0.6f, 0.6f, 1),
            new Color(0.9f, 0.9f, 0.9f, 1),
            new Color(0.2f, 0.6f, 0.9f, 0.7f),
            new Color(0.2f, 0.2f, 0.2f, 1),
            new Color(0.4f, 0.8f, 1, 1),
            new Color(0.9f, 0.9f, 0.9f, 1),
            new Color(0.9f, 0.7f, 0.3f, 1)
        ));
    }

    public static void setTheme(String themeName) {
        if (themes.containsKey(themeName)) {
            currentTheme = themeName;
        }
    }

    public static Color getBackgroundColor() {
        return themes.get(currentTheme).backgroundColor;
    }

    public static Color getLineColor() {
        return themes.get(currentTheme).lineColor;
    }

    public static Color getBlockLineColor() {
        return themes.get(currentTheme).blockLineColor;
    }

    public static Color getNumberColor() {
        return themes.get(currentTheme).numberColor;
    }

    public static Color getSelectionColor() {
        return themes.get(currentTheme).selectionColor;
    }

    public static Color getPanelColor() {
        return themes.get(currentTheme).panelColor;
    }

    public static Color getMultiplayerColor() {
        return themes.get(currentTheme).multiplayerColor;
    }

    public static Color getTextColor() {
        return themes.get(currentTheme).textColor;
    }

    public static Color getTitleColor() {
        return themes.get(currentTheme).titleColor;
    }

    private static class Theme {
        final Color backgroundColor;
        final Color lineColor;
        final Color blockLineColor;
        final Color numberColor;
        final Color selectionColor;
        final Color panelColor;
        final Color multiplayerColor;
        final Color textColor;
        final Color titleColor;

        Theme(Color bg, Color line, Color block, Color num,
              Color sel, Color panel, Color multi, Color text, Color title) {
            backgroundColor = bg;
            lineColor = line;
            blockLineColor = block;
            numberColor = num;
            selectionColor = sel;
            panelColor = panel;
            multiplayerColor = multi;
            textColor = text;
            titleColor = title;
        }
    }
}
