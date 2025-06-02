package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class SudokuBoard {
    private int[][] grid;
    final int gridSize;
    final float boardX;
    final float boardY;
    float cellSize;
    private final OrthographicCamera camera;
    private float selectionTimer = 0;
    private boolean increasing = true;
    private boolean isMultiplayer;
    private String opponentName;
    private int opponentProgress;
    int selectedRow = -1;
    int selectedCol = -1;
    private int[] availableNumbers;
    private int selectedNumber = 0;
    private float numberSelectorY;
    private float numberSelectorHeight = 1.5f;
    private static final float SELECTION_ANIMATION_TIME = 0.5f;
    private static final float LINE_THICKNESS = 2f;
    private static final float BLOCK_LINE_THICKNESS = 4f;
    private static final float NUMBER_PANEL_BORDER = 3f;
    private static final float NUMBER_PANEL_HEIGHT = 1.8f;
    private int blockSize;
    private boolean multiplayer = false;
    public int getBlockSize() { return blockSize; }
    public boolean isSelected(int row, int col) {
        return selectedRow == row && selectedCol == col;
    }

    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
    }

    public SudokuBoard(OrthographicCamera camera, float boardX, float boardY,
                       float worldScale, int gridSize, int cellsToRemove, int[][] savedGrid) {
        this.camera = camera;
        this.cellSize = worldScale * 0.9f;
        this.gridSize = gridSize;
        this.blockSize = getBlockSize();

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

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (touchPos.x >= boardX && touchPos.x < boardX + gridSize * cellSize &&
                touchPos.y >= boardY && touchPos.y < boardY + gridSize * cellSize) {

                selectedCol = (int)((touchPos.x - boardX) / cellSize);
                selectedRow = (int)((touchPos.y - boardY) / cellSize);
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
    public int getSelectedRow() { return selectedRow; }
    public int getSelectedCol() { return selectedCol; }
    public boolean isMultiplayer() { return multiplayer; }
    public String getOpponentName() { return opponentName; }
    public int getOpponentProgress() { return opponentProgress; }
    public void dispose() {}
}
