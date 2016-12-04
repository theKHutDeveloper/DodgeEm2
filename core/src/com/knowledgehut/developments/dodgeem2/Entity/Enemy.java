package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity{
    private ParticleEffect effect;
    private boolean isDying;
    private boolean killMode;
    private boolean destroy;
    private boolean pause;

    public Enemy(Texture texture, Vector2 vector2, Vector2 velocity, int frames, float scaledSize) {
        super(texture, vector2, velocity, frames, scaledSize);
        convertToAnimation(texture, frames);

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("Effects/fireBlast.p"), Gdx.files.internal("Images"));

        effect.setPosition(position.x + circleX, position.y + circleY);
        effect.setFlip(false, true);

        isDying = false;
        killMode = true;
        destroy = false;
        pause = false;
    }

    public void setPause(boolean pause){
        this.pause = pause;
    }

    public void setEffect(){
        isDying = true;
        effect.start();
        killMode = false;
    }

    public float getVelocityY(){
        return velocity.y;
    }

    @Override
    public void update() {
        if(!pause) {
            position.add(velocity);
            circle.set(position.x + circleX, position.y + circleY, radius);

            if (isDying) {
                effect.setPosition(position.x + circleX, position.y + circleY);
                effect.update(0.2f);
                if (effect.isComplete()) {
                    position.y = -490f;
                    destroy = true;
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(isDying){
            effect.draw(spriteBatch);
            effect.update(0.1f);

        }
        else {
            super.render(spriteBatch);
        }
    }

    public boolean getKillMode(){
        return killMode;
    }

    public boolean isDestroy() {return destroy;}

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
    }


    public boolean bulletHitCollision(Circle object){
        return circle.overlaps(object);
    }

    public void changeSpeed(float y){
        velocity.set(0,y);
    }

    public float getScaledSize(){
        return super.getScaledSize();
    }


}
