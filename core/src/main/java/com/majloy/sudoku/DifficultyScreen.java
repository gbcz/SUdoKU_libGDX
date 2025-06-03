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

public class DifficultyScreen implements Screen {
    private final SudokuGame game;
    private Stage stage;

    public DifficultyScreen(SudokuGame game) {
        this.game = game;
        game.getRenderer().setCurrentScreen(this);
    }

    @Override public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        game.getRenderer().setCurrentStage(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label title = new Label("Select Board Size", game.skin, "default");
        title.setAlignment(Align.center);

        TextButton boardSix = createBoardButton("6x6", 6);
        TextButton boardNine = createBoardButton("9x9", 9);
        TextButton boardTwelve = createBoardButton("12x12", 12);
        TextButton backButton = new TextButton("Back", game.skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        table.add(title).padBottom(50).row();
        table.add(boardSix).width(400).height(100).padBottom(20).row();
        table.add(boardNine).width(400).height(100).padBottom(20).row();
        table.add(boardTwelve).width(400).height(100).padBottom(40).row();
        table.add(backButton).width(200).height(60);

        stage.addActor(table);
    }

    private TextButton createBoardButton(String text, final int size) {
        TextButton button = new TextButton(text, game.skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DifficultyScreen.difficulty = size;
                game.setScreen(new DifficultyLevelScreen(game));
                dispose();
            }
        });
        return button;
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

    public static int difficulty;
}
