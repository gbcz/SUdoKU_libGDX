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
    private final MainMenuScreen mainMenu;

    public ProfileScreen(SudokuGame game, User user, MainMenuScreen mainMenu) {
        this.game = game;
        this.user = user;
        this.mainMenu = mainMenu;
        setupUI();
        game.getRenderer().setCurrentScreen(this);
    }

    private void setupUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        game.getRenderer().setCurrentStage(stage);

        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Profile", game.skin, "default");
        title.setAlignment(Align.center);

        Label nameLabel = new Label("Username: ", game.skin);
        Label levelLabel = new Label("Level: ", game.skin);
        Label gamesLabel = new Label("Games Played: ", game.skin);

        if (user != null) {
            nameLabel.setText("Username: " + user.getUsername());
            levelLabel.setText("Level: " + user.getLevel());
            gamesLabel.setText("Games Played: " + user.getGamesPlayed());
        } else {
            nameLabel.setText("Username: Guest");
            levelLabel.setText("Level: none");
            gamesLabel.setText("Games Played: none");
        }
        TextButton backButton = new TextButton("Back", game.skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        TextButton registrationButton = new TextButton("Registration", game.skin);
        registrationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen(game, mainMenu));
                dispose();
            }
        });

        TextButton loginButton = new TextButton("Login", game.skin);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen(game, mainMenu));
                dispose();
            }
        });

        table.add(title).padBottom(50).row();
        table.add(nameLabel).padBottom(20).row();
        table.add(levelLabel).padBottom(20).row();
        table.add(gamesLabel).padBottom(40).row();
        table.add(registrationButton).width(180).height(60);
        table.add(loginButton).width(180).height(60).row();
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
