package com.duszki.blackjack;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Points implements Screen {
    private Skin skin;
    private Stage stage;
    private Game game;

    private float width;
    private float height;
    private float aspectRatio;

    public Points(Game game) {
        this.game = game;
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;

        stage = new Stage(new FitViewport(width, height));
        skin = new Skin(Gdx.files.internal("Board/skin.json"));
        MyInputProcessor myInputProcessor = new MyInputProcessor();
        InputMultiplexer multiplexer = new InputMultiplexer(myInputProcessor,stage);
        Gdx.input.setInputProcessor(multiplexer);


        Table table = new Table();
        table.setBackground(skin.getDrawable("Board1728x1117"));
        table.padLeft(0.0f);
        table.padRight(100.0f);
        table.padTop(0.0f);
        table.padBottom(26.0f);
        table.align(Align.bottomRight);
        table.setFillParent(true);

        ImageButton imageButton = new ImageButton(skin, "Hit");
        table.add(imageButton).padTop(0f).padBottom(50f).spaceBottom(0f).padRight(100f);

        table.row();
        imageButton = new ImageButton(skin, "Stand");
        table.add(imageButton).padTop(0f).padBottom(50f).spaceBottom(0f).padRight(100f);

        table.row();
        imageButton = new ImageButton(skin, "Double");
        table.add(imageButton).padTop(0f).padBottom(70f).padRight(100f);
        table.row();

        Table table2 = new Table();
        skin = new Skin(Gdx.files.internal("balance/balance.json"));
        table2.setBackground(skin.getDrawable("balans"));
        Label label = new Label("", skin);
        table2.add(label).padTop(40f).row();
        table.add(table2);
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
