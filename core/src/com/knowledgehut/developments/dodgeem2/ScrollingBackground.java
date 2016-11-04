package com.knowledgehut.developments.dodgeem2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {

    private Texture background;
    private Sprite sprite;
    private int speed;
    private float y1, y2;

    public ScrollingBackground(Texture texture, int speed){
        this.background = texture;
        this.speed = speed;

        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        sprite = new Sprite(background);

        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        y1 = 0;
        y2 = sprite.getHeight() * speed;
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

    public void updateAndRender(float deltaTime, SpriteBatch spriteBatch){

        //this is necessary when using Orthographic Camera
        if(!sprite.isFlipY())sprite.flip(true,false);

        y1 += speed * deltaTime;
        y2 += speed * deltaTime;

        if(y1 >= 0) y2 = y1 - sprite.getHeight();
        if(y2 >= 0) y1 = y2 - sprite.getHeight();

        spriteBatch.draw(sprite,0, y1, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.draw(sprite,0, y2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @SuppressWarnings("unused")
    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    public void dispose(){
        background.dispose();
    }
}
