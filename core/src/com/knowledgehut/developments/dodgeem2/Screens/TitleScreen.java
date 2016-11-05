package com.knowledgehut.developments.dodgeem2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


public class TitleScreen extends Screen implements InputProcessor {

    private OrthoCamera camera;
    private BitmapFont bmpFont;
    private Sprite background;
    private Sprite title;
    private String message;
    private float GAME_SCALE_X;
    private float bkgScale;

    @Override
    public void create() {
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);

        Gdx.input.setInputProcessor(this);

        Texture texture1 = new Texture(Gdx.files.internal("Images/title_background.png"));
        bkgScale = texture1.getWidth() * GAME_SCALE_X;
        background = new Sprite(texture1);

        Texture texture = new Texture(Gdx.files.internal("Images/title.png"));
        title = new Sprite(texture, (int)(texture.getWidth()* GAME_SCALE_X), (int)(texture.getHeight() * GAME_SCALE_X));
        title.flip(false, true);
        bmpFont = new BitmapFont(Gdx.files.internal("Fonts/largeFont.fnt"), true);
        bmpFont.getData().setScale(GAME_SCALE_X);

        message = "Touch screen to begin";
    }

    @Override
    public  void update(){
        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, bkgScale, bkgScale);
        spriteBatch.draw(title, 40 * GAME_SCALE_X, 160 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, message, 50 * GAME_SCALE_X, 260 * GAME_SCALE_X);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
        title.getTexture().dispose();
        bmpFont.dispose();
        Gdx.input.setInputProcessor(null);
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
        ScreenManager.setScreen(new MenuScreen());
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
