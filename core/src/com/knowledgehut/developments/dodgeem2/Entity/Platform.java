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
    //private boolean isMovingVertically, isMovingHorizontally, isShrinking, isStretching;
    private float verticalPosition;
    private float horizontalPosition;
    private float directionVertical, distance_y, distance_x;
    private float directionHorizontal, original_y, original_x, originalSize;
    private boolean pause;
   // private boolean dropPlatform;
    private float GAME_SCALE_X;
    private float validHeight;
    private boolean movingLeft, movingRight, movingUp, shrinking, movingUpAndShrink;
    private boolean movingLeftFinished = false;
    private boolean movingRightFinished =false;
    private boolean movingUpFinished = false;
    private boolean movingUpAndShrinkingFinished = false;
    private boolean movement = false;
    private boolean shrinkingFinished = false;
    private boolean movingUpAndRight;
    private boolean movingUpAndRightFinished = false;
    private boolean movingUpAndLeftFinished = false;
    private boolean movingUpAndLeft;

    public Platform(Texture texture, Vector2 position, Vector2 velocity){
        GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);
        this.position = position;
        this.velocity = velocity.scl(GAME_SCALE_X);
        this.sprite = new Sprite(texture);
        scaledHeight = texture.getWidth() / texture.getHeight();
        this.scaledSize = originalSize = texture.getWidth() * GAME_SCALE_X;
        verticalPosition = horizontalPosition = 0;
        pause = false;
        validHeight = this.position.y;
        movingLeft = movingRight = movingUp = shrinking = movingUpAndShrink = movingUpAndRight = movingUpAndLeft = false;
    }


    public void setPause(boolean pause){
        this.pause = pause;
    }

    public boolean getPause(){
        return pause;
    }


    public float getHeight(){
        return scaledHeight;
    }

    public float getWidth(){
        return scaledSize;
    }


    public void setMovement(){
        this.movement = true;
    }

    public boolean update(float highHeight){
        if(movement) {
            position.add(velocity);

            //***********************
            //MOVING HORIZONTALLY LEFT
            //***********************
            if (movingLeft) {

                if (!movingLeftFinished) {
                    position.x += directionHorizontal * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if(Math.abs(position.x - original_x) >= distance_x){
                        position.x = horizontalPosition;
                    }

                    if(position.x <= (-220 * GAME_SCALE_X)){
                        movingLeftFinished = true;
                        movement = false;
                    }

                } else {
                    movePlatformHorizontal(0);
                    position.x += directionHorizontal * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if(Math.abs(position.x - original_x) >= distance_x){
                        position.x = horizontalPosition;
                        movingLeft = false;
                    }
                }
            }

            else if(movingUpAndLeft){
                if(!movingUpAndLeftFinished){
                    position.y += directionVertical * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if (Math.abs(position.y - original_y) >= distance_y) {
                        position.y = verticalPosition;
                    }
                    position.x += directionHorizontal * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if(Math.abs(position.x - original_x) >= distance_x){
                        position.x = horizontalPosition;
                    }
                    if(position.x <= (-220 * GAME_SCALE_X) || position.y <= highHeight){
                        movingUpAndLeftFinished = true;
                        movement = false;
                    }
                } else {
                    if(position.y == validHeight && position.x == 0){
                        movingUpAndLeft = false;
                    }

                    if(position.x < 0) {
                        movePlatformHorizontal(0);
                        position.x += directionHorizontal * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                        if (Math.abs(position.x - original_x) >= distance_x) {
                            position.x = horizontalPosition;
                        }
                    }

                    if(position.y < validHeight) {
                        movePlatformDown(validHeight);
                        if (position.y < validHeight) {
                            position.y += directionVertical * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                            if (Math.abs(position.y - original_y) >= distance_y) {
                                position.y = verticalPosition;
                            }
                        }
                    }
                }
            }
            //***********************
            //MOVING UP AND RIGHT
            //***********************
            else if(movingUpAndRight){
                if(!movingUpAndRightFinished){
                    position.y += directionVertical * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if (Math.abs(position.y - original_y) >= distance_y) {
                        position.y = verticalPosition;
                    }
                    position.x += directionHorizontal * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if(Math.abs(position.x - original_x) >= distance_x){
                        position.x = horizontalPosition;
                    }
                    if(position.x >= (220 * GAME_SCALE_X) || position.y <= highHeight){
                        movingUpAndRightFinished = true;
                        movement = false;
                    }
                } else{
                    if(position.y == validHeight && position.x == 0){
                        movingUpAndRight = false;
                    }

                    if(position.x > 0) {
                        movePlatformHorizontal(0);
                        position.x += directionHorizontal * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                        if (Math.abs(position.x - original_x) >= distance_x) {
                            position.x = horizontalPosition;
                        }
                    }

                    if(position.y < validHeight) {
                        movePlatformDown(validHeight);
                        if (position.y < validHeight) {
                            position.y += directionVertical * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                            if (Math.abs(position.y - original_y) >= distance_y) {
                                position.y = verticalPosition;
                            }
                        }
                    }
                }
            }
            //***********************
            //MOVE UP AND SHRINKING PLATFORM
            //***********************
            else if(movingUpAndShrink){
                if(!movingUpAndShrinkingFinished){
                    position.y += directionVertical * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if (Math.abs(position.y - original_y) >= distance_y) {
                        position.y = verticalPosition;
                    }
                    if(scaledSize <= (140 * GAME_SCALE_X) || position.y <= highHeight){
                        movingUpAndShrinkingFinished = true;
                        movement = false;
                    }
                } else {
                    if(scaledSize >= originalSize){
                        scaledSize = originalSize;
                    } else {
                        stretchPlatform(20 * GAME_SCALE_X);
                        movePlatformDown(validHeight);
                    }
                    if(scaledSize == originalSize && position.y == validHeight){
                        movingUpAndShrink = false;
                    }

                    if(position.y < validHeight) {
                        position.y += directionVertical * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                        if (Math.abs(position.y - original_y) >= distance_y) {
                            position.y = verticalPosition;
                        }
                    }
                }
            }
            //***********************
            //SHRINKING PLATFORM
            //***********************
            else if(shrinking){
                if(!shrinkingFinished){
                    if(scaledSize <= (100 * GAME_SCALE_X)){
                        shrinkingFinished = true;
                        movement = false;
                    }
                }
                else {
                    if(scaledSize >= originalSize){
                        scaledSize = originalSize;
                        shrinking = false;
                    } else {
                        stretchPlatform(20 * GAME_SCALE_X);
                    }
                }
            }

            //***********************
             //MOVING HORIZONTALLY RIGHT
             //***********************
            else if (movingRight) {

                if (!movingRightFinished) {
                    position.x += directionHorizontal * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if(Math.abs(position.x - original_x) >= distance_x){
                        position.x = horizontalPosition;
                    }

                    if(position.x >= (220 * GAME_SCALE_X)){
                        movingRightFinished = true;
                        movement = false;
                    }

                } else {

                    movePlatformHorizontal(0);
                    position.x += directionHorizontal * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if(Math.abs(position.x - original_x) >= distance_x){
                        position.x = horizontalPosition;
                        movingRight = false;
                    }
                }
            }
            //***********************
            //MOVING VERTICALLY
            //***********************
            else if (movingUp) {

                if (!movingUpFinished) {
                    position.y += directionVertical * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if (Math.abs(position.y - original_y) >= distance_y) {
                        position.y = verticalPosition;
                    }

                    if (position.y >= highHeight) {
                        position.y -= ((20 * GAME_SCALE_X)+ Gdx.graphics.getDeltaTime());

                        if (position.y <= highHeight) {
                            movingUpFinished = true;
                        }
                        movement = false;
                    }
                } else {
                    if (position.y <= highHeight) {
                        movePlatformDown(validHeight);
                    }
                    position.y += directionVertical * (2 * GAME_SCALE_X) + Gdx.graphics.getDeltaTime();
                    if (Math.abs(position.y - original_y) >= distance_y) {
                        position.y = verticalPosition;
                        movingUp = false;
                    }
                }
            }

        }
        return false;
    }


    public void resetPlatform(){
        this.movingRightFinished = false;
        this.movingLeftFinished = false;
        this.movingUpFinished = false;
        this.shrinkingFinished = false;
        this.movingUpAndShrinkingFinished = false;
        this.movingUpAndRightFinished = false;
        this.movingUpAndLeftFinished = false;
    }

    public void setMovingUpAndRight(){
        this.movingUpAndRight = true;
    }

    public void setMovingUpAndLeft(){
        this.movingUpAndLeft = true;
    }
    public void setMovingUp(){
        this.movingUp = true;
    }

    public void setMovingLeft(){
        this.movingLeft = true;
    }

    public void setMovingRight(){
        this.movingRight = true;
    }

    public void setShrinking(){
        this.shrinking = true;
    }

    public void setMoveUpAndShrink(){
        this.movingUpAndShrink = true;
    }

    public boolean hasPlatformFinishedMovingAndShrinking(){
        return !movingUpAndShrink && movingUpAndShrinkingFinished;
    }

    public boolean hasPlatformFinishedMovingVertically(){
        return !movingUp && movingUpFinished;
    }

    public boolean hasPlatformFinishedMovingHorizontallyRight(){
        return !movingRight && movingRightFinished;
    }

    public boolean hasPlatformFinishedMovingHorizontallyLeft(){
        return !movingLeft && movingLeftFinished;
    }

    public boolean hasPlatformFinishedShrinking(){
        return !shrinking && shrinkingFinished;
    }

    public boolean hasPlatformFinishedMovingUpAndRight(){
        return !movingUpAndRight && movingUpAndRightFinished;
    }

    public boolean hasPlatformFinishedMovingUpAndLeft(){
        return !movingUpAndLeft && movingUpAndLeftFinished;
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
        directionVertical = (vertical - position.y) / distance_y;
    }

    private void movePlatformDown(float vertical){
        verticalPosition = vertical;
        original_y = position.y;

        distance_y = Vector2.dst(position.x, position.y, position.x, vertical);
        directionVertical = (vertical - position.y) / distance_y;
    }

    public void movePlatformHorizontal(float horizontal){
        horizontalPosition = horizontal;
        original_x = position.x;

        distance_x = Vector2.dst(position.x, position.y, horizontalPosition, position.y);
        directionHorizontal = (horizontalPosition - position.x) / distance_x;
    }

    public void movePlatformUpAndShrink(float vertical, float shrinkSize){
        shrinkPlatform(shrinkSize);
        movePlatform(vertical);
    }

    public void movePlatformUpAndRight(float vertical, float horizontal){
        movePlatformHorizontal(horizontal);
        movePlatform(vertical);
    }

    public void movePlatformUpAndLeft(float vertical, float horizontal){
        movePlatformHorizontal(horizontal);
        movePlatform(vertical);
    }

    public void shrinkPlatform(float size){
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
