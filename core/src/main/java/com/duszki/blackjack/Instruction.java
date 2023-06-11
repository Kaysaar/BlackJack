package com.duszki.blackjack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class Instruction {

    private Table table;
    private Skin skin;
    private Homepage homepage;
    public Instruction(Homepage homepage){

        this.homepage = homepage;
        skin = new Skin(Gdx.files.internal("Scene/Instruction1/Instruction1.json"));
        table = new Table();
        table.setBackground(skin.getDrawable("Instruction"));
        table.setSize(1366,566);
        table.align(Align.topRight);
        table.padRight(50);
        ImageButton imageButton = new ImageButton(skin, "Next");
        table.add(imageButton);

        imageButton.addListener(new ClickListener(){

            static int change = 0;
            @Override
            public void clicked(InputEvent event, float x, float y){

                if(change == 0)table.setBackground(skin.getDrawable("Instruction2"));
                if(change == 1)table.setBackground(skin.getDrawable("Instruction3"));
                if(change == 2) {
                    table.setBackground(skin.getDrawable("Instruction"));
                    change = -1;
                }
                change++;
            }
        });

        ImageButton imageButton2 = new ImageButton(skin, "Cancel");
        table.add(imageButton2);
        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                homepage.show();
            }
        });


        table.row();

    }
    public Table getTable() {
        return table;
    }

}
