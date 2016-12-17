package com.knowledgehut.developments.dodgeem2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ScrollingBackground {

    private Sprite sprite;
    private float speed;
    private float y1, y2;
    private float GAME_SCALE_X;

    public ScrollingBackground(Texture texture, int speed){

        GAME_SCALE_X = (float)(Gdx.graphics.getWidth()) / (float)(DodgeEm2.WIDTH);

        this.speed = speed * GAME_SCALE_X;

        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth()*GAME_SCALE_X, Gdx.graphics.getHeight());

        y1 = 0;
        y2 = sprite.getHeight();
    }


    public void updateAndRender(float deltaTime, SpriteBatch spriteBatch){

        //this is necessary when using Orthographic Camera
        if(!sprite.isFlipY())sprite.flip(true,false);

        y1 += speed * deltaTime;
        y2 += speed * deltaTime;

        if(y1 >= 0) y2 = y1 - sprite.getHeight();
        if(y2 >= 0) y1 = y2 - sprite.getHeight();

        spriteBatch.draw(sprite, 0, y1, sprite.getWidth(), sprite.getHeight());
        spriteBatch.draw(sprite, 0, y2, sprite.getWidth(), sprite.getHeight());
    }


    public void dispose(){
        sprite.getTexture().dispose();
    }

    @SuppressWarnings("unused")
    public void update(float deltaTime){
        y1 -= speed * deltaTime;
        y2 -= speed * deltaTime;

        if(y1 + (sprite.getHeight() * GAME_SCALE_X) <=0) y1 = y2 + sprite.getHeight() * GAME_SCALE_X;
        if(y2 + (sprite.getHeight()* GAME_SCALE_X) <=0)  y2 = y1 + sprite.getHeight()* GAME_SCALE_X;
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
