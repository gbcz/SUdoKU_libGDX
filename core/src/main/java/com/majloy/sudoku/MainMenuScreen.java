package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    private final SudokuGame game;
    private Stage stage;
    private User currentUser;
    private Label userInfoLabel;
    private MainMenuScreen mainMenu;

    public MainMenuScreen(SudokuGame game) {
        this.game = game;
        mainMenu = this;
        game.getRenderer().setCurrentScreen(this);
    }

    @Override public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        game.getRenderer().setCurrentStage(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Table header = createHeader();
        Table menuButtons = createMenuButtons();

        Label footer = new Label("© 2025 Sudoku Game", game.skin, "default");
        footer.setColor(Color.GRAY);

        userInfoLabel = new Label(currentUser != null ?
            "Welcome, " + currentUser.getUsername() + " (Level " + currentUser.getLevel() + ")" :
            "Guest Mode", game.skin);

        mainTable.add(header).expandX().fillX().top().row();
        mainTable.add(menuButtons).expand().center().row();
        mainTable.add(footer).padBottom(20).row();
        mainTable.add(userInfoLabel).padBottom(30).row();

        stage.addActor(mainTable);
    }

    private Table createHeader() {
        Table header = new Table();
        header.setBackground(game.skin.getDrawable("default-pane"));

        Image avatar = new Image(game.skin, "default-round");
        avatar.setSize(60, 60);

        Label username = new Label("Guest", game.skin, "default");
        Label level = new Label("Level: -", game.skin, "default");

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
        TextButton newGameBtn = new TextButton("New Game", game.skin, "default");
        TextButton continueBtn = new TextButton("Continue", game.skin, "default");
        TextButton statisticsBtn = new TextButton("Statistics", game.skin, "default");
        TextButton profileBtn = new TextButton("Profile", game.skin, "default");
        TextButton settingsBtn = new TextButton("Settings", game.skin, "default");
        TextButton exitBtn = new TextButton("Exit", game.skin, "default");
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
                    Dialog noSaveDialog = new Dialog("Info", game.skin);
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

        profileBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ProfileScreen(game, currentUser, mainMenu));
                dispose();
            }
        });

        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingScreen(game));
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
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    public User getCurrentUser() {
        return currentUser;
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
}
