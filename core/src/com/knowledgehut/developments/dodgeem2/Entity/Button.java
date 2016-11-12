package com.knowledgehut.developments.dodgeem2.Entity;


import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button {
    private Sprite sprite;
    private Boolean locked;

    public Button(Sprite sp, Boolean locked){
        this.sprite = sp;
        this.locked = locked;
    }

    public Boolean isLocked(){
        return locked;
    }

    public Sprite getSprite(){
        return sprite;
    }
}
