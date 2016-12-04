package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


public class Paddle {

    private Texture texture;
    private Vector2 velocity;
    private Vector2 position;
    private float scaledSize;
    private Sprite sprite;
    private float paddleDivision;
    private boolean pause;
    private boolean isFalling;

    public Paddle(Texture texture, Vector2 vector2){

        float GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);
        this.texture = texture;
        this.position = vector2;
        this.scaledSize = texture.getWidth() * GAME_SCALE_X;
        velocity = new Vector2(0,0);
        this.sprite = new Sprite(this.texture);
        paddleDivision = texture.getWidth() / texture.getHeight();
        pause = isFalling = false;
    }

    public void update(Vector2 playerPosition, Vector2 platformPosition) {
        if(!pause) {
            position.add(velocity);
            position.x = playerPosition.x;

            if(!isFalling)
            position.y = platformPosition.y;
        }
    }


    public void setPause(boolean pause){
        this.pause = pause;
    }

    public void render(SpriteBatch spriteBatch) {

        //this is necessary when using Orthographic Camera
        if(!sprite.isFlipY())sprite.flip(false,true);
        spriteBatch.draw(sprite, position.x, position.y, scaledSize, (scaledSize/paddleDivision));
    }


    public float getScale(){
        return scaledSize;
    }
    public float getWidth(){ return scaledSize;}

    public void fall(float y){
        isFalling = true;
        velocity.y = y;
    }

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
