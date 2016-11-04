package com.knowledgehut.developments.dodgeem2.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.HEIGHT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


class MenuScreen extends Screen implements InputProcessor{
    private OrthoCamera camera;
    private Sprite arcade;
    private Sprite classic;
    private String message;
    private BitmapFont bmpFont;
    private float GAME_SCALE_X;

    @Override
    public void create() {
        camera = new OrthoCamera(WIDTH, HEIGHT);
        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);
        Gdx.input.setInputProcessor(this);

        arcade = new Sprite(new Texture(Gdx.files.internal("Images/arcade_button.png")));
        classic = new Sprite(new Texture(Gdx.files.internal("Images/classic_button.png")));
        arcade.setSize(arcade.getWidth() * GAME_SCALE_X, arcade.getHeight() * GAME_SCALE_X);
        classic.setSize(classic.getWidth() * GAME_SCALE_X, classic.getHeight() * GAME_SCALE_X);
        arcade.flip(false, true);
        arcade.setPosition(40 * GAME_SCALE_X, 230* GAME_SCALE_X);
        classic.flip(false,true);
        classic.setPosition(190* GAME_SCALE_X, 230* GAME_SCALE_X);

        message = "Choose GAME MODE to play";
        bmpFont = new BitmapFont(Gdx.files.internal("Fonts/largeFont.fnt"), true);
        bmpFont.getData().setScale(GAME_SCALE_X);
    }

    @Override
    public void update() {
        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        bmpFont.draw(spriteBatch, message, 20 * GAME_SCALE_X, 180 * GAME_SCALE_X);
        arcade.draw(spriteBatch);
        classic.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(WIDTH, HEIGHT);
    }

    @Override
    public void dispose() {
        arcade.getTexture().dispose();
        classic.getTexture().dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean keyDown(int keycode) {
        /*if(keycode == Input.Keys.A) {
            ScreenManager.setScreen(new LevelScreen());
        }
        if(keycode == Input.Keys.C) {
            ScreenManager.setScreen(new GameScreen());
        }*/
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX >= arcade.getX() && screenX <= arcade.getX() + arcade.getWidth()
                && screenY >= arcade.getY() && screenY <= arcade.getY() + arcade.getHeight()) {
            ScreenManager.setScreen(new LevelScreen());
        }

        if (screenX >= classic.getX() && screenX <= classic.getX() + classic.getWidth()
                && screenY >= classic.getY() && screenY <= classic.getY() + classic.getHeight()) {
            ScreenManager.setScreen(new GameScreen());
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}