package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Texture texture;
    private Vector2 position;
    private boolean finished;
    private static final int SPEED = 20;
    private Circle circle;
    private boolean collided;
    private float circleX, circleY, radius;

    public Bullet(Texture texture, Vector2 position){
        this.texture = texture;
        this.position = position;
        finished = false;
        collided = false;
        circleX = MathUtils.floor((texture.getWidth())/2);
        circleY = MathUtils.floor(texture.getHeight()/2);
        radius = MathUtils.floor(circleY/2);
        circle = new Circle(position.x + circleX, position.y + circleY, radius);
    }

    public boolean isFinished(){
        return finished;
    }

    public void update(float deltaTime){
        position.y -= SPEED * deltaTime;
        circle.set(position.x + circleX, position.y + circleY, radius);

        if(position.y < 0 || collided){
            finished = true;
        }
    }

    public void render(SpriteBatch spriteBatch){
        spriteBatch.draw(texture, position.x, position.y);
    }

    public Circle getCircle(){
        return circle;
    }

    public void dispose(){
        this.texture.dispose();
    }


}
