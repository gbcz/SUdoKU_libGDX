package com.majloy.sudoku;

import static com.majloy.sudoku.Assets.font;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SudokuGame extends Game {
    private SpriteBatch batch;
    public OrthographicCamera camera;
    public FitViewport viewport;
    public FontManager fontManager;
    public Skin skin;
    public DatabaseHelper dbHelper;
    public SettingsManager settingsManager;
    public SocialManager socialManager;
    public SavedGameState savedGame;
    public User currentUser;
    public AchievementSystem achievementSystem;
    private SudokuRenderer renderer;

    //Константы масштабирования
    public static final float WORLD_SCALE = 50f;
    public static final float WORLD_WIDTH = 13 * WORLD_SCALE;
    public static final float WORLD_HEIGHT = 13 * WORLD_SCALE;
    public static final float BOARD_OFFSET_X = 2f * WORLD_SCALE;
    public static final float BOARD_OFFSET_Y = 2f * WORLD_SCALE;

    @Override
    public void create() {
        Assets.load();
        batch = new SpriteBatch();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        renderer = new SudokuRenderer(skin, font);

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        renderer.render(Gdx.graphics.getDeltaTime());
        super.render();
    }

    @Override
    public void dispose() {
        Assets.dispose();
        batch.dispose();
        font.dispose();
        skin.dispose();
        renderer.dispose();
    }

    public SudokuRenderer getRenderer() {
        return renderer;
    }

    public void applySettings() {
    }
}
