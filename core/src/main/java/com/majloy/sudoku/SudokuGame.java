package com.majloy.sudoku;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SudokuGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;
    private SudokuBoard board;

    @Override
    public void create() {
        Assets.load();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 9 * 100, 9 * 100); // 9x9 клеток по 100px
        font = new BitmapFont(); // Шрифт по умолчанию
        board = new SudokuBoard();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Белый фон
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        board.render(batch, font); // Рендер доски
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        Assets.dispose();
    }
}
