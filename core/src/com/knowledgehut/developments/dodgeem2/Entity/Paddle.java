package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


public class Paddle {

    private Texture texture;
    private Vector2 position;
    //private Vector2 velocity;
    private float scaledSize;
    private Sprite sprite;
    //private float circleX, circleY, radius;
    //private Circle circle;
    private float paddleDivision;

    public Paddle(Texture texture, Vector2 vector2){

        float GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);
        this.texture = texture;
        this.position = vector2;
        //this.velocity = velocity.scl(GAME_SCALE_X);
        this.scaledSize = texture.getWidth() * GAME_SCALE_X;

        this.sprite = new Sprite(this.texture);
        paddleDivision = texture.getWidth() / texture.getHeight();
    }

    public void update(Vector2 playerPosition) {
        //position.add(velocity);
        position.x = playerPosition.x;
        position.y = playerPosition.y;
    }


    public void render(SpriteBatch spriteBatch) {

        //this is necessary when using Orthographic Camera
        if(!sprite.isFlipY())sprite.flip(false,true);
        spriteBatch.draw(sprite, position.x, position.y + scaledSize - (scaledSize/paddleDivision), scaledSize, (scaledSize/paddleDivision));

       // spriteBatch.draw(sprite, position.x, position.y, scaledSize, scaledSize);
    }


    public float getScale(){
        return scaledSize;
    }
    public float getScaledSize(){ return scaledSize;}

    public Vector2 getPosition() {
        return position;
    }


    public void setPosition(Vector2 newPosition){
        this.position = newPosition;
    }


    public void dispose() {
        texture.dispose();
    }
}
