package com.majloy.sudoku;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion whitePixel;
    public static BitmapFont font;

    public static void load() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        whitePixel = new TextureRegion(texture);
        pixmap.dispose();

        font = new BitmapFont();
        font.getData().setScale(1f);
        font.setUseIntegerPositions(false);
    }

    public static void dispose() {
        whitePixel.getTexture().dispose();
        font.dispose();
    }
}
