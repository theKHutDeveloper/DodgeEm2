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
    private boolean isMoving, isMovingHorizontally, isShrinking, isStretching;
    private float verticalPosition;
    private float horizontalPosition;
    private float directionVertical, distance_y, distance_x;
    private float directionHorizontal, original_y, original_x, originalSize;
    private boolean pause;
    private boolean dropPlatform;
    private float GAME_SCALE_X;


    public Platform(Texture texture, Vector2 position, Vector2 velocity){
        GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);
        this.position = position;
        this.velocity = velocity.scl(GAME_SCALE_X);
        this.sprite = new Sprite(texture);
        scaledHeight = texture.getWidth() / texture.getHeight();
        this.scaledSize = originalSize = texture.getWidth() * GAME_SCALE_X;
        isMoving = isMovingHorizontally = isShrinking = isStretching = false;
        verticalPosition = horizontalPosition = 0;
        pause = false;
        dropPlatform = false;
    }


    public void setPause(boolean pause){
        this.pause = pause;
    }

    public float getHeight(){
        return scaledHeight;
    }

    public float getWidth(){
        return scaledSize;
    }

    public boolean update(float lowHeight, float highHeight) {//422, 120
        if(!pause) {
            position.add(velocity);

            if (isMoving) {
                position.y += directionVertical * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                if (Math.abs(position.y - original_y) >= distance_y) {
                    position.y = verticalPosition;
                    isMoving = false;
                }
            } else if (position.y <= highHeight) {
                dropPlatform = true;
                movePlatform(lowHeight);
            } else {
                dropPlatform = false;
            }

            if(isShrinking && scaledSize <= (100 * GAME_SCALE_X)){
                isShrinking = false;
                isStretching = true;
            }

            if(isStretching){
                if(scaledSize >= originalSize){
                    isStretching = false;
                    scaledSize = originalSize;
                    //backToNormalSize = true;
                } else {
                    stretchPlatform(20 * GAME_SCALE_X);
                    if(position.y < lowHeight){
                        dropPlatform = true;
                        movePlatform(lowHeight);
                    }
                }
            }

            if(isMovingHorizontally){
                position.x += directionHorizontal * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();

                if(Math.abs(position.x - original_x) >= distance_x){
                    position.x = horizontalPosition;
                    isMovingHorizontally = false;
                }

            } else if(position.x <= (-220 * GAME_SCALE_X) || position.x >= (220 * GAME_SCALE_X)){
                movePlatformHorizontal(0);
                if(position.y < lowHeight){
                    dropPlatform = true;
                    movePlatform(lowHeight);
                }
            }
        }
        return dropPlatform;
    }


    public boolean isPlatformMovingDown(){
        return dropPlatform;
    }

    public boolean hasPlatformFinishedMovingVertically(){
        return !dropPlatform && !isMoving;
    }

    public boolean hasPlatformFinishedMovingHorizontally(){
        return position.x == 0 && !isMovingHorizontally;
    }

    //shrinking platform
    public boolean hasPlatformFinishedShrinking(){
        return !isShrinking && !isStretching;
    }

    public void render(SpriteBatch spriteBatch) {

        //this is necessary when using Orthographic Camera
        if(!sprite.isFlipY())sprite.flip(false,true);

        spriteBatch.draw(sprite, position.x, position.y, scaledSize, originalSize/scaledHeight);
    }

    public void movePlatform(float vertical){
        verticalPosition = vertical;
        original_y = position.y;

        distance_y = Vector2.dst(position.x, position.y, position.x, vertical);
        directionVertical = (vertical-position.y) / distance_y;

        isMoving = true;
    }

    public void movePlatformHorizontal(float horizontal){
        horizontalPosition = horizontal;
        original_x = position.x;

        distance_x = Vector2.dst(position.x, position.y, horizontalPosition, position.y);
        directionHorizontal = (horizontalPosition - position.x) / distance_x;

        isMovingHorizontally = true;
    }

    public void shrinkPlatform(float size){
        isShrinking = true;
        scaledSize = scaledSize - size;
        position.x = position.x + (size/2);
    }

    private void stretchPlatform(float size){
        scaledSize += size;
        position.x = position.x - (size/2);
    }

    public Vector2 getPosition(){
        return position;
    }
}
