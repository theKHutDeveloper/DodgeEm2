package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


public class Player extends Entity {
    private int screenWidth;
    private ParticleEffect effect, sparkleEffect;
    private boolean isShieldOn;
    private boolean isSparkleSet;
    private int baseFrames;
    private float GAME_SCALE_X;

    public Player(Texture texture, Vector2 vector2, Vector2 velocity, int frames, int screenWidth) {
        super(texture, vector2, velocity, frames);

        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);

        this.screenWidth = screenWidth;
        baseFrames = frames;
        convertToAnimation(texture, baseFrames);

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("Effects/shield3.p"), Gdx.files.internal("Images"));

        sparkleEffect = new ParticleEffect();
        sparkleEffect.load(Gdx.files.internal("Effects/sparkle1.p"), Gdx.files.internal("Images"));

        /*circleX = MathUtils.floor((texture.getWidth()/frames)/2);
        circleY = MathUtils.floor(texture.getHeight()/2);
        radius = MathUtils.floor(circleY/1.5f);
        circle = new Circle(position.x + circleX, position.y + circleY, radius); */

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

    @SuppressWarnings("unused")
    public void moveLeft(){
        setDirection(-300, 0);
        if(position.x > (screenWidth - 10) * GAME_SCALE_X){
            position.x = (screenWidth - 20 * GAME_SCALE_X) ;
            circle.set(position.x + circleX, position.y + circleY, radius);
        }
        else {
            position.add(velocity);
            circle.set(position.x + circleX, position.y + circleY, radius);
        }
    }


   @SuppressWarnings("unused")
   public void moveRight(){
        setDirection(300, 0);
        if(position.x > screenWidth - 10){
            position.x = screenWidth - 40;
            circle.set(position.x + circleX, position.y + circleY, radius);
        }
        else {
                position.add(velocity);
                circle.set(position.x + circleX, position.y + circleY, radius);
            }

    }
    @Override
    public void update() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setDirection(-300, 0);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            setDirection(300, 0);
        } else setDirection(0,0);

        if(position.x > screenWidth - scaledSize){
            position.x = screenWidth - scaledSize - 2;
            circle.set(position.x + circleX, position.y + circleY, radius);
        } else if(position.x < 1){
            position.x = 2;
            circle.set(position.x + circleX, position.y + circleY, radius);
        } else {
            position.add(velocity);
            circle.set(position.x + circleX, position.y + circleY, radius);
        }

        if(isShieldOn){
            effect.setPosition(position.x + circleX, position.y + circleY);
            effect.update(0.2f);
        }

        sparkleEffect.update(0.2f);
        sparkleEffect.setPosition(position.x, position.y);
    }

    private void setDirection(float x, float y){
        velocity.set(x, y);
        velocity.scl(Gdx.graphics.getDeltaTime());
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
}

/*
delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.justTouched()){
        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        }

        if(touchPos.x > position.x) position.x += moveSpeed * delta;
        else if(touchPos.x < position.x) position.x -= moveSpeed * delta;
        if(touchPos.y > position.y) position.y += moveSpeed * delta;
        else if(touchPos.y < position.y) position.y -= moveSpeed * delta;

        if(Math.abs(touchPos.x - position.x) < 5) position.x = touchPosition.x;
        if(Math.abs(touchPos.y - position.y) < 5) position.y = touchPosition.y;

        spriteBatch.draw(textureRegion, position.x, position.y);*/
