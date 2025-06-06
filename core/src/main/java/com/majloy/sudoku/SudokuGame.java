package com.majloy.sudoku;

import static com.majloy.sudoku.Assets.font;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SudokuGame extends Game {
    private SpriteBatch batch;
    public OrthographicCamera camera;
    public Skin skin;
    public DatabaseHelper dbHelper;
    public SavedGameState savedGame;
    public User currentUser;
    public AchievementSystem achievementSystem;
    private SudokuRenderer renderer;
    public static final float WORLD_SCALE = 50f;

    @Override
    public void create() {
        Assets.load();
        batch = new SpriteBatch();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        renderer = new SudokuRenderer(skin, font);
        dbHelper = new DatabaseHelper();

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
    public static class SavedGameState {
        public int gridSize;
        public int cellsToRemove;
        public int[][] grid;

        public SavedGameState(int gridSize, int cellsToRemove, int[][] grid) {
            this.gridSize = gridSize;
            this.cellsToRemove = cellsToRemove;
            this.grid = grid;
        }
    }

    public void applySettings() {
    }
}
