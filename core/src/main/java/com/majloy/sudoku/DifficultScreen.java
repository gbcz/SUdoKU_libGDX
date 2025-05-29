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

public class DifficultScreen implements Screen {
    private final SudokuGame game;
    private Stage stage;
    private Skin skin;
    private BitmapFont titleFont;

    public DifficultScreen(SudokuGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        titleFont = new BitmapFont(Gdx.files.internal("default.fnt"));
        titleFont.getData().setScale(1f);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.GOLDENROD);
        Label title = new Label("Difficult", titleStyle);
        title.setAlignment(Align.center);

        TextButton backButton = new TextButton("BACK", skin);
        TextButton easyButton = new TextButton("EASY", skin);
        TextButton mediumButton = new TextButton("MEDIUM", skin);
        TextButton hardButton = new TextButton("HARD", skin);

        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                int gridSize = DifficultyScreen.difficulty;
                int cellsToRemove = (int)(gridSize * gridSize * 0.4);
                GameScreen gameScreen = new GameScreen(game, gridSize, cellsToRemove);
                game.setScreen(gameScreen);
            }
        });

        mediumButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                int gridSize = DifficultyScreen.difficulty;
                int cellsToRemove = (int)(gridSize * gridSize * 0.6);
                GameScreen gameScreen = new GameScreen(game, gridSize, cellsToRemove);
                game.setScreen(gameScreen);
            }
        });

        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                int gridSize = DifficultyScreen.difficulty;
                int cellsToRemove = (int)(gridSize * gridSize * 0.8);
                GameScreen gameScreen = new GameScreen(game, gridSize, cellsToRemove);
                game.setScreen(gameScreen);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                DifficultyScreen difficultyScreen = new DifficultyScreen(game);
                game.setScreen(difficultyScreen);
            }
        });

        mainTable.add(title).padBottom(50).row();
        mainTable.add(easyButton).width(400).height(80).padBottom(20).row();
        mainTable.add(mediumButton).width(400).height(80).padBottom(40).row();
        mainTable.add(hardButton).width(400).height(80).padBottom(60).row();
        mainTable.add(backButton).width(200).height(80).row();

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
        skin.dispose();
        titleFont.dispose();
    }
}
