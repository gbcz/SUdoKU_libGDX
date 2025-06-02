package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DifficultyLevelScreen implements Screen {
    private final SudokuGame game;
    private Stage stage;

    public DifficultyLevelScreen(SudokuGame game) {
        this.game = game;
        setupUI();
        game.getRenderer().setCurrentScreen(this);
    }

    private void setupUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Label title = new Label("Select Difficulty", game.skin, "default");
        title.setAlignment(Align.center);

        TextButton easyButton = createDifficultyButton("Easy", 0.4f);
        TextButton mediumButton = createDifficultyButton("Medium", 0.6f);
        TextButton hardButton = createDifficultyButton("Hard", 0.8f);
        TextButton backButton = new TextButton("Back", game.skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new DifficultyScreen(game));
                dispose();
            }
        });

        mainTable.add(title).padBottom(50).row();
        mainTable.add(easyButton).width(400).height(100).padBottom(20).row();
        mainTable.add(mediumButton).width(400).height(100).padBottom(20).row();
        mainTable.add(hardButton).width(400).height(100).padBottom(40).row();
        mainTable.add(backButton).width(200).height(60);

        stage.addActor(mainTable);
    }

    private TextButton createDifficultyButton(String text, final float difficultyFactor) {
        TextButton button = new TextButton(text, game.skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int gridSize = DifficultyScreen.difficulty;
                int cellsToRemove = (int)(gridSize * gridSize * difficultyFactor);

                game.savedGame = null;
                Preferences prefs = Gdx.app.getPreferences("SudokuSave");
                prefs.remove("has_save");
                prefs.flush();

                GameScreen gameScreen = new GameScreen(game, gridSize, cellsToRemove, game.currentUser, game.savedGame.grid);
                game.setScreen(gameScreen);
                dispose();
            }
        });
        return button;
    }

    @Override
    public void render(float delta) {
        game.getRenderer().render(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
