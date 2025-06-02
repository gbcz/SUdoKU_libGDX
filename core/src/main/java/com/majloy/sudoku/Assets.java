package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion whitePixel;
    public static BitmapFont font;
    public static TextureRegion[] numberTiles;
    public static Texture backgroundTexture;

    public static Color LINE_COLOR = new Color(0.2f, 0.2f, 0.2f, 1);
    public static Color BLOCK_LINE_COLOR = new Color(0.1f, 0.1f, 0.1f, 1f);
    public static Color SELECTED_NUMBER_COLOR = new Color(0.9f, 0.9f, 0.2f, 0.7f);

    public static void load() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixel = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();

        font = new BitmapFont();
        font.getData().setScale(1f);

        backgroundTexture = new Texture(Gdx.files.internal("images/background.png"));

        numberTiles = new TextureRegion[10];

        for (int i = 0; i < 10; i++) {
            numberTiles[i] = createNumberTexture(i);
        }
    }

    private static TextureRegion createNumberTexture(int number) {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        //Здесь должна быть логика рисования цифры
        //Временно просто возвращаем белую текстуру
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(texture);
    }

    public static void dispose() {
        if (whitePixel != null && whitePixel.getTexture() != null) {
            whitePixel.getTexture().dispose();
        }
        if (font != null) {
            font.dispose();
        }
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        if (numberTiles != null) {
            for (TextureRegion tile : numberTiles) {
                if (tile != null && tile.getTexture() != null) {
                    tile.getTexture().dispose();
                }
            }
        }
    }
}
