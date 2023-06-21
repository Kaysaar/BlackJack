package com.duszki.blackjack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class Balance {
    private float width;
    private float height;
    private float aspectRatio;
    private Table table;
    private Board board;
    private Label balance;

    public Balance(Board board){
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;
        this.board = board;

        Skin skin = new Skin(Gdx.files.internal("Login/skins.json"));

        table = new Table();
        table.setBackground(skin.getDrawable("Login"));
        table.setSize(300,200);
        table.setPosition(width - width/5,height - 300);
        table.padRight(50f);

        Label label = new Label("Balance: ", skin);
        table.add(label).padRight(20f);

        balance = new Label("2000",skin);
        table.add(balance);


    }

    public Table getTable() {
        return table;
    }

    public Label getBalance() {
        return balance;
    }

    public void setBalance(String balance){
        this.balance.setText(balance);
    }


}
