package com.majloy.sudoku;

public  class SavedGameState {
    public int gridSize;
    public int cellsToRemove;
    public int[][] grid;

    public SavedGameState(int gridSize, int cellsToRemove, int[][] grid) {
        this.gridSize = gridSize;
        this.cellsToRemove = cellsToRemove;
        this.grid = grid;
    }
}
