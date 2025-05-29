package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class SudokuBoard {
    private int[][] grid;
    final int gridSize;
    final float boardX;
    final float boardY;
    final float cellSize;
    private final OrthographicCamera camera;
    int selectedRow = -1;
    int selectedCol = -1;

    private static final float LINE_THICKNESS = 2f;
    private static final float BLOCK_LINE_THICKNESS = 4f;

    private int blockSize;

    public SudokuBoard(OrthographicCamera camera, float boardX, float boardY,
                       float worldScale, int gridSize, int cellsToRemove) {
        this.camera = camera;
        this.boardX = boardX;
        this.boardY = boardY;
        this.cellSize = worldScale;
        this.gridSize = gridSize;
        this.blockSize = getBlockSize(gridSize);

        int attempts = 0;
        do {
            this.grid = SudokuGenerator.generateValidPuzzle(gridSize, cellsToRemove);
            attempts++;
        } while (countEmptyCells() < cellsToRemove * 0.9 && attempts < 5);
    }

    private int countEmptyCells() {
        int count = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 0) count++;
            }
        }
        return count;
    }

    private int getBlockSize(int gridSize) {
        switch (gridSize) {
            case 6:
                return 2;
            case 9:
                return 3;
            case 12:
                return 3;
            default:
                throw new IllegalArgumentException("Unsupported grid size");
        }
    }

    public boolean isValidMove(int row, int col, int num) {
        for (int i = 0; i < gridSize; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }

        if (gridSize == 12) {
            int blockStartRow = row - row % 3;
            int blockStartCol = col - col % 4;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (grid[blockStartRow + i][blockStartCol + j] == num) {
                        return false;
                    }
                }
            }
        }

        else {
            int blockSize = (int)Math.sqrt(gridSize);
            int blockStartRow = row - row % blockSize;
            int blockStartCol = col - col % blockSize;

            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    if (grid[blockStartRow + i][blockStartCol + j] == num) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isValidFor6x6(int row, int col, int num) {
        int colorBlockRow = row / 2;
        int colorBlockCol = col / 3;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int r = colorBlockRow * 2 + i;
                int c = colorBlockCol * 3 + j;
                if (grid[r][c] == num && !(r == row && c == col)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSolved() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[row][col] == 0 || !isValidMove(row, col, grid[row][col])) {
                    return false;
                }
            }
        }
        return true;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        //Рисуем фон доски
        batch.setColor(Color.WHITE);
        batch.draw(Assets.whitePixel, boardX, boardY, gridSize * cellSize, gridSize * cellSize);
        //Рисуем сетку
        batch.setColor(Assets.LINE_COLOR);
        for (int i = 0; i <= gridSize; i++) {
            //Горизонтальные линии
            batch.draw(Assets.whitePixel,
                boardX,
                boardY + i * cellSize,
                gridSize * cellSize,
                LINE_THICKNESS);

            //Вертикальные линии
            batch.draw(Assets.whitePixel,
                boardX + i * cellSize,
                boardY,
                LINE_THICKNESS,
                gridSize * cellSize);
        }
        //Рисуем толстые линии блоков
        batch.setColor(Assets.BLOCK_LINE_COLOR);
        for (int i = 0; i <= gridSize / 3; i++) {
            //Горизонтальные линии
            batch.draw(Assets.whitePixel,
                boardX,
                boardY + i * 3 * cellSize,
                gridSize * cellSize,
                BLOCK_LINE_THICKNESS);

            //Вертикальные линии
            batch.draw(Assets.whitePixel,
                boardX + i * 3 * cellSize,
                boardY,
                BLOCK_LINE_THICKNESS,
                gridSize * cellSize);
        }
        //Отрисовка цифр
        font.setColor(Color.BLACK);
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[row][col] != 0) {
                    drawCenteredText(batch, font,
                        String.valueOf(grid[row][col]),
                        boardX + col * cellSize,
                        boardY + row * cellSize,
                        cellSize, cellSize);
                }
            }
        }
    }

    private void drawCenteredText(SpriteBatch batch, BitmapFont font,
                                  String text, float x, float y, float width, float height) {
        float textWidth = font.getData().getGlyph(text.charAt(0)).width * font.getScaleX();
        float textHeight = font.getCapHeight() * font.getScaleY();

        font.draw(batch, text,
            x + (width - textWidth) / 2,
            y + (height + textHeight) / 2);
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (touchPos.x >= boardX && touchPos.x < boardX + gridSize * cellSize &&
                touchPos.y >= boardY && touchPos.y < boardY + gridSize * cellSize) {

                selectedCol = (int) ((touchPos.x - boardX) / cellSize);
                selectedRow = (int) ((touchPos.y - boardY) / cellSize);
            }
        }
    }

    public void handleNumberInput(int number) {
        if (selectedRow != -1 && selectedCol != -1 && grid[selectedRow][selectedCol] == 0) {
            if (isValidMove(selectedRow, selectedCol, number)) {
                grid[selectedRow][selectedCol] = number;
            }
        }
    }
}
