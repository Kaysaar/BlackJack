package com.duszki.blackjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.duszki.blackjack.shared.events.GameStartedEvent;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.HashMap;


public class FinalScreen implements Screen {
    private Game game;
    private Texture textureRegion;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private HashMap<String, Integer> players;

    private float delaySeconds = 5.0f;
    private float elapsedTime = 0.0f;
    private float width;
    private float height;

    private float aspectRatio;

    private ImageButton exitButton;

    private Skin exitButtonSkin;

    private Stage stage;

    private InputMultiplexer multiplexer;

    private FinalScoreCongratulations finalScoreCongratulations;

    Client client;


    public FinalScreen(Game game, String username, int score) {
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;
        this.game = game;
        batch = new SpriteBatch();
        textureRegion = new Texture("homepage.jpg");
        camera = new OrthographicCamera();
        camera.position.set(width / 2f, height / 2f, 0);
        viewport = new FitViewport(width, height, camera);

        stage = new Stage(viewport);
        exitButtonSkin = new Skin(Gdx.files.internal("skins/Quit/QuitButton.json"));
        exitButton = new ImageButton(exitButtonSkin);

        exitButton.setPosition(width / 2f - exitButton.getWidth() / 2f, 100);

        stage.addActor(exitButton);

        MyInputProcessor inputProcessor = new MyInputProcessor();

        multiplexer = new InputMultiplexer(inputProcessor, stage);

        Gdx.input.setInputProcessor(multiplexer);

        finalScoreCongratulations = new FinalScoreCongratulations(username, score);

        stage.addActor(finalScoreCongratulations.getTable());


        client = NetworkManager.getClient();

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new Homepage(game));

            }
        });


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(textureRegion, 0, 0, width, height);
        batch.end();
        stage.draw();
        stage.act();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        batch.dispose();

    }

}
