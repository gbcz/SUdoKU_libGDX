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
        this.user = user;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Statistics", game.skin, "title");
        table.add(title).padBottom(50).row();

        table.add(new Label("Games Played:", game.skin)).padBottom(10).row();
        table.add(new Label(String.valueOf(user.getGamesPlayed()), game.skin)).padBottom(20).row();

        table.add(new Label("Games Won:", game.skin)).padBottom(10).row();
        table.add(new Label(String.valueOf(user.getGamesWon()), game.skin)).padBottom(20).row();

        table.add(new Label("Current Level:", game.skin)).padBottom(10).row();
        table.add(new Label(String.valueOf(user.getLevel()), game.skin)).padBottom(20).row();

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

    }
}
