package com.knowledgehut.developments.dodgeem2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

import java.util.ArrayList;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


class LevelScreen2 extends Screen implements InputProcessor {

    private OrthoCamera camera;
    private ArrayList<Sprite> buttons = new ArrayList<Sprite>();
    private Sprite previous;

    @Override
    public void create() {

        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        float GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);
        Gdx.input.setInputProcessor(this);

        for(int i = 31; i < 49; i++){
            buttons.add(new Sprite(new Texture(Gdx.files.internal("Images/button_normal_"+i + ".png"))));
        }

        for (Sprite button : buttons) {
            button.setSize(button.getWidth() * GAME_SCALE_X, button.getHeight() * GAME_SCALE_X);
            button.flip(false, true);
            button.scale(-0.15f);
        }

        int x = (int)(45 * GAME_SCALE_X);
        int y = (int)(70 * GAME_SCALE_X);
        for(int i = 0; i < 18; i++){
            buttons.get(i).setPosition(x, y);
            x += (int)(45 * GAME_SCALE_X);
            if(i == 4 || i == 9 || i == 14){
                x = (int)(45 * GAME_SCALE_X);
                y += (int)(55 * GAME_SCALE_X);
            }
        }

        previous = new Sprite(new Texture(Gdx.files.internal("Images/left_normal.png")));
        previous.setSize(previous.getWidth() * GAME_SCALE_X, previous.getHeight() * GAME_SCALE_X);
        previous.flip(false, true);
        previous.scale(-0.15f);
        previous.setPosition(20 * GAME_SCALE_X, 420 * GAME_SCALE_X);
    }

        @Override
        public void update() {
            camera.update();
        }

        @Override
        public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            spriteBatch.begin();

            for (Sprite button : buttons) {
                button.draw(spriteBatch);
            }
            previous.draw(spriteBatch);

            spriteBatch.end();
        }

        @Override
        public void resize(int width, int height) {
            camera.resize(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        }

        @Override
        public void dispose() {
            for (Sprite button : buttons) {
                button.getTexture().dispose();
            }
            previous.getTexture().dispose();
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public boolean keyDown(int keycode) {

            if(keycode == Input.Keys.LEFT) {
                ScreenManager.setScreen(new LevelScreen2());
            }
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
