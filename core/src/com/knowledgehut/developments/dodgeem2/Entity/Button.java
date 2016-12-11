package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class Button {
    private ImageButton sprite;
    private Boolean locked;
    private Boolean selected;
    private TextureRegion tmp;

    public Button(ImageButton sp, Boolean locked){
        this.sprite = sp;
        this.locked = locked;
        this.selected = false;
    }

    public Boolean isLocked(){
        return locked;
    }

    public ImageButton getImage(){
        return sprite;
    }

    public Boolean isSelected(){
        return selected;
    }
    public void setSelected(boolean value){
        this.selected = value;
    }

}
