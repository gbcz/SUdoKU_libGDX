package com.majloy.sudoku;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion whitePixel;
    public static BitmapFont font;
    public static TextureRegion[] numberTiles;
    public static Color LINE_COLOR = new Color(0.2f, 0.2f, 0.2f, 1);
    public static Color BLOCK_LINE_COLOR = new Color(0.1f, 0.1f, 0.1f, 1f);
    public static Color SELECTED_NUMBER_COLOR = new Color(0.9f, 0.9f, 0.2f, 0.7f);
    public static Color NUMBER_SELECTOR_BG = new Color(0.8f, 0.8f, 0.8f, 1f);
    public static Color NUMBER_PANEL_BORDER_COLOR = new Color(0.5f, 0.5f, 0.5f, 1f);

    public static void load() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixel = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();

        font = new BitmapFont();
        font.getData().setScale(1f);

        numberTiles = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            numberTiles[i] = createNumberTexture(i);
        }
    }

    private static TextureRegion createNumberTexture(int number) {
        return null;
    }

    public static void dispose() {
        whitePixel.getTexture().dispose();
        font.dispose();
        for (TextureRegion tile : numberTiles) {
            tile.getTexture().dispose();
        }
    }
}
