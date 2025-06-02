package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginScreen implements Screen {
    private final SudokuGame game;
    private final MainMenuScreen mainMenu;
    private Stage stage;
    private Skin skin;
    private DatabaseHelper dbHelper;
    private Label errorLabel;

    public LoginScreen(SudokuGame game, MainMenuScreen mainMenu) {
        this.game = game;
        this.mainMenu = mainMenu;
        this.dbHelper = new DatabaseHelper();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Login", skin, "default");
        title.setAlignment(Align.center);

        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);

        TextButton loginBtn = new TextButton("Login", skin);
        TextButton backBtn = new TextButton("Back", skin);

        loginBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();

                if (username.isEmpty() || password.isEmpty()) {
                   showError("Username and password are required");
                   return;
                }

                User user = dbHelper.loginUser(username, password);
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
        table.add(new Label("Username:", skin)).padRight(10);
        table.add(usernameField).width(300).height(50).padBottom(20).row();
        table.add(new Label("Password:", skin)).padRight(10);
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
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
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
        if (stage != null) stage.dispose();
    }
}
