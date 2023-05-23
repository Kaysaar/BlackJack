package com.duszki.blackjack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Blackjack extends Game {
    private SpriteBatch batch;
    private Texture image;
    private Homepage homepage;



    @Override
    public void create() {

        homepage = new Homepage();
        setScreen(homepage);

    }
    @Override
    public void resize(int width, int height) {


    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
