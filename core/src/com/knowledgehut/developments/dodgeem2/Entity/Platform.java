package com.knowledgehut.developments.dodgeem2.Entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;

public class Platform {
    private Vector2 position;
    private Vector2 velocity;
    private float scaledHeight;
    private float scaledSize;
    private Sprite sprite;

    public Platform(Texture texture, Vector2 position, Vector2 velocity){
        float GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);
        this.position = position;
        this.velocity = velocity.scl(GAME_SCALE_X);
        this.sprite = new Sprite(texture);
        scaledHeight = texture.getWidth() / texture.getHeight();
        this.scaledSize = texture.getWidth() * GAME_SCALE_X;
    }

    public void update() {
        position.add(velocity);
    }


    public void render(SpriteBatch spriteBatch) {

        //this is necessary when using Orthographic Camera
        if(!sprite.isFlipY())sprite.flip(false,true);

        spriteBatch.draw(sprite, position.x, position.y, scaledSize, scaledSize/scaledHeight);
    }


}
