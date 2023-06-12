package com.duszki.blackjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.*;

public class Setting {
    private Table table;
    private Skin skin;
    private Homepage homepage;

    public Setting(Homepage homepage){
        this.homepage = homepage;
        skin = new Skin(Gdx.files.internal("Setting/skins.json"));
        table = new Table();
        table.setBackground(skin.getDrawable("Back"));
        table.center();
        table.setSize(928, 566);
        Label label = new Label("Sound",skin);
        table.add(label).padBottom(30f).row();
        Slider slider = new Slider(0,100,25,false,skin);
        table.add(slider).padBottom(20f).row();
        label = new Label("Screen size",skin);
        table.add(label).padBottom(20f).row();
        ImageButton imageButton =new ImageButton(skin,"FullScreen");
        table.add(imageButton).size(200,50).padBottom(20f).row();

        ImageButton imageButton2 =new ImageButton(skin,"default");
        table.add(imageButton2).row();
        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                homepage.show();
            }
        });

    }
    public Table getTable() {
        return table;
    }

}
