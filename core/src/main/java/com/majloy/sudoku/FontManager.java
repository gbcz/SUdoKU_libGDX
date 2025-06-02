package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

public class FontManager implements Disposable {
    private BitmapFont titleFont;
    private BitmapFont mediumFont;
    private BitmapFont regularFont;
    private BitmapFont smallFont;

    public FontManager() {
        loadFonts();
    }

    private void loadFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/Roboto-Regular.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();

        param.size = 72;
        param.color = Color.WHITE;
        titleFont = generator.generateFont(param);

        param.size = 36;
        mediumFont = generator.generateFont(param);

        param.size = 24;
        regularFont = generator.generateFont(param);

        param.size = 18;
        smallFont = generator.generateFont(param);

        generator.dispose();
    }

    public BitmapFont getTitleFont() { return titleFont; }
    public BitmapFont getMediumFont() { return mediumFont; }
    public BitmapFont getRegularFont() { return regularFont; }
    public BitmapFont getSmallFont() { return smallFont; }

    @Override
    public void dispose() {
        titleFont.dispose();
        mediumFont.dispose();
        regularFont.dispose();
        smallFont.dispose();
    }
}
