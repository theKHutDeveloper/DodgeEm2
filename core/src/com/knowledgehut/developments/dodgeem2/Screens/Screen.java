package com.knowledgehut.developments.dodgeem2.Screens;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Screen {
    public abstract void create();
    public abstract void update();
    public abstract void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer);
    public abstract void resize(int width, int height);
    public abstract void dispose();
    public abstract void pause();
    public abstract void resume();
}
