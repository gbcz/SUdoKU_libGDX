package com.majloy.sudoku;

import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class SudokuGenerator {
    private static final Random random = new Random();

    public static int[][] generateValidPuzzle(int gridSize, int cellsToRemove) {
        if (gridSize != 6 && gridSize != 9 && gridSize != 12) {
            throw new IllegalArgumentException("Unsupported grid size");
        }

        int[][] grid = new int[gridSize][gridSize];
        int blockSize = getBlockSize(gridSize);

        if (gridSize == 12) {
            return generate12x12Puzzle(cellsToRemove);
        }

        fillDiagonalBlocks(grid, blockSize);
        if (!solveSudoku(grid, blockSize)) {
            throw new IllegalStateException("Failed to generate valid Sudoku");
        }
        removeNumbers(grid, cellsToRemove, blockSize);

        return grid;
    }

    private static int[][] generate12x12Puzzle(int cellsToRemove) {
        int[][] grid = new int[12][12];
        int blockRows = 3;
        int blockCols = 4;

        for (int boxRow = 0; boxRow < 4; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                fill12x12Block(grid, boxRow * blockRows, boxCol * blockCols);
            }
        }

        removeNumbersSimple(grid, cellsToRemove);

        return grid;
    }

    private static void fill12x12Block(int[][] grid, int startRow, int startCol) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 12; i++) numbers.add(i);
        Collections.shuffle(numbers);

        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (startRow + i < 12 && startCol + j < 12) {
                    grid[startRow + i][startCol + j] = numbers.get(index++);
                }
            }
        }
    }

    private static void removeNumbersSimple(int[][] grid, int cellsToRemove) {
        Random random = new Random();
        int removed = 0;
        int maxAttempts = cellsToRemove * 2;

        while (removed < cellsToRemove && maxAttempts-- > 0) {
            int row = random.nextInt(12);
            int col = random.nextInt(12);

            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                removed++;
            }
        }
    }

    private static int getBlockSize(int gridSize) {
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

    private static void fillDiagonalBlocks(int[][] grid, int blockSize) {
        for (int box = 0; box < grid.length; box += blockSize) {
            fillBlock(grid, box, box, blockSize);
        }
    }

    private static void fillBlock(int[][] grid, int row, int col, int blockSize) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= grid.length; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        int index = 0;
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                if (row + i < grid.length && col + j < grid.length) {
                    grid[row + i][col + j] = numbers.get(index++);
                }
            }
        }
    }

    public static boolean solveSudoku(int[][] grid, int blockSize) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= grid.length; num++) {
                        if (isValid(grid, row, col, num, blockSize)) {
                            grid[row][col] = num;
                            if (solveSudoku(grid, blockSize)) {
                                return true;
                            }
                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int[][] grid, int row, int col, int num, int blockSize) {
        for (int x = 0; x < grid.length; x++) {
            if (grid[row][x] == num) {
                return false;
            }
        }

        for (int x = 0; x < grid.length; x++) {
            if (grid[x][col] == num) {
                return false;
            }
        }

        int blockRow = row - row % blockSize;
        int blockCol = col - col % blockSize;
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                if (grid[blockRow + i][blockCol + j] == num) {
                    return false;
                }
            }
        }

        if (grid.length == 6) {
            return isValidFor6x6(grid, row, col, num);
        }

        return true;
    }

    private static boolean isValidFor6x6(int[][] grid, int row, int col, int num) {
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

    private static void removeNumbers(int[][] grid, int cellsToRemove, int blockSize) {
        int count = cellsToRemove;
        int attempts = 0;
        int maxAttempts = 1000;

        while (count > 0 && attempts < maxAttempts) {
            int row = random.nextInt(grid.length);
            int col = random.nextInt(grid.length);

            if (grid[row][col] != 0) {
                int temp = grid[row][col];
                grid[row][col] = 0;

                int[][] tempGrid = copyGrid(grid);
                if (countSolutions(tempGrid, blockSize) == 1) {
                    count--;
                } else {
                    grid[row][col] = temp;
                }
            }
            attempts++;
        }

        if (count > 0) {
            System.out.println("Warning: Only removed " + (cellsToRemove - count) +
                " cells out of " + cellsToRemove);
        }
    }

    public static boolean hasUniqueSolution(int[][] grid, int blockSize) {
        int[][] tempGrid = copyGrid(grid);
        return countSolutions(tempGrid, blockSize) == 1;
    }

    private static int countSolutions(int[][] grid, int blockSize) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                if (grid[row][col] == 0) {
                    int count = 0;
                    for (int num = 1; num <= grid.length && count < 2; num++) {
                        if (isValid(grid, row, col, num, blockSize)) {
                            grid[row][col] = num;
                            count += countSolutions(grid, blockSize);
                            grid[row][col] = 0;
                        }
                    }
                    return count;
                }
            }
        }
        return 1;
    }

    private static int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][grid.length];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, grid.length);
        }
        return copy;
    }
}
