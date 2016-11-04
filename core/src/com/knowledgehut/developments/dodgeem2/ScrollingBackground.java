package com.knowledgehut.developments.dodgeem2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Console;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;

public class ScrollingBackground {

    private Sprite sprite;
    private int speed;
    private float y1, y2;
    private float scale;

    public ScrollingBackground(Texture texture, int speed){

        float GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);
        this.speed = speed;
        scale = texture.getWidth() * GAME_SCALE_X;

        sprite = new Sprite(texture);

        y1 = 0;
        y2 = sprite.getHeight()  * speed;
    }


    public void updateAndRender(float deltaTime, SpriteBatch spriteBatch){

        //this is necessary when using Orthographic Camera
        if(!sprite.isFlipY())sprite.flip(true,false);

        y1 += speed * deltaTime;
        y2 += speed * deltaTime;

        if(y1 >= 0) y2 = y1 - sprite.getHeight();
        if(y2 >= 0) y1 = y2 - sprite.getHeight();

        spriteBatch.draw(sprite, 0, y1, scale, scale);
        spriteBatch.draw(sprite, 0, y2, scale, scale);
    }


    public void dispose(){
        sprite.getTexture().dispose();
    }

    @SuppressWarnings("unused")
    public void update(float deltaTime){
        y1 -= speed * deltaTime;
        y2 -= speed * deltaTime;

        if(y1 + sprite.getHeight() <=0) y1 = y2 + sprite.getHeight();
        if(y2 + sprite.getHeight() <=0) y2 = y1 + sprite.getHeight();
    }

    @SuppressWarnings("unused")
    public void render(SpriteBatch spriteBatch){
        spriteBatch.draw(sprite,0, y1);
        spriteBatch.draw(sprite,0, y2);
    }

    @SuppressWarnings("unused")
    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }
}
