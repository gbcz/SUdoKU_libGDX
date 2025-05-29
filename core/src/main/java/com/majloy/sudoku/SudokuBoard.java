package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;

public class SudokuBoard {
    private int[][] grid;
    private final int gridSize;
    private final float boardX, boardY;
    private final float cellSize;
    private final OrthographicCamera camera;
    private int selectedRow = -1, selectedCol = -1;

    private static final float LINE_THICKNESS = 2f;
    private static final float BLOCK_LINE_THICKNESS = 4f;

    public SudokuBoard(OrthographicCamera camera, float boardX, float boardY,
                       float worldScale, int gridSize) {
        this.camera = camera;
        this.boardX = boardX;
        this.boardY = boardY;
        this.cellSize = worldScale;
        this.gridSize = gridSize;
        this.grid = new int[gridSize][gridSize];
        generatePuzzle();
    }

    private void generatePuzzle() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = (i * 3 + i / 3 + j) % gridSize + 1;
            }
        }
    }

    public void removeNumbers(int count) {
        Random random = new Random();
        while (count > 0) {
            int row = random.nextInt(gridSize);
            int col = random.nextInt(gridSize);
            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                count--;
            }
        }
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
        for (int i = 0; i <= 3; i++) {
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
                Gdx.app.log("Input", "Selected cell: " + selectedRow + "," + selectedCol);
            }
        }
    }
}
