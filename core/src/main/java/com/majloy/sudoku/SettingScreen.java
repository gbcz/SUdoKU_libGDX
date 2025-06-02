package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingScreen implements Screen {
    private final SudokuGame game;
    private Stage stage;
    private Preferences prefs;

    public SettingScreen(SudokuGame game) {
        this.game = game;
        this.prefs = Gdx.app.getPreferences("SudokuSettings");
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        final CheckBox soundCheckbox = new CheckBox(" Sound", game.skin);
        soundCheckbox.setChecked(prefs.getBoolean("soundEnabled", true));

        final SelectBox<String> themeSelectBox = new SelectBox<>(game.skin);
        themeSelectBox.setItems("Light", "Dark", "Blue");
        themeSelectBox.setSelected(prefs.getString("theme", "Light"));

        TextButton backButton = new TextButton("Back", game.skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                saveSettings(soundCheckbox.isChecked(), themeSelectBox.getSelected());
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        table.add(new Label("Settings", game.skin, "title")).padBottom(50).row();
        table.add(soundCheckbox).padBottom(30).row();
        table.add(new Label("Theme:", game.skin)).padBottom(10).row();
        table.add(themeSelectBox).width(200).padBottom(40).row();
        table.add(backButton).width(200).height(60);

        stage.addActor(table);
    }

    private void saveSettings(boolean soundEnabled, String theme) {
        prefs.putBoolean("soundEnabled", soundEnabled);
        prefs.putString("theme", theme);
        prefs.flush();
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
    }
}
