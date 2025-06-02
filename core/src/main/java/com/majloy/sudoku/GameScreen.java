package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
    private Label hintsLabel;
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

    public void setHintsLeft(int hintsLeft) {
        this.hintsLeft = hintsLeft;
    }

    public GameScreen(SudokuGame game, int gridSize, int cellsToRemove, User user) {
        this(game, gridSize, cellsToRemove, user, null); // Передаем null для savedGrid
    }

    public GameScreen(SudokuGame game, int gridSize, int cellsToRemove, User user, int[][] savedGrid) {
        this.game = game;
        this.currentUser = user;
        this.batch = new SpriteBatch();
        this.board = new SudokuBoard(game.camera,
            SudokuGame.BOARD_OFFSET_X,
            SudokuGame.BOARD_OFFSET_Y,
            SudokuGame.WORLD_SCALE,
            gridSize,
            cellsToRemove,
            savedGrid);

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
        initUI();
    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(uiStage);

        Table table = new Table();
        table.setFillParent(true);

        //Верхняя панель с кнопками и информацией
        Table topPanel = new Table();

        table.add(topPanel).center().padTop(20).row();

        //Кнопка меню
        TextButton menuBtn = new TextButton("Menu", game.skin);
        menuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPauseMenu();
            }
        });

        //Таймер
        timeLabel = new Label("00:00", game.skin);

        //Подсказки
        hintsLabel = new Label("Hints: " + hintsLeft, game.skin);
        TextButton hintBtn = new TextButton("Hint", game.skin);
        hintBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showHint();
            }
        });

        //Добавляем элементы на верхнюю панель
        topPanel.add(menuBtn).width(100).height(50).padRight(20);
        topPanel.add(timeLabel).width(100).padRight(20);
        topPanel.add(hintsLabel).width(100).padRight(20);
        topPanel.add(hintBtn).width(100).height(50);

        table.add(topPanel).expandX().fillX().padTop(20).row();

        //Нижняя панель с цифрами для выбора
        Table numberPanel = new Table();
        for (int i = 1; i <= board.getGridSize(); i++) {
            TextButton numBtn = createNumberButton(i);
            numberPanel.add(numBtn).width(50).height(50).pad(5);
            if (i % 5 == 0) numberPanel.row();
        }

        table.add(numberPanel).expandY().bottom().padBottom(20);
        uiStage.addActor(table);
    }

    private TextButton createNumberButton(final int number) {
        TextButton button = new TextButton(String.valueOf(number), game.skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (board.hasSelectedCell()) {
                    board.setSelectedCellValue(number);
                }
            }
        });
        return button;
    }

    private void showHint() {
        if (hintsLeft <= 0) {
            Dialog noHintsDialog = new Dialog("Info", game.skin);
            noHintsDialog.text("No hints left!");
            noHintsDialog.button("OK");
            noHintsDialog.show(uiStage);
            return;
        }

        int[] emptyCell = board.findEmptyCell();
        if (emptyCell != null) {
            int correctValue = board.getSolutionForCell(emptyCell[0], emptyCell[1]);
            board.setCellValue(emptyCell[0], emptyCell[1], correctValue);
            hintsLeft--;
            updateHintsDisplay();
        }
    }

    private void updateHintsDisplay() {
        hintsLabel.setText("Hints: " + hintsLeft);
    }

    private void showPauseMenu() {
        isPaused = true;

        Dialog pauseDialog = new Dialog("Pause", game.skin) {
            @Override
            protected void result(Object object) {
                isPaused = false;
                if (object.equals(false)) {
                    saveAndExit();
                }
            }
        };

        pauseDialog.text("Game Paused");
        pauseDialog.button("Continue", true);
        pauseDialog.button("Save & Exit", false);
        pauseDialog.key(Input.Keys.ESCAPE, true);

        pauseDialog.show(uiStage);
    }

    private void saveAndExit() {
        game.savedGame = new SudokuGame.SavedGameState(
            board.getGridSize(),
            board.getCellsToRemove(),
            board.getGrid()
        );

        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    private void showWinScreen() {
        Dialog winDialog = new Dialog("Congratulations!", game.skin);
        winDialog.text("You solved the puzzle!");

        if (currentUser != null) {
            currentUser.incrementGamesPlayed();
            currentUser.incrementGamesWon();
            currentUser.setLevel(currentUser.getLevel() + 1);
            game.dbHelper.updateUserStats(currentUser);

            // Проверка достижений
            game.achievementSystem.checkAchievements(currentUser);

            winDialog.text("\nLevel up! Now you're level " + currentUser.getLevel());

            // Показ новых достижений
            for (String achievementId : currentUser.getUnlockedAchievements()) {
                AchievementSystem.Achievement achievement =
                    AchievementSystem.ALL_ACHIEVEMENTS.get(achievementId);
                if (achievement != null) {
                    winDialog.text("\nAchievement unlocked: " + achievement.getName());
                }
            }
        }

        winDialog.button("OK", true);
        winDialog.show(uiStage);
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

        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        board.render(batch, game.fontManager.getRegularFont());
        batch.end();

        uiStage.act(delta);
        uiStage.draw();

        if (board.isSolved()) {
            showWinScreen();
        }

        if (board.isMultiplayer()) {
            board.renderMultiplayerInfo(batch, game.fontManager.getSmallFont());
        }
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
