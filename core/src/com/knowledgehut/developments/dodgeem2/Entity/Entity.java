package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


abstract class Entity {

    Vector2 position;
    Vector2 velocity;
    Circle circle;
    float circleX, circleY;
    float radius;
    boolean looped;
    private int frames;
    protected Animation animation;
    private Texture texture;
    private float elapsedTime;
    float scaledSize;
    float GAME_SCALE_X;


    Entity(Texture texture, Vector2 vector2, Vector2 velocity, int frames){
        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);
        this.texture = texture;
        this.position = vector2;
        this.velocity = velocity;
        this.frames = frames;
        this.scaledSize = (texture.getWidth()/frames) * GAME_SCALE_X;
        this.looped = true;

        circleX = MathUtils.floor((scaledSize)/2);
        circleY = MathUtils.floor(scaledSize/2);
        radius = MathUtils.floor(circleY/1.5f);
        circle = new Circle(position.x + circleX, position.y + circleY, radius);
    }

    Entity(Texture texture, Vector2 vector2, Vector2 velocity, int frames, float scaledSize){
        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);
        this.texture = texture;
        this.position = vector2;
        this.velocity = velocity.scl(GAME_SCALE_X);
        this.frames = frames;
        this.scaledSize = scaledSize * GAME_SCALE_X;

        circleX = MathUtils.floor((this.scaledSize)/2);
        circleY = MathUtils.floor(this.scaledSize/2);
        radius = MathUtils.floor(circleY/1.5f);
        circle = new Circle(position.x + circleX, position.y + circleY, radius);
    }

    public abstract void update();

    public void render(SpriteBatch spriteBatch){
        elapsedTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, true);

        //this is necessary when using Orthographic Camera
        if(!currentFrame.isFlipY())currentFrame.flip(false,true);
            spriteBatch.draw(animation.getKeyFrame(elapsedTime, true), position.x, position.y, scaledSize, scaledSize);
    }

    public Vector2 getPosition(){
        return position;
    }

    public Circle getCircle(){
        return circle;
    }

    public void dispose(){
        this.texture.dispose();
    }

    public float getScaledSize(){
        return scaledSize;
    }

    void convertToAnimation(Texture texture, int frames) {
        TextureRegion tmpTexture[][] = TextureRegion.split(texture,48,48);
        TextureRegion[] animationFrames = new TextureRegion[frames];
        int index = 0;

        for(int i = 0; i < frames; i++){
            animationFrames[index++] = tmpTexture[0][i];
        }

        animation = new Animation(1f/3f, animationFrames);
    }
}
