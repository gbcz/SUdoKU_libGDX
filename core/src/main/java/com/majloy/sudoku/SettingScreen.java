package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

        Label title = new Label("Settings", game.skin, "title");
        table.add(title).padBottom(50).row();

        // Настройка звука
        final CheckBox soundCheckbox = new CheckBox(" Sound Effects", game.skin);
        soundCheckbox.setChecked(prefs.getBoolean("soundEnabled", true));
        table.add(soundCheckbox).padBottom(20).row();

        // Выбор темы
        final SelectBox<String> themeSelect = new SelectBox<>(game.skin);
        themeSelect.setItems("Classic", "Dark", "Colorful");
        themeSelect.setSelected(prefs.getString("theme", "Classic"));
        table.add(new Label("Theme:", game.skin)).padBottom(5).row();
        table.add(themeSelect).width(300).padBottom(30).row();

        // Кнопки
        TextButton applyBtn = new TextButton("Apply", game.skin);
        TextButton backBtn = new TextButton("Back", game.skin);

        applyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveSettings(soundCheckbox.isChecked(), themeSelect.getSelected());
                Dialog appliedDialog = new Dialog("Info", game.skin);
                appliedDialog.text("Settings applied!");
                appliedDialog.button("OK");
                appliedDialog.show(stage);
            }
        });

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        table.add(applyBtn).width(200).height(50).padBottom(15).row();
        table.add(backBtn).width(200).height(50);

        stage.addActor(table);
    }

    private void saveSettings(boolean soundEnabled, String theme) {
        prefs.putBoolean("soundEnabled", soundEnabled);
        prefs.putString("theme", theme);
        prefs.flush();
        game.applySettings(); // Применяем настройки в игре
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
