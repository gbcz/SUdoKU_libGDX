package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    private final SudokuGame game;
    private Stage stage;
    private Skin skin;
    private BitmapFont titleFont;

    public MainMenuScreen(SudokuGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        titleFont = new BitmapFont(Gdx.files.internal("default.fnt"));
        titleFont.getData().setScale(2f);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.GOLDENROD);
        Label title = new Label("SUDOKU", titleStyle);
        title.setAlignment(Align.center);

        TextButton newGameButton = new TextButton("NEW GAME", skin);
        TextButton exitButton = new TextButton("EXIT", skin);

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                GameScreen gameScreen = new GameScreen(game);
                game.setScreen(gameScreen);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        mainTable.add(title).padBottom(50).row();
        mainTable.add(newGameButton).width(400).height(80).padBottom(20).row();
        mainTable.add(exitButton).width(400).height(80).row();

        stage.addActor(mainTable);
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
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        titleFont.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
