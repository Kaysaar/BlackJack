package com.duszki.blackjack;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import com.duszki.blackjack.shared.data.*;
import com.duszki.blackjack.shared.events.*;
import com.duszki.blackjack.shared.models.Card;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import com.duszki.blackjack.shared.models.*;

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

    private ArrayList<UnrevealedCard> hand;


    private Client client;

    private int cardsInHand;

    private int cardsInDealer;

    private ArrayList<UnrevealedCard> dealer;

    private Hand dealerHand;

    private PlayerData yourData;

    private UnrevealedCard blank;


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

        Bet bet = new Bet(this);
        stage.addActor(bet.getTable());

        Balance balance = new Balance(this);
        stage.addActor(balance.getTable());
        blank = new UnrevealedCard("back");
        blank.setDealerAction(0);

        hand = new ArrayList<>();
        dealer = new ArrayList<>();

        cardsInHand = 0;

        cardsInDealer = 0;

        buttonHit = new ImageButton(skin, "Hit");
        buttonHit.setPosition(width - width / 5, 300);
        stage.addActor(buttonHit);
        buttonHit.setVisible(false);
        buttonHit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                HitEvent hitEvent = new HitEvent();
                client.sendTCP(hitEvent);
                buttonDouble.setVisible(false);

            }
        });

        buttonStand = new ImageButton(skin, "Stand");
        buttonStand.setPosition(width - width / 5, 200);

        stage.addActor(buttonStand);
        buttonStand.setVisible(false);

        buttonStand.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StandEvent standEvent = new StandEvent();
                client.sendTCP(standEvent);

            }
        });


        buttonDouble = new ImageButton(skin, "Double");
        buttonDouble.setPosition(width - width / 5, 100);

        stage.addActor(buttonDouble);
        buttonDouble.setVisible(false);

        buttonDouble.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DoubleDownEvent doubleEvent = new DoubleDownEvent();
                client.sendTCP(doubleEvent);
                buttonDouble.setVisible(false);

            }
        });


        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof RoundStartEvent) {

                    RoundStartEvent roundStartEvent = (RoundStartEvent) object;

                    GameUpdateData gameUpdateData = roundStartEvent.getGameUpdateData();

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    balance.setBalance(Integer.toString(gameUpdateData.getYourData().getTokens()));

                    Card card;

                    for (int i = 0; i < 2; i++) {

                        card = gameUpdateData.getYourData().getHand().getCardsInHand().get(i);
                        Card finalCard = card;
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                addCardBoard(finalCard.toString());
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        card = gameUpdateData.getDealerHand().getCardsInHand().get(i);

                        Card finalCard1 = card;

                        if(card.isHidden()) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    addCardforDealer("back");
                                }
                            });
                        } else {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    addCardforDealer(finalCard1.toString());
                                }
                            });
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
//                    buttonHit.setVisible(true);
//                    buttonDouble.setVisible(true);
//                    buttonStand.setVisible(true);
                }
            }

        });

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof GameUpdateData) {

                    GameUpdateData gameUpdateData = (GameUpdateData) object;

                    balance.setBalance(Integer.toString(gameUpdateData.getYourData().getTokens()));

                    Card card;

                    if (gameUpdateData.getYourData().getHand().getCardsInHand().size() > hand.size()) {
                        card = gameUpdateData.getYourData().getHand().getCardsInHand().get(hand.size());
                        Card finalCard = card;
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                addCardBoard(finalCard.toString());
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    if (gameUpdateData.getDealerHand().getCardsInHand().size() > dealer.size()) {

                        if(!gameUpdateData.getDealerHand().getCardsInHand().get(0).isHidden()) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("carddeck/carddeck/carddeck.atlas"));
                                    TextureAtlas.AtlasRegion region = atlas.findRegion(gameUpdateData.getDealerHand().getCardsInHand().get(0).toString());
                                    TextureRegionDrawable drawable = new TextureRegionDrawable(region);
                                    blank.getImage().setDrawable(drawable);

                                }
                            });
                        }

                        card = gameUpdateData.getDealerHand().getCardsInHand().get(dealer.size());
                        Card finalCard = card;
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                addCardforDealer(finalCard.toString());
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }


                }
            }

        });

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof RequestBetEvent) {

                    RequestBetEvent requestBetEvent = (RequestBetEvent) object;

                    buttonHit.setVisible(false);
                    buttonDouble.setVisible(false);
                    buttonStand.setVisible(false);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    removeCards();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            bet.getTable().setVisible(true);
                        }
                    });
                }
            }
        });

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof EndOfGameEvent) {
                    EndOfGameEvent endOfGameEvent = (EndOfGameEvent) object;
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            game.setScreen(new FinalScreen(game, endOfGameEvent.getUsername(), endOfGameEvent.getCoins()));
                        }
                    });

                }
            }
        });

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof YourTurnEvent) {
                    buttonHit.setVisible(true);
                    buttonDouble.setVisible(true);
                    buttonStand.setVisible(true);
                }
            }
        });


    }


    void addCardBoard(String card) {
        UnrevealedCard unrevealedCard = new UnrevealedCard(card);
        unrevealedCard.setAction(hand.size());
        stage.addActor(unrevealedCard.getImage());
        hand.add(unrevealedCard);

    }

    void addCardforDealer(String card) {

        UnrevealedCard unrevealedCard = new UnrevealedCard(card);
        unrevealedCard.setDealerAction(dealer.size());
        System.out.println(dealer.size());
        stage.addActor(unrevealedCard.getImage());
        dealer.add(unrevealedCard);

        if(card.equals("back")) {
            blank = unrevealedCard;
        }

    }


    void removeCards() {
        for (UnrevealedCard unrevealedCard : hand) {
            unrevealedCard.getImage().addAction(Actions.removeActor());
        }
        hand.clear();
        for (UnrevealedCard unrevealedCard : dealer) {
            unrevealedCard.getImage().addAction(Actions.removeActor());
        }
        dealer.clear();
        blank.getImage().addAction(Actions.removeActor());

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
