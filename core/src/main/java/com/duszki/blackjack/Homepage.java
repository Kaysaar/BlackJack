package com.duszki.blackjack;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Audio.*;
import com.badlogic.gdx.Gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


import java.awt.*;

public class Homepage implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private ImageButton buttonPlay;
    private ImageButton buttonSetting;
    private ImageButton buttonHelp;
    private ImageButton ins1;
    private ImageButton buttonQuit;


    public Homepage() {

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = new Texture("homepage.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(backgroundTexture.getWidth(), backgroundTexture.getHeight(), camera);

        // Button play
        TextureRegionDrawable play = new TextureRegionDrawable(new Texture("skins/Play.png"));
        buttonPlay = new ImageButton(play);
        float buttonX = ((Gdx.graphics.getWidth() - buttonPlay.getWidth()) / 2f);
        buttonPlay.setPosition(buttonX,500);

        //Button setting
        TextureRegionDrawable  setting = new TextureRegionDrawable(new Texture("skins/Setting.png"));
        buttonSetting = new ImageButton(setting);
        buttonSetting.setPosition(buttonX,400);

        //Buttton help
        TextureRegionDrawable  help = new TextureRegionDrawable(new Texture("skins/Help.png"));
        buttonHelp = new ImageButton(help);
        buttonHelp.setPosition(buttonX,300);

        TextureRegionDrawable  instruction1 = new TextureRegionDrawable(new Texture("skins/Insttruction1.png"));
        ins1 = new ImageButton(instruction1);
        ins1.setPosition(((Gdx.graphics.getWidth() - ins1.getWidth()) / 2f),400);

        //Button quit
        TextureRegionDrawable quit = new TextureRegionDrawable(new Texture("skins/Quit.png"));
        buttonQuit = new ImageButton(quit);
        buttonQuit.setPosition(buttonX,200);

        stage.addActor(buttonPlay);
        stage.addActor(buttonHelp);
        stage.addActor(buttonQuit);
        stage.addActor(buttonSetting);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

            }
        });
        buttonSetting.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.clear();
            }
        });

        buttonHelp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.clear();
                stage.addActor(ins1);
                stage.act();

            }
        });

        buttonQuit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        camera.setToOrtho(false, width, height);
        viewport.update(width, height, true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport.apply();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
