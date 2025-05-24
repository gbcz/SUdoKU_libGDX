package com.majloy.sudoku;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SudokuGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SudokuBoard board;

    // Константы масштабирования
    public static final float WORLD_SCALE = 50f; // Увеличиваем масштаб для лучшего отображения
    public static final float WORLD_WIDTH = 13 * WORLD_SCALE;
    public static final float WORLD_HEIGHT = 13 * WORLD_SCALE;
    public static final float BOARD_OFFSET_X = 2f * WORLD_SCALE;
    public static final float BOARD_OFFSET_Y = 2f * WORLD_SCALE;

    @Override
    public void create() {
        Assets.load();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        board = new SudokuBoard(camera, BOARD_OFFSET_X, BOARD_OFFSET_Y, WORLD_SCALE);

        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        board.render(batch, Assets.font);
        batch.end();

        board.handleInput(); // Обработка ввода после отрисовки
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets.dispose();
    }
}
