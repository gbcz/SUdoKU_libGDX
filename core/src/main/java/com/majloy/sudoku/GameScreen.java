package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final SudokuGame game;
    private final SudokuBoard board;
    private Stage stage;
    private float playTime = 0;
    private int hintsLeft = 3;

    public GameScreen(SudokuGame game, int gridSize, int cellsToRemove, User user, int[][] grid) {
        this.game = game;
        this.board = new SudokuBoard(game.camera, SudokuGame.BOARD_OFFSET_X,
            SudokuGame.BOARD_OFFSET_Y, SudokuGame.WORLD_SCALE, gridSize, cellsToRemove, grid);

        setupUI();
        game.getRenderer().setCurrentScreen(this);
    }

    private void setupUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        game.getRenderer().setCurrentStage(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        playTime += delta;
        game.getRenderer().render(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        board.dispose();
    }

    // Остальные методы интерфейса Screen...
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    public String getFormattedTime() {
        int minutes = (int)(playTime / 60);
        int seconds = (int)(playTime % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public boolean isMultiplayer() {
        return board != null && board.isMultiplayer();
    }

    public String getOpponentName() {
        return board != null ? board.getOpponentName() : "";
    }

    public int getOpponentProgress() {
        return board != null ? board.getOpponentProgress() : 0;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public int getHintsLeft() {
        return hintsLeft;
    }
}
