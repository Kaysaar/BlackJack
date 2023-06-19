package com.duszki.blackjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class Loading implements Screen {
    private Game game;
    private Texture textureRegion;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;

   private float delaySeconds = 5.0f;
   private float elapsedTime = 0.0f;
   private float width;
   private float height;

   private float aspectRatio;


    public Loading(Game game){
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        height = 1000;
        width = height * aspectRatio;
        this.game = game;
        batch =new SpriteBatch();
        textureRegion = new Texture("Loading.png");
        camera =new OrthographicCamera();
        camera.position.set(width / 2f, height / 2f, 0);
        viewport = new FitViewport(width, height, camera);

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
        batch.draw(textureRegion,0,0, width, height);
        batch.end();
        elapsedTime += delta;
        if (elapsedTime >= delaySeconds) {
           game.setScreen(new  Board(game));
        }
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
