package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private final SudokuGame game;
    private SudokuBoard board;
    private SpriteBatch batch;

    public GameScreen(SudokuGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.board = new SudokuBoard(game.camera,
            SudokuGame.BOARD_OFFSET_X,
            SudokuGame.BOARD_OFFSET_Y,
            SudokuGame.WORLD_SCALE);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(game.camera.combined);
        batch.begin();
        board.render(batch, Assets.font);
        batch.end();

        board.handleInput();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
