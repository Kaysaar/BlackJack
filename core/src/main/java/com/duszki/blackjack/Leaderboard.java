package com.duszki.blackjack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class Leaderboard {
    private float width;
    private float height;
    private float aspectRatio;
    private Table table;
    private Board board;


    private Label firstPlace;
    private Label secondPlace;
    private Label thirdPlace;
    private Label forthPlace;
    private Label fifthPlace;

    public Leaderboard(){
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

        firstPlace = new Label("1. ",skin);
        secondPlace = new Label("2. ",skin);
        thirdPlace = new Label("3. ",skin);
        forthPlace = new Label("4. ",skin);
        fifthPlace = new Label("5. ",skin);

        table.add(firstPlace).padBottom(10f).row();
        table.add(secondPlace).padBottom(10f).row();
        table.add(thirdPlace).padBottom(10f).row();
        table.add(forthPlace).padBottom(10f).row();
        table.add(fifthPlace).padBottom(10f).row();
    }

    public Table getTable() {
        return table;
    }

    void setFirstPlace(String username){
        fifthPlace.setText("1. " + username);
    }
    void setSecondPlace(String username){
        fifthPlace.setText("2. " + username);
    }
    void setThirdPlace(String username){
        fifthPlace.setText("3. " + username);
    }
    void setForthPlace(String username){
        fifthPlace.setText("4. " + username);
    }
    void setFifthPlace(String username){
        fifthPlace.setText("5. " + username);
    }




}
