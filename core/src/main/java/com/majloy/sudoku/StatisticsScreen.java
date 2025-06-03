package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StatisticsScreen implements Screen {
    private final SudokuGame game;
    private final User user;
    private Stage stage;

    public StatisticsScreen(SudokuGame game, User user) {
        this.game = game;
        this.user = user != null ? user : createGuestUser();
        setupUI();
        game.getRenderer().setCurrentScreen(this);
    }

    private User createGuestUser() {
        return new User(-1, "Guest", 1, 0);
    }

    private void setupUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        game.getRenderer().setCurrentStage(stage);

        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Statistics", game.skin, "default");
        table.add(title).padBottom(50).row();

        table.add(new Label("Games Played:", game.skin)).padBottom(10).row();
        table.add(new Label(String.valueOf(user != null ? user.getGamesPlayed() : 0), game.skin))
            .padBottom(20).row();

        table.add(new Label("Games Won:", game.skin)).padBottom(10).row();
        table.add(new Label(String.valueOf(user != null ? user.getGamesWon() : 0), game.skin))
            .padBottom(20).row();

        table.add(new Label("Current Level:", game.skin)).padBottom(10).row();
        table.add(new Label(String.valueOf(user != null ? user.getLevel() : 1), game.skin))
            .padBottom(20).row();

        TextButton backBtn = new TextButton("Back", game.skin);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        table.add(backBtn).width(200).height(50).padTop(40);

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
