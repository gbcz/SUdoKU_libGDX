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
            } else if (currentScreen instanceof ProfileScreen) {
                renderProfileScreen((ProfileScreen) currentScreen, delta);
            } else if (currentScreen instanceof StatisticsScreen) {
                renderStatisticsScreen((StatisticsScreen) currentScreen, delta);
            } else if (currentScreen instanceof SettingsScreen) {
                renderSettingsScreen((SettingsScreen) currentScreen, delta);
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
        font.draw(batch, "Time: " + screen.getFormattedTime(), 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Hints: " + screen.getHintsLeft(), 20, Gdx.graphics.getHeight() - 50);

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
        batch.end();
    }

    private void renderProfileScreen(ProfileScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    private void renderStatisticsScreen(StatisticsScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    private void renderSettingsScreen(SettingsScreen screen, float delta) {
        batch.begin();
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void renderBoard(SudokuBoard board) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(ThemeManager.getBackgroundColor());
        shapeRenderer.rect(board.getX(), board.getY(), board.getSize(), board.getSize());
        shapeRenderer.end();

        renderGrid(board);
        renderNumbers(board);
        renderSelection(board);
    }

    private void renderGrid(SudokuBoard board) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        float cellSize = board.getCellSize();
        float size = board.getSize();
        float x = board.getX();
        float y = board.getY();

        shapeRenderer.setColor(ThemeManager.getLineColor());
        for (int i = 0; i <= board.getGridSize(); i++) {
            shapeRenderer.line(x, y + i * cellSize, x + size, y + i * cellSize);
            shapeRenderer.line(x + i * cellSize, y, x + i * cellSize, y + size);
        }

        shapeRenderer.setColor(ThemeManager.getBlockLineColor());
        float blockSize = board.getBlockSize() * cellSize;
        for (int i = 0; i <= board.getGridSize() / board.getBlockSize(); i++) {
            shapeRenderer.line(x, y + i * blockSize, x + size, y + i * blockSize);
            shapeRenderer.line(x + i * blockSize, y, x + i * blockSize, y + size);
        }

        shapeRenderer.end();
    }

    private void renderNumbers(SudokuBoard board) {
        batch.begin();
        font.setColor(ThemeManager.getNumberColor());

        float cellSize = board.getCellSize();
        for (int row = 0; row < board.getGridSize(); row++) {
            for (int col = 0; col < board.getGridSize(); col++) {
                int value = board.getValue(row, col);
                if (value != 0) {
                    float x = board.getX() + col * cellSize;
                    float y = board.getY() + row * cellSize;

                    String text = String.valueOf(value);
                    float textWidth = font.getData().getGlyph(text.charAt(0)).width * font.getScaleX();
                    float textHeight = font.getCapHeight() * font.getScaleY();

                    font.draw(batch, text,
                        x + (cellSize - textWidth) / 2,
                        y + (cellSize + textHeight) / 2);
                }
            }
        }
        batch.end();
    }

    private void renderSelection(SudokuBoard board) {
        if (board.hasSelectedCell()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(ThemeManager.getSelectionColor());

            float cellSize = board.getCellSize();
            shapeRenderer.rect(
                board.getX() + board.getSelectedCol() * cellSize,
                board.getY() + board.getSelectedRow() * cellSize,
                cellSize, cellSize);

            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }
}
