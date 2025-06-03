package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;

public class SudokuBoard {
    private int[][] grid;
    int gridSize;
    float boardX;
    float boardY;
    float cellSize;
    private final OrthographicCamera camera;
    private String opponentName;
    private int opponentProgress;
    int selectedRow = -1;
    int selectedCol = -1;
    private final int[] availableNumbers;
    private float numberSelectorY;
    private float x, y;
    private int blockSize;
    private boolean multiplayer = false;
    private int[][] originalGrid;

    public void updatePosition() {
        float totalSize = gridSize * cellSize;
        this.x = (Gdx.graphics.getWidth() - totalSize) / 2;
        this.y = (Gdx.graphics.getHeight() - totalSize) / 2;
    }

    public int getBlockSize() { switch (gridSize) {
        case 4:
            return 2;
        case 9:
            return 3;
        case 16:
            return 4;
        default:
            throw new IllegalArgumentException("Unsupported grid size");
        }
    }

    public boolean isSelected(int row, int col) {
        return selectedRow == row && selectedCol == col;
    }

    public SudokuBoard(OrthographicCamera camera, float worldScale, int gridSize, int cellsToRemove, int[][] savedGrid) {
        this.camera = camera;
        this.gridSize = gridSize;
        this.blockSize = getBlockSize();

        this.cellSize = calculateCellSize(worldScale);

        this.availableNumbers = new int[gridSize];
        for (int i = 0; i < gridSize; i++) {
            availableNumbers[i] = i + 1;
        }

        if (savedGrid != null) {
            this.grid = copyGrid(savedGrid);
            this.originalGrid = copyGrid(savedGrid);
        } else {
            this.grid = SudokuGenerator.generateValidPuzzle(gridSize, cellsToRemove);
            this.originalGrid = copyGrid(this.grid);
        }

        centerBoard();
    }

    private int[][] copyGrid(int[][] source) {
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            copy[i] = Arrays.copyOf(source[i], source[i].length);
        }
        return copy;
    }

    private float calculateCellSize(float worldScale) {
        float baseCellSize = worldScale * 0.9f;
        switch (gridSize) {
            case 4:  return baseCellSize * 1.1f;
            case 9:  return baseCellSize;
            case 16: return baseCellSize * 0.85f;
            default: return baseCellSize;
        }
    }

    private void centerBoard() {
        float totalSize = gridSize * cellSize;

        this.boardX = (Gdx.graphics.getWidth() - totalSize) / 2;
        this.boardY = (Gdx.graphics.getHeight() - totalSize) / 2;

        this.numberSelectorY = boardY - cellSize * 1.5f;
    }

    public void updateSize() {
        centerBoard();
    }

    public boolean isValidMove(int row, int col, int number) {
        if (number == 0) return true;

        for (int i = 0; i < gridSize; i++) {
            if (grid[row][i] == number && i != col) return false;
            if (grid[i][col] == number && i != row) return false;
        }

        int blockStartRow = row - row % blockSize;
        int blockStartCol = col - col % blockSize;

        for (int r = 0; r < blockSize; r++) {
            for (int c = 0; c < blockSize; c++) {
                if (grid[blockStartRow + r][blockStartCol + c] == number &&
                    (blockStartRow + r) != row && (blockStartCol + c) != col) {
                    return false;
                }
            }
        }

        return true;
    }

    public void handleInput() {
        for (int i = 0; i < 2; i++) {
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                camera.unproject(touchPos);

                if (touchPos.x >= boardX && touchPos.x <= boardX + getSize() &&
                    touchPos.y >= boardY && touchPos.y <= boardY + getSize()) {

                    selectedCol = (int) ((touchPos.x - boardX) / cellSize);
                    selectedRow = (int) ((touchPos.y - boardY) / cellSize);

                    selectedCol = MathUtils.clamp(selectedCol, 0, gridSize - 1);
                    selectedRow = MathUtils.clamp(selectedRow, 0, gridSize - 1);
                }
            }
        }
    }


    public int getGridSize() { return gridSize; }
    public float getX() { return boardX; }
    public float getY() { return boardY; }
    public float getSize() { return gridSize * cellSize; }
    public float getCellSize() { return cellSize; }
    public int[][] getGrid() { return grid; }
    public int getValue(int row, int col) { return grid[row][col]; }
    public boolean hasSelectedCell() { return selectedRow != -1 && selectedCol != -1; }
    public boolean isMultiplayer() { return multiplayer; }
    public void setCellValue(int row, int col, int value) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
        if (value < 0 || value > gridSize) {
            throw new IllegalArgumentException("Invalid cell value");
        }
        grid[row][col] = value;
    }

    public void setSelectedCellValue(int number) {
        if (selectedRow >= 0 && selectedCol >= 0 &&
            selectedRow < gridSize && selectedCol < gridSize) {

            if (isCellEditable(selectedRow, selectedCol)) {
                if (number == 0 || isValidMove(selectedRow, selectedCol, number)) {
                    grid[selectedRow][selectedCol] = number;
                } else {
                    Gdx.app.log("Sudoku", "Invalid move!");
                }
            }
        }
    }

    public boolean isCellEditable(int row, int col) {
        return originalGrid[row][col] == 0;
    }

    public boolean isValid(int row, int col, int value) {
        if (value == 0) return true;

        for (int i = 0; i < gridSize; i++) {
            if (grid[row][i] == value && i != col) return false;
            if (grid[i][col] == value && i != row) return false;
        }

        int blockRow = row - row % blockSize;
        int blockCol = col - col % blockSize;

        for (int r = 0; r < blockSize; r++) {
            for (int c = 0; c < blockSize; c++) {
                if (grid[blockRow + r][blockCol + c] == value &&
                    (blockRow + r) != row && (blockCol + c) != col) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean hasConflict(int row, int col) {
        int value = grid[row][col];
        if (value == 0) return false;

        return !isValid(row, col, value);
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
        int count = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 0) count++;
            }
        }
        return count;
    }
    public int getOpponentProgress() {return opponentProgress;}
    public String getOpponentName() {return opponentName;}
    public void dispose() {}
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
}
