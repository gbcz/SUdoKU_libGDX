package com.majloy.sudoku;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SudokuGame extends Game {
    public OrthographicCamera camera;
    public FitViewport viewport;

    // Константы масштабирования
    public static final float WORLD_SCALE = 50f;
    public static final float WORLD_WIDTH = 13 * WORLD_SCALE;
    public static final float WORLD_HEIGHT = 13 * WORLD_SCALE;
    public static final float BOARD_OFFSET_X = 2f * WORLD_SCALE;
    public static final float BOARD_OFFSET_Y = 2f * WORLD_SCALE;

    @Override
    public void create() {
        Assets.load();

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
    }
}
