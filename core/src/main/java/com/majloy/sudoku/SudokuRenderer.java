package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class SudokuRenderer implements Disposable {
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Skin skin;
    private Stage currentStage;
    private Screen currentScreen;

    public SudokuRenderer(Skin skin, BitmapFont font) {
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.font = font;
        this.skin = skin;
    }

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    public void setCurrentScreen(Screen screen) {
        this.currentScreen = screen;
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (currentScreen != null) {
            if (currentScreen instanceof GameScreen) {
                renderGameScreen((GameScreen) currentScreen, delta);
            } else if (currentScreen instanceof MainMenuScreen) {
                renderMainMenuScreen((MainMenuScreen) currentScreen, delta);
            } else if (currentScreen instanceof DifficultyScreen) {
                renderDifficultyScreen((DifficultyScreen) currentScreen, delta);
            } else if (currentScreen instanceof DifficultyLevelScreen) {
                renderDifficultyLevelScreen((DifficultyLevelScreen) currentScreen, delta);
            } else if (currentScreen instanceof ProfileScreen) {
                renderProfileScreen((ProfileScreen) currentScreen, delta);
            } else if (currentScreen instanceof RegisterScreen) {
                renderRegistrationScreen((RegisterScreen) currentScreen, delta);
            } else if (currentScreen instanceof LoginScreen) {
                renderLoginScreen((LoginScreen) currentScreen, delta);
            } else if (currentScreen instanceof StatisticsScreen) {
                renderStatisticsScreen((StatisticsScreen) currentScreen, delta);
            } else if (currentScreen instanceof SettingScreen) {
                renderSettingsScreen((SettingScreen) currentScreen, delta);
            }
        }

        if (currentStage != null) {
            currentStage.act(delta);
            currentStage.draw();
        }
    }

    private void renderGameScreen(GameScreen screen, float delta) {
        renderBoard(screen.getBoard());

        batch.begin();
        font.setColor(ThemeManager.getTextColor());

        if (screen.isMultiplayer()) {
            font.setColor(ThemeManager.getMultiplayerColor());
            font.draw(batch, "Opponent: " + screen.getOpponentName(),
                Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 20);
            font.draw(batch, "Progress: " + screen.getOpponentProgress() + "%",
                Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 50);
        }
        batch.end();
    }

    private void renderMainMenuScreen(MainMenuScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SUDOKU", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        if (screen.getCurrentUser() != null) {
            font.setColor(ThemeManager.getTextColor());
            font.draw(batch, "Welcome, " + screen.getCurrentUser().getUsername(),
                20, Gdx.graphics.getHeight() - 50);
            font.draw(batch, "Level: " + screen.getCurrentUser().getLevel(),
                20, Gdx.graphics.getHeight() - 80);
        }
        batch.end();
    }

    private void renderDifficultyScreen(DifficultyScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SUDOKU", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderDifficultyLevelScreen(DifficultyLevelScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SUDOKU", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderProfileScreen(ProfileScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SUDOKU", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderRegistrationScreen(RegisterScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SUDOKU", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderLoginScreen(LoginScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SUDOKU", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderStatisticsScreen(StatisticsScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SUDOKU", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderSettingsScreen(SettingScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SUDOKU", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderBoard(SudokuBoard board) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(ThemeManager.getBackgroundColor());
        shapeRenderer.rect(board.getX(), board.getY(),
            board.getSize(), board.getSize());

        float cellSize = board.getCellSize();
        for (int row = 0; row < board.getGridSize(); row++) {
            for (int col = 0; col < board.getGridSize(); col++) {
                renderCell(board, row, col, cellSize);
            }
        }

        renderGridLines(board);

        shapeRenderer.end();
    }

    private void renderCell(SudokuBoard board, int row, int col, float cellSize) {
        float x = board.getX() + col * cellSize;
        float y = board.getY() + row * cellSize;

        if (board.isSelected(row, col)) {
            shapeRenderer.setColor(ThemeManager.getSelectionColor());
            shapeRenderer.rect(x, y, cellSize, cellSize);
        }

        int value = board.getValue(row, col);
        if (value != 0) {
            batch.begin();
            font.setColor(ThemeManager.getNumberColor());
            font.draw(batch, String.valueOf(value),
                x + cellSize/2 - 5, y + cellSize/2 + 5);
            batch.end();
        }
    }

    private void renderGridLines(SudokuBoard board) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(ThemeManager.getLineColor());

        float size = board.getSize();
        float cellSize = board.getCellSize();
        float x = board.getX();
        float y = board.getY();

        //Тонкие линии
        for (int i = 0; i <= board.getGridSize(); i++) {
            //Горизонтальные
            shapeRenderer.line(x, y + i * cellSize,
                x + size, y + i * cellSize);
            //Вертикальные
            shapeRenderer.line(x + i * cellSize, y,
                x + i * cellSize, y + size);
        }

        //Толстые линии блоков
        shapeRenderer.setColor(ThemeManager.getBlockLineColor());
        float blockSize = board.getBlockSize() * cellSize;
        for (int i = 0; i <= board.getGridSize() / board.getBlockSize(); i++) {
            //Горизонтальные
            shapeRenderer.line(x, y + i * blockSize,
                x + size, y + i * blockSize);
            //Вертикальные
            shapeRenderer.line(x + i * blockSize, y,
                x + i * blockSize, y + size);
        }

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }
}
