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

    //Константы масштабирования
    public static final float WORLD_SCALE = 50f;
    public static final float WORLD_WIDTH = 13 * WORLD_SCALE;
    public static final float WORLD_HEIGHT = 13 * WORLD_SCALE;
    public static final float BOARD_OFFSET_X = 2f * WORLD_SCALE;
    public static final float BOARD_OFFSET_Y = 2f * WORLD_SCALE;

    @Override
    public void create() {
        Assets.load();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);

        fontManager = new FontManager();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        dbHelper = new DatabaseHelper();
        settingsManager = new SettingsManager();

        loadSavedGame();
        loadUser();

        setScreen(new MainMenuScreen(this));
    }

    public void applySettings() {
        // Применяем настройки (звук, тему и т.д.)
        boolean soundEnabled = settingsManager.isSoundEnabled();
        String theme = settingsManager.getTheme();
        // ... применение настроек ...
    }

    private void loadSavedGame() {
        // Загрузка сохраненной игры из Preferences
        Preferences prefs = Gdx.app.getPreferences("SudokuSave");
        if (prefs.contains("saved")) {
            // ... загрузка состояния ...
        }
    }

    private void loadUser() {
        // Попытка загрузить последнего пользователя
        Preferences prefs = Gdx.app.getPreferences("SudokuUser");
        String username = prefs.getString("lastUser", null);
        if (username != null) {
            currentUser = dbHelper.loginUser(username, prefs.getString("lastUserPass", ""));
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
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
        fontManager.dispose();
        skin.dispose();
    }
}
