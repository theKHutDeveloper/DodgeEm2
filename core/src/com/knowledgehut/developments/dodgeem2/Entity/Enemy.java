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

    public Enemy(Texture texture, Vector2 vector2, Vector2 velocity, int frames, float scaledSize) {
        super(texture, vector2, velocity, frames, scaledSize);
        convertToAnimation(texture, frames);

        /*circleX = MathUtils.floor((texture.getWidth()/frames)/2);
        circleY = MathUtils.floor(texture.getHeight()/2);
        radius = MathUtils.floor(circleY/1.5f);
        circle = new Circle(position.x + circleX, position.y + circleY, radius);
*/
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("Effects/fireBlast.p"), Gdx.files.internal("Images"));

        effect.setPosition(position.x + circleX, position.y + circleY);
        effect.setFlip(false, true);

        isDying = false;
        killMode = true;
    }

    public void setEffect(){
        isDying = true;
        effect.start();
        killMode = false;
    }


    @Override
    public void update() {
        position.add(velocity);
        circle.set(position.x + circleX, position.y + circleY, radius);

        if(isDying){
            effect.setPosition(position.x + circleX, position.y + circleY);
            effect.update(0.2f);
        }
    }



    @Override
    public void render(SpriteBatch spriteBatch) {
        if(isDying){
            effect.draw(spriteBatch);
            effect.update(0.1f);
            if(effect.isComplete()){
                position.y = 490f;
            }
        }
        else {
            super.render(spriteBatch);
        }
    }

    public boolean getKillMode(){
        return killMode;
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
    }


    public boolean bulletHitCollision(Circle object){
        return circle.overlaps(object);
    }

    public void changeSpeed(float y){
        velocity.set(0,y);
    }




}
