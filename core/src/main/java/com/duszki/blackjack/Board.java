package com.duszki.blackjack;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class Board implements Screen {
    private Skin skin;
    private Stage stage;
    private Game game;
    private ArrayList<Texture> groupOfCards = new ArrayList<>();
    private boolean showCard;
    private static final float CARD_WIDTH = 143;
    private static final float CARD_HEIGHT = 200;
    private static final int CURRENT_CARD = 0;
    private static final int MAX_CARDS = 10;
    private ArrayList<Float> cardXList = new ArrayList<>();

    public Board(Game game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("Board/skin.json"));
        MyInputProcessor myInputProcessor = new MyInputProcessor();
        InputMultiplexer multiplexer = new InputMultiplexer(myInputProcessor, stage);
        Gdx.input.setInputProcessor(multiplexer);

        showCard = false;

        Table table = new Table();
        table.setBackground(skin.getDrawable("Board1728x1117"));
        table.padLeft(0.0f);
        table.padRight(100.0f);
        table.padTop(0.0f);
        table.padBottom(26.0f);
        table.align(Align.bottomRight);
        table.setFillParent(true);

        ImageButton imageButton = new ImageButton(skin, "Hit");
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCard = true;
                cardXList.add(CARD_WIDTH * cardXList.size());
            }
        });
        table.add(imageButton).padTop(0f).padBottom(50f).spaceBottom(0f).padRight(500f);

        table.row();
        imageButton = new ImageButton(skin, "Stand");
        table.add(imageButton).padTop(0f).padBottom(50f).spaceBottom(0f).padRight(500f);

        table.row();
        imageButton = new ImageButton(skin, "Double");
        table.add(imageButton).padTop(0f).padBottom(200f).padRight(500f);
        stage.addActor(table);

        for (int i = 0; i < MAX_CARDS; i++) {
            Texture card = new Texture("Cards/tyl.png");
            groupOfCards.add(card);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        if (showCard) {
            Batch batch = stage.getBatch();
            batch.begin();
            for (int i = 0; i < cardXList.size(); i++) {
                batch.draw(groupOfCards.get(CURRENT_CARD), cardXList.get(i), 0);
            }
            batch.end();
        }
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
        for (Texture card : groupOfCards) {
            card.dispose();
        }
    }
}
