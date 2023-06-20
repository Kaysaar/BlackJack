package com.duszki.blackjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Bet  {

    private float width;
    private float height;
    private float aspectRatio;
    private Table table;
    private Table table2;
    private Board board;
    private TextField textField;

    public Bet(Board board) {

        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;
        this.board = board;


        table2 = new Table();
        Skin skin = new Skin(Gdx.files.internal("balance/balance.json"));
        table2.setBackground(skin.getDrawable("balans"));
        table2.setSize(300, 200);
        Label label = new Label("", skin);
        table2.add(label).row();
        table2.setPosition(width - width / 5,720);

        Skin bet = new Skin(Gdx.files.internal("new_bet/new_bet.json"));
        table = new Table();
        table.setBackground(bet.getDrawable("back2_0"));
        table.setSize(300, 200);

        TextField textField = new TextField("", bet);
        table.add(textField).padTop(200.0f).row();
        table.setPosition(width - width / 5,500);

        Skin bet_button = new Skin(Gdx.files.internal("bet_button/bet_button.json"));
        ImageButton imageButton = new ImageButton(bet_button, "default");
        table.add(imageButton).padTop(40.0f).row();
        table.setPosition(width - width / 5,500);
    }

    public Table getTable() {
        return table;
    }
    public Table getTabl2(){return table2;}


}
