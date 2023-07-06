package com.duszki.blackjack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.duszki.blackjack.shared.events.RequestCurrRankingEvent;

import java.util.ArrayList;

public class Leaderboard {
    private float width;
    private float height;
    private float aspectRatio;
    private Table table;
    private Board board;


    private ArrayList<Label> places = new ArrayList<>();

    //TODO Better way of transfering data

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

        places.add(new Label("1. ",skin)) ;
        places.add(new Label("2. ",skin)) ;
        places.add(new Label("3. ",skin)) ;
        places.add(new Label("4. ",skin)) ;
        places.add(new Label("5. ",skin)) ;

        table.add(places.get(0)).left().padBottom(10f).row();
        table.add(places.get(1)).left().padBottom(10f).row();
        table.add(places.get(2)).left().padBottom(10f).row();
        table.add(places.get(3)).left().padBottom(10f).row();
        table.add(places.get(4)).left().padBottom(10f).row();

    }

    public Table getTable() {
        return table;
    }

    public void  setPlace(String username , int coins , int place){

        String toPlace = place+". "+username;
        places.get(place-1).setText(toPlace);
    }
}
