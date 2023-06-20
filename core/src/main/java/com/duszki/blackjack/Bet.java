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
        textField = new TextField("", bet);
        table.add(textField).padTop(70.0f);
        table.setPosition(width - width / 5,500);

    }

    public Table getTable() {
        return table;
    }



}
