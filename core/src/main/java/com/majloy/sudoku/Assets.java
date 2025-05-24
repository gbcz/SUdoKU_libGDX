package com.majloy.sudoku;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion whitePixel;
    public static BitmapFont font;
    public static Color LINE_COLOR = new Color(0.2f, 0.2f, 0.2f, 1);
    public static Color BLOCK_LINE_COLOR = new Color(0.1f, 0.1f, 0.1f, 1f);

    public static void load() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixel = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();

        font = new BitmapFont();
        font.getData().setScale(1f);
    }

    public static void dispose() {
        whitePixel.getTexture().dispose();
        font.dispose();
    }
}
