package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginScreen implements Screen {
    private final SudokuGame game;
    private final MainMenuScreen mainMenu;
    private Stage stage;
    private Label errorLabel;

    public LoginScreen(SudokuGame game, MainMenuScreen mainMenu) {
        this.game = game;
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

        Label title = new Label("Login", game.skin, "default");
        title.setAlignment(Align.center);

        TextField usernameField = new TextField("", game.skin);
        usernameField.setMessageText("Username");

        TextField passwordField = new TextField("", game.skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        errorLabel = new Label("", game.skin);
        errorLabel.setColor(Color.RED);

        TextButton loginBtn = new TextButton("Login", game.skin);
        TextButton backBtn = new TextButton("Back", game.skin);

        loginBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    showError("Username and password are required");
                    return;
                }

                User user = game.dbHelper.loginUser(username, password);
                if (user != null) {
                    mainMenu.setCurrentUser(user);
                    game.setScreen(mainMenu);
                    dispose();
                } else {
                    showError("Invalid username or password");
                }
            }
        });

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(mainMenu);
                dispose();
            }
        });

        table.add(title).colspan(2).padBottom(30).row();
        table.add(errorLabel).colspan(2).padBottom(10).row();
        table.add(new Label("Username:", game.skin)).padRight(10);
        table.add(usernameField).width(300).height(50).padBottom(20).row();
        table.add(new Label("Password:", game.skin)).padRight(10);
        table.add(passwordField).width(300).height(50).padBottom(40).row();
        table.add(loginBtn).colspan(2).width(300).height(60).padBottom(20).row();
        table.add(backBtn).colspan(2).width(200).height(50);

        stage.addActor(table);
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    @Override
    public void render(float delta) {
        game.getRenderer().render(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.getViewport().getCamera().position.set(
            stage.getViewport().getWorldWidth()/2,
            stage.getViewport().getWorldHeight()/2,
            0
        );
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
