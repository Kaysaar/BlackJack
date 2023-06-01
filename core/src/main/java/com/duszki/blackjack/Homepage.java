package com.duszki.blackjack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.audio.Music;

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

    private Music music;

    public Homepage() {

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = new Texture("homepage.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(backgroundTexture.getWidth(), backgroundTexture.getHeight(), camera);

        // Music
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Kevin MacLeod - George Street Shuffle.ogg"));
        music.setLooping(true);
        music.play();

        // Button play
        Skin play = new Skin(Gdx.files.internal("skins/Play/PlayButton.json"));
        buttonPlay = new ImageButton(play);
        float buttonX = ((Gdx.graphics.getWidth() - buttonPlay.getWidth()) / 2f);
        buttonPlay.setPosition(buttonX,500);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

            }
        });

        //Button setting
        Skin setting = new Skin(Gdx.files.internal("skins/Settings/SettingsButton.json"));
        buttonSetting = new ImageButton(setting);
        buttonSetting.setPosition(buttonX,400);
        buttonSetting.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.clear();
            }
        });

        //Buttton help
        Skin help = new Skin(Gdx.files.internal("skins/Help/HelpButton.json"));
        buttonHelp = new ImageButton(help);
        buttonHelp.setPosition(buttonX,300);
        buttonHelp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

            }
        });

        //Button quit
        Skin quit = new Skin(Gdx.files.internal("skins/Quit/QuitButton.json"));
        buttonQuit = new ImageButton(quit);
        buttonQuit.setPosition(buttonX,200);

        buttonQuit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

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
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        music.dispose();
    }
}
