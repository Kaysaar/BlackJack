package com.duszki.blackjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
        label.setFontScale(2);
        table.add(label).padBottom(30f).row();
        Slider slider = new Slider(0,100,25,false,skin);
        slider.setValue(100);
        slider.addListener(new  ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(slider.getValue() == 0){
                    homepage.getMusic().setVolume(0);
                }
                if(slider.getValue() == 25){
                    homepage.getMusic().setVolume(0.25f);
                }
                if(slider.getValue() == 50){
                    homepage.getMusic().setVolume(0.5f);
                }
                if(slider.getValue() == 75){
                    homepage.getMusic().setVolume(0.75f);
                }
                if(slider.getValue() == 100){
                    homepage.getMusic().setVolume(1f);
                }
            }
        });

        table.add(slider).padBottom(20f).row();
        label = new Label("Screen size",skin);
        label.setFontScale(2);
        table.add(label).padBottom(20f).row();
        ImageButton imageButton =new ImageButton(skin,"FullScreen");
        imageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                if(Gdx.graphics.isFullscreen()){
                    Gdx.graphics.setWindowedMode(1280,720);
                    Gdx.input.setInputProcessor(homepage.getMultiplexer());
                }else {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    Gdx.input.setInputProcessor(homepage.getMultiplexer());
                }

            }
        });

        table.add(imageButton).size(300,70).padBottom(20f).row();

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
