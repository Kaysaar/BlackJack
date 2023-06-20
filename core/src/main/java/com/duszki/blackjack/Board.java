package com.duszki.blackjack;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

import java.awt.*;
import java.util.ArrayList;

import com.duszki.blackjack.shared.data.*;
import com.duszki.blackjack.shared.events.DoubleDownEvent;
import com.duszki.blackjack.shared.events.HitEvent;
import com.duszki.blackjack.shared.events.StandEvent;
import com.duszki.blackjack.shared.models.Card;
import com.duszki.blackjack.shared.player.PlayerTransferData;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class Board implements Screen {
    private static final boolean DEBUG = true;
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

    ImageButton buttonHit;
    ImageButton buttonStand;
    ImageButton buttonDouble;

    private ArrayList<UnrevealedCard> Hand;

    private DataToTransfer currentGameState;

    private Client client;

    private int cardsInHand;

    private ArrayList<UnrevealedCard> Dealer;

    public Board(Game game) {

        if (DEBUG) {
//            Log.ERROR();
//            Log.WARN();
//            Log.INFO();
            Log.DEBUG();
//            Log.TRACE();
        }

        this.client = NetworkManager.getClient();

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
        InputMultiplexer multiplexer = new InputMultiplexer(myInputProcessor, stage);
        Gdx.input.setInputProcessor(multiplexer);


        Hand = new ArrayList<>();
        Dealer = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
                addCardforDealer();
        }

        cardsInHand = 0;

//        for (int i = 0; i < 2; i++) {
//            addCard();
//        }


        buttonHit = new ImageButton(skin, "Hit");
        buttonHit.setPosition(width - width / 5, 300);
        stage.addActor(buttonHit);
        buttonHit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                HitEvent hitEvent = new HitEvent();
                client.sendTCP(hitEvent);

            }
        });

        buttonStand = new ImageButton(skin, "Stand");
        buttonStand.setPosition(width - width / 5, 200);
        stage.addActor(buttonStand);

        buttonStand.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StandEvent standEvent = new StandEvent();
                client.sendTCP(standEvent);
            }
        });


        buttonStand = new ImageButton(skin, "Double");
        buttonStand.setPosition(width - width / 5, 100);
        stage.addActor(buttonStand);

        buttonDouble.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DoubleDownEvent doubleEvent = new DoubleDownEvent();
                client.sendTCP(doubleEvent);
            }
        });

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof PlayerTransferData) {
                    PlayerTransferData request = (PlayerTransferData) object;

                    ArrayList<Card> cards = request.getCards();
                    if (cards.size() == 2) {
                        cardsInHand = cards.size();
                        for (Card card : cards) {
                            System.out.println(card.toString());
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    addCardBoard();
                                }
                            });

                        }
                    } else {
                        for (int i = cardsInHand; i < cards.size(); i++) {
                            System.out.println(cards.get(i).toString());
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    addCardBoard();
                                }
                            });

                            cardsInHand++;

                        }

                    }
                }


            }

        });

    }


    void addCardBoard(){
        UnrevealedCard unrevealedCard = new UnrevealedCard("10_of_clubs");
        unrevealedCard.setAction(Hand.size());
        stage.addActor(unrevealedCard.getImage());
        Hand.add(unrevealedCard);

    }

    void addCardforDealer(){
        UnrevealedCard unrevealedCard;
        if(Dealer.size() == 0) {
            unrevealedCard = new UnrevealedCard("back");
        }else {
            unrevealedCard = new UnrevealedCard("10_of_clubs");
        }

        unrevealedCard.setDealerAction(Dealer.size());
        stage.addActor(unrevealedCard.getImage());
        Dealer.add(unrevealedCard);
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
        batch.draw(backgroundTexture, 0, 0, width, height);
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
