package com.duszki.blackjack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.*;

public class Homepage extends ApplicationAdapter {
    private Texture homepage;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;

    @Override
    public void create() {
        homepage = new Texture("homepage.jpg");
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(homepage.getWidth(), homepage.getHeight(), camera);


    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 0);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(homepage, 0, 0);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport.apply();

    }

    @Override
    public void dispose() {
        batch.dispose();
        homepage.dispose();

    }
}
