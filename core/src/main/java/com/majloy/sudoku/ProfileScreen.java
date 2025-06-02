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

public class ProfileScreen implements Screen {
    private final SudokuGame game;
    private final User user;
    private Stage stage;
    private Skin skin;

    public ProfileScreen(SudokuGame game, User user) {
        this.game = game;
        this.user = user;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = game.skin;

        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Profile", skin, "default");
        title.setAlignment(Align.center);

        Label nameLabel = new Label("Username: " + user.getUsername(), skin);
        Label levelLabel = new Label("Level: " + user.getLevel(), skin);
        Label gamesLabel = new Label("Games Played: " + user.getGamesPlayed(), skin);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        table.add(title).padBottom(50).row();
        table.add(nameLabel).padBottom(20).row();
        table.add(levelLabel).padBottom(20).row();
        table.add(gamesLabel).padBottom(40).row();
        table.add(backButton).width(400).height(80);

        stage.addActor(table);
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
    }
}
