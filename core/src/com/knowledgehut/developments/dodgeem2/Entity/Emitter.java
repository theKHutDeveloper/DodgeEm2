package com.knowledgehut.developments.dodgeem2.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Emitter {
    private ParticleEffect effect;

    private Vector2 position;
    private Vector2 velocity;

    public Emitter(String filename, String directory, Vector2 position, Vector2 velocity){
        this.position = position;
        this.velocity = velocity;

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal(filename), Gdx.files.internal(directory));

        effect.setPosition(position.x, position.y);
        effect.flipY();
    }

    public void create(){
        effect.start();

        //this is necessary when using Orthographic Camera
        effect.setFlip(false, true);
    }

    public void update(Vector2 newPosition){
        setPosition(newPosition);
        effect.update(Gdx.graphics.getDeltaTime());

        if(effect.isComplete())effect.reset(); //repeats constantly
        System.out.println(position);
    }

    public void render(SpriteBatch spriteBatch){
        effect.draw(spriteBatch);
    }

    public void setPosition(Vector2 newPosition){
        this.position = newPosition;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void dispose(){
        effect.dispose();
    }
}
