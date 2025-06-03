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
        font.draw(batch, "SSSSSudoku", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
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
        font.draw(batch, "SSSSSudoku", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderDifficultyLevelScreen(DifficultyLevelScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SSSSSudoku", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderProfileScreen(ProfileScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SSSSSudoku", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderRegistrationScreen(RegisterScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SSSSSudoku", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderLoginScreen(LoginScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SSSSSudoku", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderStatisticsScreen(StatisticsScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SSSSSudoku", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderSettingsScreen(SettingScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SSSSSudoku", (float) Gdx.graphics.getWidth() /2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        batch.end();
    }

    private void renderBoard(SudokuBoard board) {
        board.updatePosition();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(ThemeManager.getBackgroundColor());
        shapeRenderer.rect(board.getX(), board.getY(), board.getSize(), board.getSize());
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float cellSize = board.getCellSize();
        for (int row = 0; row < board.getGridSize(); row++) {
            for (int col = 0; col < board.getGridSize(); col++) {
                if (board.isSelected(row, col)) {
                    shapeRenderer.setColor(ThemeManager.getSelectionColor());
                    shapeRenderer.rect(
                        board.getX() + col * cellSize,
                        board.getY() + row * cellSize,
                        cellSize, cellSize
                    );
                }
            }
        }
        shapeRenderer.end();

        batch.begin();
        font.setColor(ThemeManager.getNumberColor());
        for (int row = 0; row < board.getGridSize(); row++) {
            for (int col = 0; col < board.getGridSize(); col++) {
                int value = board.getValue(row, col);
                if (value != 0) {
                    float x = board.getX() + col * cellSize + cellSize/2 - 5;
                    float y = board.getY() + row * cellSize + cellSize/2 + 5;
                    font.draw(batch, String.valueOf(value), x, y);
                }
            }
        }
        batch.end();

        renderGridLines(board);
    }

    private void renderCell(SudokuBoard board, int row, int col, float cellSize) {
        float x = board.getX() + col * cellSize;
        float y = board.getY() + row * cellSize;

        if (board.isSelected(row, col)) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(ThemeManager.getSelectionColor());
            shapeRenderer.rect(x, y, cellSize, cellSize);
            shapeRenderer.end();
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
        float x = board.getX();
        float y = board.getY();
        float size = board.getSize();
        float cellSize = board.getCellSize();
        int blockSize = board.getBlockSize();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glLineWidth(1.0f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(ThemeManager.getLineColor());

        for (int i = 0; i <= board.getGridSize(); i++) {
            shapeRenderer.line(x, y + i * cellSize, x + size, y + i * cellSize); // горизонтальные
            shapeRenderer.line(x + i * cellSize, y, x + i * cellSize, y + size); // вертикальные
        }
        shapeRenderer.end();

        Gdx.gl.glLineWidth(3.0f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(ThemeManager.getBlockLineColor());

        int blocks = board.getGridSize() / blockSize;
        for (int i = 0; i <= blocks; i++) {
            float pos = i * blockSize * cellSize;
            shapeRenderer.line(x, y + pos, x + size, y + pos); // горизонтальные
            shapeRenderer.line(x + pos, y, x + pos, y + size); // вертикальные
        }
        shapeRenderer.end();

        Gdx.gl.glLineWidth(1.0f);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }
}
