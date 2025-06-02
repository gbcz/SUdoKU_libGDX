package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final SudokuGame game;
    private final SudokuBoard board;
    private final SpriteBatch batch;
    private Stage uiStage;
    private User currentUser;
    private boolean isPaused = false;
    private int hintsLeft = 3;
    private float playTime = 0;
    private Label timeLabel;

    public GameScreen(SudokuGame game, int gridSize, int cellsToRemove, User user) {
        this.game = game;
        this.currentUser = user;
        this.batch = new SpriteBatch();
        this.board = new SudokuBoard(game.camera,
            SudokuGame.BOARD_OFFSET_X,
            SudokuGame.BOARD_OFFSET_Y,
            SudokuGame.WORLD_SCALE,
            gridSize,
            cellsToRemove);

        this.uiStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(uiStage);

        Table table = new Table();
        table.setFillParent(true);
        table.top().left();

        timeLabel = new Label("00:00", game.skin);
        table.add(timeLabel).pad(20).row();

        TextButton menuBtn = new TextButton("Menu", game.skin);
        menuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPauseMenu();
            }
        });

        table.add(menuBtn).pad(20);
        uiStage.addActor(table);
    }

    private void showPauseMenu() {
        isPaused = true;

        Dialog pauseDialog = new Dialog("Pause", game.skin) {
            @Override
            protected void result(Object object) {
                isPaused = false;
            }
        };

        pauseDialog.button("Continue", true);
        pauseDialog.button("Save & Exit", false);
        pauseDialog.key(Input.Keys.ESCAPE, true);

        pauseDialog.show(uiStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (isPaused) {
            uiStage.act(delta);
        } else {
            board.update(delta);
            updateTimer(delta);
        }

        // Отрисовка
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        board.render(batch, game.fontManager.getRegularFont());
        batch.end();

        uiStage.draw();

        if (board.isSolved()) {
            showWinScreen();
        }
    }

    private void showHint() {
        if (currentUser == null || hintsLeft <= 0) return;
        for (int row = 0; row < board.getGridSize(); row++) {
            for (int col = 0; col < board.getGridSize(); col++) {
                if (board.getCellValue(row, col) == 0) {
                    int correctValue = board.getSolutionForCell(row, col);
                    board.setCellValue(row, col, correctValue);
                    hintsLeft--;
                    updateHintsDisplay();
                    return;
                }
            }
        }
    }

    private void updateHintsDisplay() {
    }

    private void updateTimer(float delta) {
        if (!isPaused && !board.isSolved()) {
            playTime += delta;
            timeLabel.setText(formatTime(playTime));
        }
    }

    private String formatTime(float seconds) {
        int minutes = (int)(seconds / 60);
        int secs = (int)(seconds % 60);
        return String.format("%02d:%02d", minutes, secs);
    }

    private void showWinScreen() {
        Dialog winDialog = new Dialog("Congratulations!", game.skin);
        winDialog.text("You solved the puzzle!");

        if (currentUser != null) {
            currentUser.incrementGamesPlayed();
            currentUser.setLevel(currentUser.getLevel() + 1);
            game.dbHelper.updateUserStats(currentUser);
            winDialog.text("\nLevel up! Now you're level " + currentUser.getLevel());
        }

        winDialog.button("OK", true);
        winDialog.show(uiStage);
    }

    @Override
    public void resize(int width, int height) {
        board.resize(width, height);
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        uiStage.dispose();
        board.dispose();
    }
}
