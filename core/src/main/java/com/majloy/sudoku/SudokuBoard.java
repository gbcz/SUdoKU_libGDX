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
    float cellSize;
    private final OrthographicCamera camera;
    int selectedRow = -1;
    int selectedCol = -1;
    private int[] availableNumbers;
    private int selectedNumber = 0;
    private float numberSelectorY;
    private float numberSelectorHeight = 1.5f;
    private static final float SELECTION_ANIMATION_TIME = 0.5f;
    private float selectionTimer = 0;
    private boolean increasing = true;
    private static final float LINE_THICKNESS = 2f;
    private static final float BLOCK_LINE_THICKNESS = 4f;
    private static final float NUMBER_PANEL_BORDER = 3f;
    private static final float NUMBER_PANEL_HEIGHT = 1.8f;

    private int blockSize;

    public SudokuBoard(OrthographicCamera camera, float boardX, float boardY,
                       float worldScale, int gridSize, int cellsToRemove, int[][] savedGrid) {
        this.camera = camera;
        this.cellSize = worldScale * 0.9f;
        this.gridSize = gridSize;
        this.blockSize = getBlockSize(gridSize);

        availableNumbers = new int[gridSize];
        for (int i = 0; i < gridSize; i++) {
            availableNumbers[i] = i + 1;
        }

        float scaleFactor = 1f;
        if (gridSize == 12) scaleFactor = 0.85f;
        if (gridSize == 6) scaleFactor = 1.1f;

        this.cellSize *= scaleFactor;

        float totalBoardHeight = gridSize * cellSize;
        float totalHeight = totalBoardHeight + cellSize * numberSelectorHeight;

        this.boardX = (SudokuGame.WORLD_WIDTH - gridSize * cellSize) / 2;
        this.boardY = SudokuGame.WORLD_HEIGHT - SudokuGame.BOARD_OFFSET_Y - totalBoardHeight;
        this.numberSelectorY = boardY - cellSize * numberSelectorHeight;

        if (savedGrid != null) {
            this.grid = savedGrid;
        } else {
            this.grid = SudokuGenerator.generateValidPuzzle(gridSize, cellsToRemove);
        }
    }

    public int getGridSize() {
        return gridSize;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int getCellValue(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
        return grid[row][col];
    }

    public void setCellValue(int row, int col, int value) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
        if (value < 0 || value > gridSize) {
            throw new IllegalArgumentException("Invalid cell value");
        }
        grid[row][col] = value;
    }

    public boolean hasSelectedCell() {
        return selectedRow != -1 && selectedCol != -1;
    }

    public void setSelectedCellValue(int value) {
        if (hasSelectedCell() && grid[selectedRow][selectedCol] == 0) {
            if (isValidMove(selectedRow, selectedCol, value)) {
                grid[selectedRow][selectedCol] = value;
            }
        }
    }

    public int[] findEmptyCell() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[row][col] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    public int getSolutionForCell(int row, int col) {
        // В реальной реализации здесь должен быть доступ к решению
        // Для примера возвращаем 1
        return 1;
    }

    public int getCellsToRemove() {
        // Возвращает количество удаленных клеток (для сохранения игры)
        int count = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 0) count++;
            }
        }
        return count;
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

        int blockRowStart = row - row % blockSize;
        int blockColStart = col - col % blockSize;

        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                if (grid[blockRowStart + i][blockColStart + j] == num) {
                    return false;
                }
            }
        }

        if (gridSize == 6) {
            return isValidFor6x6(row, col, num);
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
        batch.setColor(Color.WHITE);
        batch.draw(Assets.whitePixel, boardX, boardY, gridSize * cellSize, gridSize * cellSize);

        //Анимированное выделение
        if (selectedRow != -1 && selectedCol != -1) {
            selectionTimer += Gdx.graphics.getDeltaTime() * (increasing ? 1 : -1);
            if (selectionTimer > SELECTION_ANIMATION_TIME) increasing = false;
            if (selectionTimer < 0) increasing = true;

            float alpha = 0.3f + 0.2f * (selectionTimer / SELECTION_ANIMATION_TIME);
            batch.setColor(1, 1, 0, alpha);
            batch.draw(Assets.whitePixel,
                boardX + selectedCol * cellSize,
                boardY + selectedRow * cellSize,
                cellSize, cellSize);
        }
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

            if (touchPos.x < boardX || touchPos.y < boardY ||
                touchPos.x > boardX + gridSize * cellSize ||
                touchPos.y > boardY + gridSize * cellSize) {
                return;
            }

            if (touchPos.x >= boardX && touchPos.x < boardX + gridSize * cellSize &&
                touchPos.y >= numberSelectorY && touchPos.y < numberSelectorY + cellSize * NUMBER_PANEL_HEIGHT) {

                int numberIndex = (int)((touchPos.x - boardX) / ((gridSize * cellSize) / gridSize));
                if (numberIndex >= 0 && numberIndex < availableNumbers.length) {
                    selectedNumber = availableNumbers[numberIndex];
                }
                return;
            }

            if (touchPos.x >= boardX && touchPos.x < boardX + gridSize * cellSize &&
                touchPos.y >= boardY && touchPos.y < boardY + gridSize * cellSize) {

                selectedCol = (int)((touchPos.x - boardX) / cellSize);
                selectedRow = (int)((touchPos.y - boardY) / cellSize);

                if (selectedNumber != 0 && grid[selectedRow][selectedCol] == 0) {
                    if (isValidMove(selectedRow, selectedCol, selectedNumber)) {
                        grid[selectedRow][selectedCol] = selectedNumber;
                    }
                }
            }
        }
    }

    public void resize(int width, int height) {
    }

    public void dispose() {
    }

    public void update(float delta) {

    }
}
