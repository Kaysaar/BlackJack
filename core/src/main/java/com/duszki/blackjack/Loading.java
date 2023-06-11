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


    public Loading(Game game){
        this.game = game;
        batch =new SpriteBatch();
        textureRegion = new Texture("Loading.png");
        camera =new OrthographicCamera();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(textureRegion.getWidth(), textureRegion.getHeight(), camera);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(textureRegion, 0, 0);
        batch.end();
        elapsedTime += delta;
        if (elapsedTime >= delaySeconds) {
           game.setScreen(new  Board(game));
        }
    }

    @Override
    public void resize(int width, int height) {
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
        batch.dispose();

    }
}
