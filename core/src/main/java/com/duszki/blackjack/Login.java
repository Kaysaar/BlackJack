package com.duszki.blackjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


//import java.net.InetAddress;
//import java.net.UnknownHostException;

import com.duszki.blackjack.shared.data.DataToTransfer;
import com.duszki.blackjack.shared.data.WinnersOfRound;
import com.duszki.blackjack.shared.events.*;
import com.duszki.blackjack.shared.models.Card;
import com.duszki.blackjack.shared.models.Hand;
import com.duszki.blackjack.shared.player.HandTransferData;
import com.duszki.blackjack.shared.player.PlayerTransferData;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.*;
import com.duszki.blackjack.shared.player.*;

import java.util.ArrayList;
import java.util.Date;

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


        // Client setup

        final Client client = new Client();
        registerClientClasses(client);


        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                String ip = textField2.getText();
                String port = textField3.getText();
                String Username = textField4.getText();
//                if(checkIpValid(ip) && isPortValid(port)){
//                    int p = Integer.parseInt(port);
//                    if(checkPortValid(p)){
//                        game.setScreen(new Loading(game));
//                    }
//                }
//                game.setScreen(new Loading(game));
                try {
                    client.start();
                    client.connect(5000, ip, Integer.parseInt(port));
                } catch (Exception e) {
                    e.printStackTrace();
                    textField2.setText("");
                    textField3.setText("");
                    textField4.setText("");
                }

//                Blackjack.getInstance().setClient(client);

                JoinRequestEvent joinRequestEvent = new JoinRequestEvent(Username);

                client.sendTCP(joinRequestEvent);

            }
        });

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof JoinResponseEvent) {
                    JoinResponseEvent joinResponseEvent = (JoinResponseEvent) object;
                    if (joinResponseEvent.getSuccess()) {
//                        Blackjack.getInstance().setPlayer(joinResponseEvent.getPlayer());
//                        Blackjack.getInstance().setTable(joinResponseEvent.getTable());

//                        Blackjack.getInstance().setClient(client);

                        NetworkManager.setClient(client);

                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new Loading(game));
                            }
                        });

//                        game.setScreen(new Loading(game));

                    } else {
                        textField2.setText("");
                        textField3.setText("");
                        textField4.setText("");
                    }
                }
            }
        });

    }

//    public boolean checkIpValid(String ip) {
//        try {
//            InetAddress inetAddress = InetAddress.getByName(ip);
//            return true;
//        } catch (UnknownHostException e) {
//            return false;
//        }
//    }
//    public boolean checkPortValid(int port) {
//
//        if(port>=0 && port <= 65535){
//            return true;
//        }
//        return false;
//    }
//
//    public boolean isPortValid(String port) {
//        try {
//            int portInt = Integer.parseInt(port);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }

    public Table getTable() {
        return table;
    }

    public void registerClientClasses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();

        kryo.register(String.class);
        kryo.register(Boolean.class);
//        kryo.register(reqPlayerData.class);
        kryo.register(Date.class);
//        kryo.register(Network.Ping.class);
        kryo.register(Card.class);
        kryo.register(Hand.class);
        kryo.register(ArrayList.class);
//        kryo.register(Network.increaseCash.class);
//        kryo.register(Network.increasePoints.class);
//        kryo.register(Network.decreaseCash.class);
//        kryo.register(Network.decreasePoints.class);
//        kryo.register(RequestType.class);


        kryo.register(JoinRequestEvent.class);
        kryo.register(JoinResponseEvent.class);
        kryo.register(DoubleDownEvent.class);
        kryo.register(EndTurnEvent.class);
        kryo.register(GameStartedEvent.class);
        kryo.register(NotValidatedToDoEvent.class);
        kryo.register(RequestGameStartEvent.class);
        kryo.register(StandEvent.class);
        kryo.register(YourTurnEvent.class);
        kryo.register(HandTransferData.class);
        kryo.register(PlayerTransferData.class);
        kryo.register(DataToTransfer.class);
        kryo.register(WinnersOfRound.class);
    }

}
