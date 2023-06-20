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
    private Board board;
    private TextField textField;

    public Bet(Board board) {

        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;
        this.board = board;

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



}
