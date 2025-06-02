package com.majloy.sudoku;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SudokuGame extends Game {
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
        try {
            Assets.load();
            camera = new OrthographicCamera();
            viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
            camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);

            fontManager = new FontManager();

            skin = new Skin(Gdx.files.internal("uiskin.json"));
            dbHelper = new DatabaseHelper();
            settingsManager = new SettingsManager();
            socialManager = new SocialManager(dbHelper);
            achievementSystem = new AchievementSystem();

            loadSavedGame();
            loadUser();

            renderer = new SudokuRenderer(this);

            setScreen(new MainMenuScreen(this));
        } catch (Exception e) {
            Gdx.app.error("SudokuGame", "Initialization failed", e);
            throw new RuntimeException("Game initialization failed", e);
        }
    }

    public void applySettings() {
        boolean soundEnabled = settingsManager.isSoundEnabled();
        String theme = settingsManager.getTheme();
    }

    private void loadSavedGame() {
        Preferences prefs = Gdx.app.getPreferences("SudokuSave");
        if (prefs.getBoolean("has_save", false)) {
            int gridSize = prefs.getInteger("grid_size", 9);
            int cellsToRemove = prefs.getInteger("cells_removed", 40);

            int[][] grid = new int[gridSize][gridSize];

            savedGame = new SavedGameState(gridSize, cellsToRemove, grid);
        }
    }

    private void loadUser() {
        Preferences prefs = Gdx.app.getPreferences("SudokuUser");
        String username = prefs.getString("lastUser", null);
        if (username != null) {
            currentUser = dbHelper.loginUser(username, prefs.getString("lastUserPass", ""));
        } else {
            currentUser = new User(-1, "Guest", 1, 0);
        }
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

    @Override
    public void render() {
        renderer.render(Gdx.graphics.getDeltaTime());
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        super.dispose();
        try {
            if (Assets.font != null) {
                Assets.dispose();
            }
            if (fontManager != null) {
                fontManager.dispose();
            }
            if (skin != null) {
                skin.dispose();
            }
        } catch (Exception e) {
            Gdx.app.error("SudokuGame", "Dispose failed", e);
        }
    }
}
