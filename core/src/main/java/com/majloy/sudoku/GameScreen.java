package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private final SudokuGame game;
    private final SudokuBoard board;
    private final SpriteBatch batch;

    public GameScreen(SudokuGame game, int gridSize, int cellsToRemove) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.board = new SudokuBoard(game.camera,
            SudokuGame.BOARD_OFFSET_X,
            SudokuGame.BOARD_OFFSET_Y,
            SudokuGame.WORLD_SCALE,
            gridSize,
            cellsToRemove);

        game.camera.position.set(
            SudokuGame.WORLD_WIDTH / 2,
            SudokuGame.WORLD_HEIGHT / 2,
            0
        );
        game.camera.update();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
        game.camera.position.set(SudokuGame.WORLD_WIDTH/2, SudokuGame.WORLD_HEIGHT/2, 0);
        game.camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        batch.setProjectionMatrix(game.camera.combined);

        batch.begin();
        board.render(batch, Assets.font);

        if (board.selectedRow != -1 && board.selectedCol != -1) {
            batch.setColor(1, 1, 0, 0.3f);
            batch.draw(Assets.whitePixel,
                board.boardX + board.selectedCol * board.cellSize,
                board.boardY + board.selectedRow * board.cellSize,
                board.cellSize, board.cellSize);
        }
        if (board.isSolved()) {
            Assets.font.setColor(Color.GREEN);
            Assets.font.getData().setScale(2f);
            Assets.font.draw(batch, "ПОБЕДА!",
                board.boardX + board.gridSize * board.cellSize / 2 - 50,
                board.boardY + board.gridSize * board.cellSize + 30);
        }
        batch.end();

        board.handleInput();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
