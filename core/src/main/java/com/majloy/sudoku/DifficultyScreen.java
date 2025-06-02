package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DifficultyScreen implements Screen {
    private final SudokuGame game;
    private Stage stage;
    public static int difficulty;

    public DifficultyScreen(SudokuGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Label title = new Label("Select Board Size", game.skin, "default");
        title.setAlignment(Align.center);

        TextButton boardSix = createBoardButton("6x6", 6);
        TextButton boardNine = createBoardButton("9x9", 9);
        TextButton boardTwelve = createBoardButton("12x12", 12);
        TextButton backButton = new TextButton("Back", game.skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        mainTable.add(title).padBottom(50).row();
        mainTable.add(boardSix).width(400).height(100).padBottom(20).row();
        mainTable.add(boardNine).width(400).height(100).padBottom(20).row();
        mainTable.add(boardTwelve).width(400).height(100).padBottom(40).row();
        mainTable.add(backButton).width(200).height(60);

        stage.addActor(mainTable);
    }

    private TextButton createBoardButton(String text, final int size) {
        TextButton button = new TextButton(text, game.skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                difficulty = size;
                game.setScreen(new DifficultyLevelScreen(game));
                dispose();
            }
        });
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
