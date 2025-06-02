package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SudokuRenderer {
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final SudokuGame game;

    public SudokuRenderer(SudokuGame game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.font = game.fontManager.getRegularFont();
    }

    public void render(float delta) {
        // Очистка экрана
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Отрисовка текущего экрана
        if (game.getScreen() instanceof GameScreen) {
            renderGameScreen((GameScreen) game.getScreen(), delta);
        } else if (game.getScreen() instanceof MainMenuScreen) {
            renderMainMenu((MainMenuScreen) game.getScreen(), delta);
        }
        // Добавьте другие экраны по аналогии
    }

    private void renderGameScreen(GameScreen screen, float delta) {
        // Отрисовка игрового поля
        renderBoard(screen.getBoard());

        // Отрисовка UI
        batch.begin();
        renderGameUI(screen);
        batch.end();
    }

    private void renderBoard(SudokuBoard board) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Отрисовка фона доски
        shapeRenderer.setColor(ThemeManager.getBackgroundColor());
        shapeRenderer.rect(board.getX(), board.getY(),
            board.getSize(), board.getSize());

        // Отрисовка клеток
        float cellSize = board.getCellSize();
        for (int row = 0; row < board.getGridSize(); row++) {
            for (int col = 0; col < board.getGridSize(); col++) {
                renderCell(board, row, col, cellSize);
            }
        }

        // Отрисовка линий сетки
        renderGridLines(board);

        shapeRenderer.end();
    }

    private void renderCell(SudokuBoard board, int row, int col, float cellSize) {
        float x = board.getX() + col * cellSize;
        float y = board.getY() + row * cellSize;

        // Выделение выбранной клетки
        if (board.isSelected(row, col)) {
            shapeRenderer.setColor(ThemeManager.getSelectionColor());
            shapeRenderer.rect(x, y, cellSize, cellSize);
        }

        // Отрисовка значения клетки
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

        // Тонкие линии
        for (int i = 0; i <= board.getGridSize(); i++) {
            // Горизонтальные
            shapeRenderer.line(x, y + i * cellSize,
                x + size, y + i * cellSize);
            // Вертикальные
            shapeRenderer.line(x + i * cellSize, y,
                x + i * cellSize, y + size);
        }

        // Толстые линии блоков
        shapeRenderer.setColor(ThemeManager.getBlockLineColor());
        float blockSize = board.getBlockSize() * cellSize;
        for (int i = 0; i <= board.getGridSize() / board.getBlockSize(); i++) {
            // Горизонтальные
            shapeRenderer.line(x, y + i * blockSize,
                x + size, y + i * blockSize);
            // Вертикальные
            shapeRenderer.line(x + i * blockSize, y,
                x + i * blockSize, y + size);
        }

        shapeRenderer.end();
    }

    private void renderGameUI(GameScreen screen) {
        // Отрисовка таймера
        font.setColor(ThemeManager.getTextColor());
        font.draw(batch, "Time: " + screen.getFormattedTime(), 20, Gdx.graphics.getHeight() - 20);

        // Отрисовка подсказок
        font.draw(batch, "Hints: " + screen.getHintsLeft(), 20, Gdx.graphics.getHeight() - 50);

        // Отрисовка панели цифр
        renderNumberPanel(screen);

        // Отрисовка мультиплеерной информации
        if (screen.isMultiplayer()) {
            renderMultiplayerInfo(screen);
        }
    }

    private void renderNumberPanel(GameScreen screen) {
        float panelY = 20;
        float numberSize = 40;
        float spacing = 10;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(ThemeManager.getPanelColor());
        shapeRenderer.rect(0, panelY, Gdx.graphics.getWidth(), numberSize + 20);
        shapeRenderer.end();

        batch.begin();
        float x = (Gdx.graphics.getWidth() - (numberSize * 9 + spacing * 8)) / 2;
        for (int i = 1; i <= 9; i++) {
            font.draw(batch, String.valueOf(i), x, panelY + numberSize);
            x += numberSize + spacing;
        }
        batch.end();
    }

    private void renderMultiplayerInfo(GameScreen screen) {
        batch.begin();
        font.setColor(ThemeManager.getMultiplayerColor());
        font.draw(batch, "Opponent: " + screen.getOpponentName(),
            Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Progress: " + screen.getOpponentProgress() + "%",
            Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 50);
        batch.end();
    }

    private void renderMainMenu(MainMenuScreen screen, float delta) {
        batch.begin();

        // Отрисовка фона
        batch.draw(Assets.backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Отрисовка заголовка
        font.setColor(ThemeManager.getTitleColor());
        font.getData().setScale(2f);
        font.draw(batch, "SUDOKU",
            Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight() - 100);
        font.getData().setScale(1f);

        // Отрисовка информации о пользователе
        if (screen.getCurrentUser() != null) {
            font.setColor(ThemeManager.getTextColor());
            font.draw(batch, "Welcome, " + screen.getCurrentUser().getUsername(),
                20, Gdx.graphics.getHeight() - 50);
            font.draw(batch, "Level: " + screen.getCurrentUser().getLevel(),
                20, Gdx.graphics.getHeight() - 80);
        }

        batch.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }
}
