package com.duszki.blackjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class bet implements Screen {
    private Skin skin;
    private Stage stage;
    private Game game;

    private float width;
    private float height;
    private float aspectRatio;

    public bet(Game game) {
        this.game = game;
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;

        stage = new Stage(new FitViewport(width, height));
        skin = new Skin(Gdx.files.internal("Board/skin.json"));
        MyInputProcessor myInputProcessor = new MyInputProcessor();
        InputMultiplexer multiplexer = new InputMultiplexer(myInputProcessor, stage);
        Gdx.input.setInputProcessor(multiplexer);

        Table table = new Table();
        table.setBackground(skin.getDrawable("Board1728x1117"));
        table.padLeft(0.0f);
        table.padRight(100.0f);
        table.padTop(0.0f);
        table.padBottom(26.0f);
        table.align(Align.bottomRight);
        table.setFillParent(true);

        Skin bet = new Skin(Gdx.files.internal("new_bet/new_bet.json"));
        Table table2 = new Table();
        table2.setBackground(bet.getDrawable("back2_0"));
        table2.setSize(200, 200);
        TextField textField = new TextField("", bet);
        table2.add(textField).padTop(70.0f);
        table.add(table2);


//        Skin back_button = new Skin(Gdx.files.internal("back_button/back_button.json"));
//        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
//        buttonStyle.up = back_button.getDrawable("back2");
//        buttonStyle.down = back_button.getDrawable("back_button2");
//        buttonStyle.over = back_button.getDrawable("back_button");
//        ImageButton imageButton = new ImageButton(buttonStyle);
//
//        Skin lab = new Skin(Gdx.files.internal("bet/bet.json"));
//        TextField textField = new TextField("", lab);
//
//        Stack stack = new Stack();
//        stack.add(imageButton);
//        stack.add(textField);
//
//        Table stackTable = new Table();
//        stackTable.add(stack).size(imageButton.getWidth(), imageButton.getHeight());
//
//        table.add(stackTable).padBottom(300f);
//        table.row();
//

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
