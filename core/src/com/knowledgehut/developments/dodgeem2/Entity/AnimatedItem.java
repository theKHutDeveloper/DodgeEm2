package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class AnimatedItem extends Entity {
    private int basicFrames;
    private boolean pause;

    public AnimatedItem(Texture texture, Vector2 vector2, Vector2 velocity, int frames, float scaledSize) {
        super(texture, vector2, velocity, frames, scaledSize);

        basicFrames = frames;
        convertToAnimation(texture, basicFrames);

        pause = false;
        looped = true;
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
    public void dispose() {
        super.dispose();
    }

    @Override
    void convertToAnimation(Texture texture, int frames) {
        super.convertToAnimation(texture, basicFrames);
    }
}
