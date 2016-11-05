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
import com.badlogic.gdx.utils.TimeUtils;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.*;


class FinScreen extends Screen implements InputProcessor{

    private BitmapFont bmpFont, bmpFont2;
    private String message, scoreText1, scoreText2, scoreText3, scoreText4, message2;
    private float GAME_SCALE_X;
    private OrthoCamera camera;
    private Sprite background;
    private long screenActive;
    private float bkgScale;

    @Override
    public void create() {
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        screenActive = System.currentTimeMillis();

        Gdx.input.setInputProcessor(this);

        Texture texture = new Texture(Gdx.files.internal("Images/title_background.png"));
        bkgScale = texture.getWidth() * GAME_SCALE_X;
        background = new Sprite(texture);

        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);

        bmpFont = new BitmapFont(Gdx.files.internal("Fonts/largeFont.fnt"), true);
        bmpFont2 = new BitmapFont(Gdx.files.internal("Fonts/mediumFont.fnt"), true);
        bmpFont.getData().setScale(GAME_SCALE_X);
        bmpFont2.getData().setScale(GAME_SCALE_X);

        message = "Game Over!!!";
        scoreText1 = "You scored " + SCORE + "  points ";
        scoreText2 = "* Fruit collected: " + FRUIT_SCORE;
        scoreText4 = "* Enemies destroyed:" + ENEMIES_KILLED;
        message2 = "Touch screen to replay";

        if(TOP_SCORE == 0){
            TOP_SCORE = SCORE;
        }
        else if(SCORE > TOP_SCORE){
            TOP_SCORE = SCORE;
        }

        scoreText3 = "Top Score: "+TOP_SCORE;
    }

    @Override
    public void update() {
        camera.update();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            ScreenManager.setScreen(new GameScreen());
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, bkgScale, bkgScale);
        bmpFont2.draw(spriteBatch, scoreText3, 10 * GAME_SCALE_X, 20 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, message, 80 * GAME_SCALE_X, 160 * GAME_SCALE_X);
        bmpFont2.draw(spriteBatch, scoreText1, 70 * GAME_SCALE_X, 238 * GAME_SCALE_X);
        bmpFont2.draw(spriteBatch, scoreText2, 90 * GAME_SCALE_X, 270 * GAME_SCALE_X);
        bmpFont2.draw(spriteBatch, scoreText4, 80 * GAME_SCALE_X, 310 * GAME_SCALE_X);
        bmpFont2.draw(spriteBatch, message2, 80 * GAME_SCALE_X, 400 * GAME_SCALE_X);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
    }

    @Override
    public void dispose() {
        bmpFont.dispose();
        bmpFont2.dispose();
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
        long delayTime = 1000;
        if(TimeUtils.timeSinceMillis(screenActive) > delayTime) {
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
