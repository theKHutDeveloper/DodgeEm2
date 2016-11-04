package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;

public class Item{

    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float scaledSize;
    private Sprite sprite;
    private float circleX, circleY, radius;
    private Circle circle;
    private DodgeEm2.ItemType itemType;
    private float GAME_SCALE_X;

    public Item(Texture texture, Vector2 vector2, Vector2 velocity, float scaledSize, DodgeEm2.ItemType itemType) {
        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);
        this.texture = texture;
        this.position = vector2;
        this.velocity = velocity;
        this.scaledSize = scaledSize * GAME_SCALE_X;
        this.itemType = itemType;

        this.sprite = new Sprite(this.texture);

        circleX = MathUtils.floor(scaledSize/2);
        circleY = MathUtils.floor(scaledSize/2);
        radius = MathUtils.floor(circleY/2);
        circle = new Circle(position.x + circleX, position.y + circleY, radius);
    }

    public void update() {
        position.add(velocity);
        circle.set(position.x + circleX, position.y + circleY, radius);
    }


    public void render(SpriteBatch spriteBatch) {
        //elapsedTime += Gdx.graphics.getDeltaTime();

        //this is necessary when using Orthographic Camera
        if(!sprite.isFlipY())sprite.flip(false,true);

        spriteBatch.draw(sprite, position.x, position.y, scaledSize, scaledSize);
    }


    public Vector2 getPosition() {
        return position;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setPosition(Vector2 newPosition){
        this.position = newPosition;
    }

    public Circle getCircle() {
        return circle;
    }

    public DodgeEm2.ItemType getItemType(){
        return itemType;
    }


    public void dispose() {
        texture.dispose();
    }
}
