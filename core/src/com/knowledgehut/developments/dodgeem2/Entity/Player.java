package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;


public class Player extends Entity {
    private float screenWidth;
    private ParticleEffect effect, sparkleEffect;
    private boolean isShieldOn;
    private boolean isSparkleSet;
    private int baseFrames;

    public Player(Texture texture, Vector2 vector2, Vector2 velocity, int frames, float screenWidth) {
        super(texture, vector2, velocity, frames);

        this.screenWidth = screenWidth;
        baseFrames = frames;
        convertToAnimation(texture, baseFrames);

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("Effects/shield3.p"), Gdx.files.internal("Images"));

        sparkleEffect = new ParticleEffect();
        sparkleEffect.load(Gdx.files.internal("Effects/sparkle1.p"), Gdx.files.internal("Images"));

        isShieldOn = false;
        effect.setPosition(position.x + circleX, position.y + circleY);
        effect.start();
        effect.setFlip(false, true);

        isSparkleSet = false;
        sparkleEffect.setFlip(false, true);
        sparkleEffect.setPosition(position.x, position.y);
    }

    public void changePlayerTexture(String imgName){
        Texture newTexture = new Texture(Gdx.files.internal(imgName));
        convertToAnimation(newTexture, baseFrames);
    }

    public void showShield(){
        isShieldOn = true;
    }

    public void hideShield(){
        isShieldOn = false;
    }

    public void setSparkleEffect(){
        isSparkleSet = true;
        sparkleEffect.start();
    }


    @Override
    public void update() {

        if(position.x > (screenWidth - scaledSize)){
            position.x = (screenWidth - scaledSize - 2 );
            circle.set(position.x + circleX, position.y + circleY, radius);
        } else if(position.x < 1 ){
            position.x = 2 ;
            circle.set(position.x + circleX, position.y + circleY, radius);
        }

        if(isShieldOn){
            effect.setPosition(position.x + circleX, position.y + circleY);
            effect.update(0.2f);
        }

        sparkleEffect.update(0.2f);
        sparkleEffect.setPosition(position.x, position.y);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
        if(isShieldOn){
            effect.draw(spriteBatch);
            effect.update(0.2f);
            if(effect.isComplete())effect.reset();
        }

        if(isSparkleSet){
            sparkleEffect.draw(spriteBatch);
            sparkleEffect.update(0.2f);
            if(sparkleEffect.isComplete()) isSparkleSet = false;
        }
    }

    @Override
    public Vector2 getPosition() {
        return super.getPosition();
    }

    @Override
    public Circle getCircle() {
        return super.getCircle();
    }

    public float getScale(){
        return scaledSize;
    }

    @Override
    public void dispose() {
        super.dispose();
        effect.dispose();
        sparkleEffect.dispose();
    }

    @Override
    void convertToAnimation(Texture texture, int frames) {
        super.convertToAnimation(texture, frames);
    }

    public boolean hasCollided(Circle thing){
        return(getCircle().overlaps(thing));
    }

    public void movePlayer(int screenX){
        position.x = screenX;
        circle.set(position.x + circleX, position.y + circleY, radius);
    }
}

