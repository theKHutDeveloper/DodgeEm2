package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class AnimatedItem extends Entity {
    private int basicFrames;

    public AnimatedItem(Texture texture, Vector2 vector2, Vector2 velocity, int frames, float scaledSize) {
        super(texture, vector2, velocity, frames, scaledSize);

        basicFrames = frames;
        convertToAnimation(texture, basicFrames);

        /*circleX = MathUtils.floor(scaledSize/2);
        circleY = MathUtils.floor(scaledSize/2);
        radius = MathUtils.floor(circleY/2);
        circle = new Circle(position.x + circleX, position.y + circleY, radius);*/
        looped = true;
    }

    @Override
    public void update() {
        position.add(velocity);
        circle.set(position.x + circleX, position.y + circleY, radius);
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
