package com.duszki.blackjack;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class Board implements Screen {
    private Skin skin;
    private Stage stage;
    private Game game;

    private float width;
    private float height;
    private float aspectRatio;

    private SpriteBatch batch;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private ArrayList<UnrevealedCard> Hand;



    public Board(Game game) {
        this.game = game;
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;

        batch = new SpriteBatch();

        backgroundTexture = new Texture("Board1728x1117.png");

        camera = new OrthographicCamera();

        camera.position.set(width / 2f, height / 2f, 0);
        viewport = new FitViewport(width, height, camera);

        viewport.apply();

        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("Board/skin.json"));
        MyInputProcessor myInputProcessor = new MyInputProcessor();
        InputMultiplexer multiplexer = new InputMultiplexer(myInputProcessor,stage);
        Gdx.input.setInputProcessor(multiplexer);

        Hand = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            addCard();
        }


        ImageButton imageButton = new ImageButton(skin, "Hit");
        imageButton.setPosition(width - width/5,300);
        stage.addActor(imageButton);
        imageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addCard();
            }
        });

        imageButton = new ImageButton(skin, "Stand");
        imageButton.setPosition(width-width/5,200);
        stage.addActor(imageButton);
        imageButton = new ImageButton(skin, "Double");
        imageButton.setPosition(width -width/5,100);
        stage.addActor(imageButton);
    }

    void addCard(){
        UnrevealedCard unrevealedCard = new UnrevealedCard();
        unrevealedCard.setAction(Hand.size());
        stage.addActor(unrevealedCard.getImage());
        Hand.add(unrevealedCard);


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0,width,height);
        batch.end();
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
