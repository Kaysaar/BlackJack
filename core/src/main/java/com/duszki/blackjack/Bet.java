package com.duszki.blackjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Bet  {

    private float width;
    private float height;
    private float aspectRatio;
    private Table table;
    private Board board;
    private TextField textField;
    private String string;

    public Bet(Board board) {

        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;
        this.board = board;


        final Skin[] bet = {new Skin(Gdx.files.internal("new_bet/new_bet.json"))};
        Skin bet_button = new Skin(Gdx.files.internal("bet_button/bet_button.json"));
        ImageButton imageButton = new ImageButton(bet_button, "default");
        table = new Table();
        table.padTop(150f);
        table.setBackground(bet[0].getDrawable("back2_0"));
        table.setSize(500, 300);

        TextField textField = new TextField("", bet[0]);
        table.add(textField).padRight(20f);
        imageButton.setSize(50,50);
        table.add(imageButton);
        table.setPosition(width/2 - 5 * imageButton.getWidth(),500 - 2*imageButton.getHeight());
        imageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                string = textField.getText();
                textField.setText("");
            }
        });

    }

    public Table getTable() {
        return table;
    }

    public String getBet(){
        return this.string;
    }



}
