package com.knowledgehut.developments.dodgeem2.Entity;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Asteroid extends Entity{
    private boolean pause;

    public Asteroid(Texture texture, Vector2 vector2, Vector2 velocity, int frames){
        super(texture, vector2, velocity, frames);

        TextureRegion tmpTexture[][] = TextureRegion.split(texture,35,35);
        TextureRegion[] animationFrames = new TextureRegion[frames];
        int index = 0;

        for(int i = 0; i < frames; i++){
            animationFrames[index++] = tmpTexture[0][i];
        }

        animation = new Animation(1f/3f, animationFrames);
        pause = false;
    }

    public void setPause(boolean pause){
        this.pause = pause;
    }

    @Override
    public void update() {
        if(!pause) {
            position.add(velocity);
            circle.set(position.x + circleX, position.y + circleY, radius);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
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
    public void dispose(){
        super.dispose();
    }
}
