package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private CheckBox soundCheckbox;
    private SelectBox<String> themeSelectBox;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.add(new Label("Настройки", skin)).row();
        table.add(soundCheckbox = new CheckBox("Звук", skin)).row();
        table.add(new Label("Тема", skin));
        table.add(themeSelectBox = new SelectBox<>(skin)).row();
        //Добавьте кнопку "Сохранить"
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
