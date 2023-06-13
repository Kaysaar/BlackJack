package com.duszki.blackjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Login {
    private Table table;
    private Game game;
    private Homepage homepage;
    private Skin skin;

    public Login(Homepage homepage, Game game) {

        this.homepage = homepage;
        this.game = game;
        table = new Table();
        skin = new Skin(Gdx.files.internal("Login/skins.json"));
        table.setBackground(skin.getDrawable("Login"));
        table.setSize(928, 566);
        Label label = new Label("Log in", skin);
        table.add(label).padBottom(50f).row();
        label = new Label("Ip address", skin);
        table.add(label).padBottom(10f).row();
        TextField textField2 = new TextField("", skin);
        table.add(textField2).padBottom(10f).row();
        label = new Label("Port", skin);
        table.add(label).padBottom(10f).row();
        TextField textField3 = new TextField("", skin);
        table.add(textField3).padBottom(10f).row();
        label = new Label("Username", skin);
        table.add(label).padBottom(10f).row();
        TextField textField4 = new TextField("", skin);
        table.add(textField4).padBottom(20f).row();
        ImageButton imageButton = new ImageButton(skin, "Next");
        table.add(imageButton).row();
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                String ip = textField2.getText();
                String port = textField3.getText();
                String Username = textField4.getText();
                if(checkIpValid(ip) && isPortValid(port)){
                    int p = Integer.parseInt(port);
                    if(checkPortValid(p)){
                        game.setScreen(new Loading(game));
                    }
                }
                textField2.setText("");
                textField3.setText("");
                textField4.setText("");

            }
        });

        ImageButton imageButton2 = new ImageButton(skin, "Cancel");
        table.add(imageButton2).row();
        imageButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                textField2.setText("");
                textField3.setText("");
                textField4.setText("");
                homepage.show();
            }
        });

    }

    public boolean checkIpValid(String ip) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
    public boolean checkPortValid(int port) {

        if(port>=0 && port <= 65535){
            return true;
        }
        return false;
    }

    public boolean isPortValid(String port) {
        try {
            int portInt = Integer.parseInt(port);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Table getTable() {
        return table;
    }

}
