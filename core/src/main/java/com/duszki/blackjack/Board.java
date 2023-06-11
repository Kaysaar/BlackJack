package com.duszki.blackjack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Board implements Screen {
    private Skin skin;
    private Stage stage;
    private Game game;

    public Board(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("Board/skin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setBackground(skin.getDrawable("Board1728x1117"));
        table.padLeft(0.0f);
        table.padRight(100.0f);
        table.padTop(0.0f);
        table.padBottom(26.0f);
        table.align(Align.bottomRight);
        table.setFillParent(true);

        ImageButton imageButton = new ImageButton(skin, "Hit");
        table.add(imageButton).padTop(0f).padBottom(50f).spaceBottom(0f).padRight(500f);

        table.row();
        imageButton = new ImageButton(skin, "Stand");
        table.add(imageButton).padTop(0f).padBottom(50f).spaceBottom(0f).padRight(500f);

        table.row();
        imageButton = new ImageButton(skin, "Double");
        table.add(imageButton).padTop(0f).padBottom(200f).padRight(500f);
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

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

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
