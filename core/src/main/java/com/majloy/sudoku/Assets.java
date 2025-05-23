package com.majloy.sudoku;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion whitePixel;

    public static void load() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixel = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
    }

    public static void dispose() {
        whitePixel.getTexture().dispose();
    }
}
