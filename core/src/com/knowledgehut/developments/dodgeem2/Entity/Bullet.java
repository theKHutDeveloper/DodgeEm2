package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;

public class Bullet {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private boolean finished;
    private Sprite sprite;
    private Circle circle;
    private boolean collided;
    private float circleX, circleY, radius;
    private float scaledSize = 12;
    private boolean pause;

    public Bullet(Texture texture, Vector2 position, Vector2 velocity){
        float GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);
        this.texture = texture;
        this.position = position;
        this.velocity = velocity.scl(GAME_SCALE_X);

        sprite = new Sprite(this.texture);
        //sprite.flip(false, true);
        this.scaledSize = scaledSize * GAME_SCALE_X;

        finished = false;
        collided = false;
        circleX = MathUtils.floor((texture.getWidth())/2);
        circleY = MathUtils.floor(texture.getHeight()/2);
        radius = MathUtils.floor(circleY/2);
        circle = new Circle(position.x + circleX, position.y + circleY, radius);
        pause = false;
    }

    public void setPause(boolean pause){
        this.pause = pause;
    }

    public boolean isFinished(){
        return finished;
    }

    public void update(){
        if(!pause) {
            position.add(velocity);
            circle.set(position.x + circleX, position.y + circleY, radius);

            if (position.y < 0 || collided) {
                finished = true;
            }
        }
    }

    public void render(SpriteBatch spriteBatch){

        if(!sprite.isFlipY())sprite.flip(false,true);

        spriteBatch.draw(sprite, position.x, position.y, scaledSize, scaledSize);
    }

    public Circle getCircle(){
        return circle;
    }

    public void dispose(){
        this.texture.dispose();
    }


}
