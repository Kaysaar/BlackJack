package com.duszki.blackjack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayList;

public class FinalScoreCongratulations {
    private float width;
    private float height;
    private float aspectRatio;
    private Table table;
    private Board board;

    private String username;
    private int coins;

    private Label usernameLabel;
    private Label coinsLabel;



    public FinalScoreCongratulations(String username, int coins){
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;
       // this.board = board;

        Skin skin = new Skin(Gdx.files.internal("Login/skins.json"));

        table = new Table();
        table.setBackground(skin.getDrawable("Login"));
        table.setSize(width/2,400);
        table.align(Align.left);
        table.setPosition((width - table.getWidth())/2f,200);
        table.padLeft(100f);

        String congratualtions = new StringBuilder("Congratulations ").append(username).append("!").toString();

        usernameLabel = new Label(congratualtions,skin);

        String yourWinnings = new StringBuilder("Your winnings: ").append(coins).append(" coins").toString();

        coinsLabel = new Label(yourWinnings,skin);

        table.add(usernameLabel).padBottom(10f).row();

        table.add(coinsLabel).padBottom(10f).row();



    }

    public Table getTable() {
        return table;
    }

}
