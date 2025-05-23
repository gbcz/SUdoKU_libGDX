package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SudokuBoard {
    private final int[][] grid;
    private final float cellSize = 100;

    private int selectedRow = -1, selectedCol = -1;

    public SudokuBoard() {
        grid = new int[9][9];
        generatePuzzle();
    }

    private void generatePuzzle() {
        // Заполняем сетку (пока заглушка)
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = (i + j) % 9 + 1; // Простой пример
            }
        }
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        handleInput();
        // Рисуем сетку
        batch.setColor(0, 0, 0, 1); // Чёрный цвет
        for (int i = 0; i <= 9; i++) {
            // Горизонтальные линии
            batch.draw(Assets.whitePixel, 0, i * cellSize, 9 * cellSize, 2);
            // Вертикальные линии
            batch.draw(Assets.whitePixel, i * cellSize, 0, 2, 9 * cellSize);
        }

        // Рисуем цифры
        font.setColor(0, 0, 0, 1);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    font.draw(batch, String.valueOf(grid[i][j]),
                        j * cellSize + 40,
                        (9 - i) * cellSize - 40);
                }
            }
        }

        if (selectedRow != -1 && selectedCol != -1) {
            batch.setColor(0.8f, 0.8f, 1, 0.5f);
            batch.draw(Assets.whitePixel,
                selectedCol * cellSize,
                (8 - selectedRow) * cellSize,
                cellSize, cellSize);
        }
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Инвертируем Y
            selectedCol = (int) (touchX / cellSize);
            selectedRow = 8 - (int) (touchY / cellSize); // Т.к. координаты идут сверху
        }
    }
}
