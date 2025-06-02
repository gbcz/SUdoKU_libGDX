package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ProfileScreen implements Screen {
    private final SudokuGame game;
    private final User user;
    private Stage stage;

    public ProfileScreen(SudokuGame game, User user) {
        this.game = game;
        this.user = user;
        setupUI();
        game.getRenderer().setCurrentScreen(this);
    }

    private void setupUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Profile", game.skin, "default");
        title.setAlignment(Align.center);

        Label nameLabel = new Label("Username: " + user.getUsername(), game.skin);
        Label levelLabel = new Label("Level: " + user.getLevel(), game.skin);
        Label gamesLabel = new Label("Games Played: " + user.getGamesPlayed(), game.skin);

        TextButton backButton = new TextButton("Back", game.skin);
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
