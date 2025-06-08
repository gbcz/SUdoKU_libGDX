package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LeaderboardScreen implements Screen {
    private final SudokuGame game;
    private final User user;
    private Stage stage;

    public LeaderboardScreen(SudokuGame game, User user) {
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

        Label leaderboard = new Label("Leaderboard", game.skin, "default");
        leaderboard.setColor(Color.ORANGE);
        Label person1 = new Label("RomanT, Score: 100", game.skin, "default");
        person1.setColor(Color.GRAY);
        Label person2 = new Label("RomanTe, Score: 53", game.skin, "default");
        person2.setColor(Color.GRAY);
        Label person3 = new Label("RomanTes, Score: 36", game.skin, "default");
        person3.setColor(Color.GRAY);
        Label person4 = new Label("RomanTest, Score: 10", game.skin, "default");
        person4.setColor(Color.GRAY);
        Label person5 = new Label("Roman, Score: 0", game.skin, "default");
        person5.setColor(Color.RED);

        table.add(leaderboard).padBottom(20).row();
        table.add(person1).padBottom(10).row();
        table.add(person2).padBottom(10).row();
        table.add(person3).padBottom(10).row();
        table.add(person4).padBottom(10).row();
        table.add(person5).padBottom(10).row();
        
        TextButton backBtn = new TextButton("Back", game.skin);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        table.add(backBtn).width(200).height(50).padTop(40).row();

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
