package com.knowledgehut.developments.dodgeem2.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.HEIGHT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


class MenuScreen extends Screen implements InputProcessor{
    private OrthoCamera camera;
    private Sprite arcade;
    private Sprite classic;
    private Sprite sound_on, music_on, highscore, music_off;
    private String message;
    private BitmapFont bmpFont;
    private Sprite background;
    private float GAME_SCALE_X;
    private long delayTime = 1000;
    private long screenActive;
    private float bkgScale;
    private boolean musicOn;

    @Override
    public void create() {
        musicOn = DodgeEm2.prefs.getBoolean("musicOn");

        camera = new OrthoCamera(WIDTH, HEIGHT);
        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);
        Gdx.input.setInputProcessor(this);

        Texture texture = new Texture(Gdx.files.internal("Images/title_background.png"));
        bkgScale = texture.getWidth() * GAME_SCALE_X;
        background = new Sprite(texture);

        arcade = new Sprite(new Texture(Gdx.files.internal("Images/arcade_off.png")));
        classic = new Sprite(new Texture(Gdx.files.internal("Images/classic_off.png")));
        sound_on = new Sprite(new Texture(Gdx.files.internal("Images/sound_on.png")));
        music_on = new Sprite(new Texture(Gdx.files.internal("Images/music_on.png")));
        music_off = new Sprite(new Texture(Gdx.files.internal("Images/music_off.png")));
        highscore = new Sprite(new Texture(Gdx.files.internal("Images/highscores.png")));

        arcade.setSize(arcade.getWidth() * GAME_SCALE_X, arcade.getHeight() * GAME_SCALE_X);
        classic.setSize(classic.getWidth() * GAME_SCALE_X, classic.getHeight() * GAME_SCALE_X);
        sound_on.setSize(sound_on.getWidth() * GAME_SCALE_X, sound_on.getHeight() * GAME_SCALE_X);
        music_on.setSize(music_on.getWidth() * GAME_SCALE_X, music_on.getHeight() * GAME_SCALE_X);
        music_off.setSize(music_off.getWidth() * GAME_SCALE_X, music_off.getHeight() * GAME_SCALE_X);
        highscore.setSize(highscore.getWidth() * GAME_SCALE_X, highscore.getHeight() * GAME_SCALE_X);

        arcade.flip(false, true);
        classic.flip(false,true);
        sound_on.flip(false,true);
        music_on.flip(false,true);
        music_off.flip(false,true);
        highscore.flip(false,true);

        arcade.setPosition(60 * GAME_SCALE_X, 210* GAME_SCALE_X);
        classic.setPosition(60* GAME_SCALE_X, 260* GAME_SCALE_X);
        sound_on.setPosition(70* GAME_SCALE_X, 400* GAME_SCALE_X);
        music_on.setPosition(140* GAME_SCALE_X, 400* GAME_SCALE_X);
        music_off.setPosition(140* GAME_SCALE_X, 400* GAME_SCALE_X);
        highscore.setPosition(210* GAME_SCALE_X, 400* GAME_SCALE_X);

        message = "Choose GAME MODE";
        bmpFont = new BitmapFont(Gdx.files.internal("Fonts/largeFont.fnt"), true);
        bmpFont.getData().setScale(GAME_SCALE_X);

        screenActive = System.currentTimeMillis();
    }

    @Override
    public void update() {
        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        spriteBatch.draw(background, 0, 0, bkgScale, bkgScale);
        bmpFont.draw(spriteBatch, message, 55 * GAME_SCALE_X, 150 * GAME_SCALE_X);
        arcade.draw(spriteBatch);
        classic.draw(spriteBatch);
        sound_on.draw(spriteBatch);
        if(musicOn){
            music_on.draw(spriteBatch);
        } else music_off.draw(spriteBatch);

        highscore.draw(spriteBatch);

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
        sound_on.getTexture().dispose();
        music_on.getTexture().dispose();
        music_off.getTexture().dispose();
        highscore.getTexture().dispose();
        background.getTexture().dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean keyDown(int keycode) {

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
        if(TimeUtils.timeSinceMillis(screenActive) > delayTime) {
            if (screenX >= arcade.getX() && screenX <= arcade.getX() + arcade.getWidth()
                    && screenY >= arcade.getY() && screenY <= arcade.getY() + arcade.getHeight()) {
                arcade.setTexture(new Texture(Gdx.files.internal("Images/arcade_on.png")));
            } else if (screenX >= classic.getX() && screenX <= classic.getX() + classic.getWidth()
                    && screenY >= classic.getY() && screenY <= classic.getY() + classic.getHeight()) {
                classic.setTexture(new Texture(Gdx.files.internal("Images/classic_on.png")));
            }

            if (screenX >= music_on.getX() && screenX <= music_on.getX() + music_on.getWidth()
                    && screenY >= music_on.getY() && screenY <= music_on.getY() + music_on.getHeight()) {
                musicOn = !musicOn;

                DodgeEm2.prefs.putBoolean("musicOn", musicOn);
                DodgeEm2.prefs.flush();
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(TimeUtils.timeSinceMillis(screenActive) > delayTime) {
            if (screenX >= arcade.getX() && screenX <= arcade.getX() + arcade.getWidth()
                    && screenY >= arcade.getY() && screenY <= arcade.getY() + arcade.getHeight()) {
                ScreenManager.setScreen(new LevelScreen());
            }

            if (screenX >= classic.getX() && screenX <= classic.getX() + classic.getWidth()
                    && screenY >= classic.getY() && screenY <= classic.getY() + classic.getHeight()) {
                ScreenManager.setScreen(new GameScreen());
            }
        }
        return true;
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
