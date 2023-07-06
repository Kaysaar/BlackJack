package com.duszki.blackjack;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

public class UnrevealedCard {


    private Image image;
    private MoveToAction moveToAction;
    private float aspectRatio;
    private float height;
    private float width;
    private Texture texture;


    public UnrevealedCard(String name){
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;
        texture = new Texture(Gdx.files.internal("Cards/Back.png"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("carddeck/carddeck/carddeck.atlas"));
        TextureAtlas.AtlasRegion region = atlas.findRegion(name);

        image = new Image(region);
        image.setScale(0.3f);
        image.setPosition(width/2 + width/4,1200);
        moveToAction = new MoveToAction();

    }


    public void setAction(int NumOfCards){
        int shift = 35;
        moveToAction.setPosition(width/2 + shift * NumOfCards - width/15,50 );
        moveToAction.setDuration(1);
        moveToAction.setInterpolation(Interpolation.smooth);
        image.addAction(moveToAction);
    }


    public void setDealerAction(int NumOfCards){
        int shift = 35;
        moveToAction.setPosition(width/2 + shift * NumOfCards - width/15,780 );
        moveToAction.setDuration(1);
        moveToAction.setInterpolation(Interpolation.smooth);
        image.addAction(moveToAction);

    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }





}
