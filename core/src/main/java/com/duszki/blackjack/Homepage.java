package com.duszki.blackjack;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.audio.Music;

public class Homepage implements Screen {


    private Game game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private ImageButton buttonPlay;
    private ImageButton buttonSetting;
    private ImageButton buttonHelp;
    private ImageButton buttonQuit;

    private float width;
    private float height;

    private float aspectRatio;


    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }

    private InputMultiplexer multiplexer;

    private Music music;

    public Music getMusic() {
        return music;
    }

    public Game getGame() {
        return game;
    }


    public Homepage(Game game) {
        this.game = game;

        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();

        height = 1000;
        width = height * aspectRatio;

        batch = new SpriteBatch();

        backgroundTexture = new Texture("homepage.jpg");
        camera = new OrthographicCamera();
//        camera.setToOrtho(false, width, height);

        viewport = new FitViewport(width, height, camera);

        camera.position.set(width / 2f, height / 2f, 0);

        viewport.apply();

        stage = new Stage(viewport);

        MyInputProcessor myInputProcessor = new MyInputProcessor();
        multiplexer = new InputMultiplexer(myInputProcessor, stage);

        Gdx.input.setInputProcessor(multiplexer);

        Instruction instruction = new Instruction(this);
        instruction.getTable().setPosition(((width - instruction.getTable().getWidth()) / 2f), 100);


        Login login = new Login(this, game);
        login.getTable().setPosition(((width - login.getTable().getWidth()) / 2f), 300);

        Setting setting2 = new Setting(this);
        setting2.getTable().setPosition(((width - setting2.getTable().getWidth()) / 2f), 300);


        // Music
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Kevin MacLeod - George Street Shuffle.ogg"));
        music.setLooping(true);
        music.play();

        // Button play
        Skin play = new Skin(Gdx.files.internal("skins/Play/PlayButton.json"));
        buttonPlay = new ImageButton(play);
        float buttonX = ((width - buttonPlay.getWidth()) / 2f);
        buttonPlay.setPosition(buttonX, 500);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(login.getTable());
            }
        });

        //Button setting
        Skin setting = new Skin(Gdx.files.internal("skins/Settings/SettingsButton.json"));
        buttonSetting = new ImageButton(setting);
        buttonSetting.setPosition(buttonX, 400);
        buttonSetting.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(setting2.getTable());
            }
        });


        //Buttton help
        Skin help = new Skin(Gdx.files.internal("skins/Help/HelpButton.json"));
        buttonHelp = new ImageButton(help);
        buttonHelp.setPosition(buttonX, 300);
        buttonHelp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(instruction.getTable());
            }
        });

        //Button quit
        Skin quit = new Skin(Gdx.files.internal("skins/Quit/QuitButton.json"));
        buttonQuit = new ImageButton(quit);
        buttonQuit.setPosition(buttonX, 200);
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        stage.clear();
        stage.addActor(buttonPlay);
        stage.addActor(buttonHelp);
        stage.addActor(buttonQuit);
        stage.addActor(buttonSetting);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(backgroundTexture, 0, 0,width,height);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);


//        if (!Gdx.graphics.isFullscreen()) {
//            float scale = Math.min(1600f / this.width, 1200f / this.height);
//            Array<Actor> actors = stage.getActors();
//            for (Actor actor : actors) {
//                actor.setScale(scale);
//            }
//        } else {
//            Array<Actor> actors = stage.getActors();
//            for (Actor actor : actors) {
//                actor.setScale(1);
//            }
//        }
//        camera.setToOrtho(false, width, height);
//        viewport.update(width, height, true);
//        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

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
