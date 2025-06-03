package com.majloy.sudoku;

import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class SudokuGenerator {
    private static final Random random = new Random();
    private static final int MAX_GENERATION_ATTEMPTS = 1000;

    public static int[][] generateValidPuzzle(int gridSize, int cellsToRemove) {
        validateGridSize(gridSize);
        validateCellsToRemove(gridSize, cellsToRemove);

        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            try {
                int[][] grid = new int[gridSize][gridSize];
                int blockSize = getBlockSize(gridSize);

                if (gridSize == 16) {
                    return generate12x12Puzzle(cellsToRemove);
                }

                fillDiagonalBlocks(grid, blockSize);
                if (!solveSudoku(grid, blockSize)) {
                    continue;
                }

                if (removeNumbersSafely(grid, cellsToRemove, blockSize)) {
                    return grid;
                }
            } catch (Exception e) {
            }
        }
        throw new IllegalStateException("Failed to generate valid Sudoku after " +
            MAX_GENERATION_ATTEMPTS + " attempts");
    }

    private static void validateGridSize(int gridSize) {
        if (gridSize != 4 && gridSize != 9 && gridSize != 16) {
            throw new IllegalArgumentException("Unsupported grid size: " + gridSize);
        }
    }

    private static void validateCellsToRemove(int gridSize, int cellsToRemove) {
        int totalCells = gridSize * gridSize;
        int minEmptyCells = (int)(totalCells * 0.3);
        int maxEmptyCells = (int)(totalCells * 0.7);

        if (cellsToRemove < minEmptyCells || cellsToRemove > maxEmptyCells) {
            throw new IllegalArgumentException(String.format(
                "Invalid cellsToRemove: %d for grid %dx%d (should be between %d and %d)",
                cellsToRemove, gridSize, gridSize, minEmptyCells, maxEmptyCells));
        }
    }

    private static int[][] generate12x12Puzzle(int cellsToRemove) {
        int[][] grid = new int[16][16];
        int blockRows = 4;
        int blockCols = 4;

        for (int boxRow = 0; boxRow < 4; boxRow++) {
            for (int boxCol = 0; boxCol < 4; boxCol++) {
                fill12x12Block(grid, boxRow * blockRows, boxCol * blockCols);
            }
        }

        removeNumbersSimple(grid, cellsToRemove);

        return grid;
    }

    private static void fill12x12Block(int[][] grid, int startRow, int startCol) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 16; i++) numbers.add(i);
        Collections.shuffle(numbers);

        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (startRow + i < 16 && startCol + j < 16) {
                    grid[startRow + i][startCol + j] = numbers.get(index++);
                }
            }
        }
    }

    private static boolean removeNumbersSafely(int[][] grid, int cellsToRemove, int blockSize) {
        int count = cellsToRemove;
        int attempts = 0;
        int maxAttempts = cellsToRemove * 3;
        int[][] solution = copyGrid(grid);

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

        return count == 0;
    }

    private static void removeNumbersSimple(int[][] grid, int cellsToRemove) {
        Random random = new Random();
        int removed = 0;
        int maxAttempts = cellsToRemove * 2;

        while (removed < cellsToRemove && maxAttempts-- > 0) {
            int row = random.nextInt(16);
            int col = random.nextInt(16);

            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                removed++;
            }
        }
    }

    private static int getBlockSize(int gridSize) {
        switch (gridSize) {
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

        if (grid.length == 4) {
            return isValidFor4x4(grid, row, col, num);
        }

        return true;
    }

    private static boolean isValidFor4x4(int[][] grid, int row, int col, int num) {
        int colorBlockRow = row / 2;
        int colorBlockCol = col / 2;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int r = colorBlockRow * 2 + i;
                int c = colorBlockCol * 2 + j;
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
