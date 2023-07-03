package com.duszki.blackjack;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.duszki.blackjack.shared.events.GameStartedEvent;
import com.duszki.blackjack.shared.events.RequestCurrRankingEvent;
import com.duszki.blackjack.shared.events.RequestGameStartEvent;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Loading implements Screen {
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

    private ImageButton startGameButton;

    private Skin startButtonSkin;

    private Stage stage;

    private InputMultiplexer multiplexer;

    private Leaderboard leaderboard;

    Client client;


    public Loading(Game game) {
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
        startButtonSkin = new Skin(Gdx.files.internal("skins/Play/PlayButton.json"));
        startGameButton = new ImageButton(startButtonSkin);

        startGameButton.setPosition(width / 2f - startGameButton.getWidth() / 2f, 100);

        stage.addActor(startGameButton);

        MyInputProcessor inputProcessor = new MyInputProcessor();

        multiplexer = new InputMultiplexer(inputProcessor, stage);

        Gdx.input.setInputProcessor(multiplexer);

        leaderboard = new Leaderboard();
        stage.addActor(leaderboard.getTable());


        client = NetworkManager.getClient();

        players = new HashMap<>();

        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                RequestGameStartEvent requestGameStartEvent = new RequestGameStartEvent();
                client.sendTCP(requestGameStartEvent);

            }
        });

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof GameStartedEvent) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new Board(game));
                        }
                    });

                }


            }
        });
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof RequestCurrRankingEvent) {
                    RequestCurrRankingEvent iterator = (RequestCurrRankingEvent)object;
                    int i=1;
                    for (Map.Entry<String, Integer> stringIntegerEntry : iterator.getPlayersSorted().entrySet()) {
                        leaderboard.setPlace(stringIntegerEntry.getKey(),stringIntegerEntry.getValue(),i);
                        i++;
                    }
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new Board(game));
                        }
                    });

                }


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

    public void updatePlayersInLobby(){
        RequestCurrRankingEvent requestCurrRankingEvent = new RequestCurrRankingEvent();
        this.players =  requestCurrRankingEvent.getPlayersSorted();
    }


}
