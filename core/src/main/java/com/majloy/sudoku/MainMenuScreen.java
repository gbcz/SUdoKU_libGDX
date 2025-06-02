package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    private final SudokuGame game;
    private Stage stage;
    private Skin skin;
    private User currentUser;
    private Label userInfoLabel;

    public MainMenuScreen(SudokuGame game) {
        this.game = game;
    }
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = createCustomSkin();

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Table header = createHeader();

        Table menuButtons = createMenuButtons();

        Label footer = new Label("© 2023 Sudoku Game", skin, "default");
        footer.setColor(Color.GRAY);

        userInfoLabel = new Label(currentUser != null ?
            "Welcome, " + currentUser.getUsername() + " (Level " + currentUser.getLevel() + ")" :
            "Guest Mode", skin);

        mainTable.add(header).expandX().fillX().top().row();
        mainTable.add(menuButtons).expand().center().row();
        mainTable.add(footer).padBottom(20).row();
        mainTable.add(userInfoLabel).padBottom(30).row();

        stage.addActor(mainTable);
    }

    private Skin createCustomSkin() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = game.fontManager.getTitleFont();
        titleStyle.fontColor = Color.GOLDENROD;
        skin.add("title", titleStyle);

        TextButton.TextButtonStyle menuButtonStyle = new TextButton.TextButtonStyle();
        menuButtonStyle.font = game.fontManager.getMediumFont();
        menuButtonStyle.up = skin.getDrawable("default-round");
        skin.add("menuButton", menuButtonStyle);

        return skin;
    }

    private Table createHeader() {
        Table header = new Table();
        header.setBackground(skin.getDrawable("default-pane"));

        Image avatar = new Image(skin, "default-round");
        avatar.setSize(60, 60);

        Label username = new Label("Guest", skin);
        Label level = new Label("Level: -", skin, "default");

        if (currentUser != null) {
            username.setText(currentUser.getUsername());
            level.setText("Level: " + currentUser.getLevel());
        }

        header.add(avatar).pad(10, 20, 10, 10);
        header.add(username).padRight(10);
        header.add(level);
        header.pad(10);

        return header;
    }

    private Table createMenuButtons() {
        Table buttons = new Table();
        buttons.defaults().width(300).height(70).padBottom(15);

        TextButton newGameBtn = new TextButton("New Game", skin, "menuButton");
        TextButton continueBtn = new TextButton("Continue", skin, "menuButton");
        TextButton statisticsBtn = new TextButton("Statistics", skin, "menuButton");
        TextButton profileBtn = new TextButton("Profile", skin, "menuButton");
        TextButton settingsBtn = new TextButton("Settings", skin, "menuButton");
        TextButton exitBtn = new TextButton("Exit", skin, "menuButton");

        continueBtn.setDisabled(true);

        newGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new DifficultyScreen(game));
                dispose();
            }
        });

        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (loadSavedGame()) {
                    game.setScreen(new GameScreen(game,
                        game.savedGame.gridSize,
                        game.savedGame.cellsToRemove,
                        currentUser,
                        game.savedGame.grid));
                    dispose();
                } else {
                    Dialog noSaveDialog = new Dialog("Info", skin);
                    noSaveDialog.text("No saved game found!");
                    noSaveDialog.button("OK");
                    noSaveDialog.show(stage);
                }
            }
        });

        statisticsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StatisticsScreen(game, currentUser));
                dispose();
            }
        });

        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
                dispose();
            }
        });

        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        buttons.add(newGameBtn).row();
        buttons.add(continueBtn).row();
        buttons.add(statisticsBtn).row();
        buttons.add(profileBtn).row();
        buttons.add(settingsBtn).row();
        buttons.add(exitBtn).row();

        return buttons;
    }

    private void updateUserInfo() {
        if (stage != null) {
            show();
        }
    }

    private boolean loadSavedGame() {
        return game.savedGame != null;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (userInfoLabel != null) {
            userInfoLabel.setText("Welcome, " + user.getUsername() + " (Level " + user.getLevel() + ")");
        }
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
        stage.getViewport().update(width, height, true);
        float scale = Math.min(width / 800f, height / 1200f);
        stage.getRoot().setScale(scale);
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
    }
}
