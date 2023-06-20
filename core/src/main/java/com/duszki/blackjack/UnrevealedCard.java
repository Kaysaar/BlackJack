package com.duszki.blackjack;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
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


    public UnrevealedCard(){
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;
        texture = new Texture(Gdx.files.internal("Cards/Back.png"));
        image = new Image(texture);
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


    public Image getImage() {
        return image;
    }

    public MoveToAction getMoveToAction() {
        return moveToAction;
    }




}
